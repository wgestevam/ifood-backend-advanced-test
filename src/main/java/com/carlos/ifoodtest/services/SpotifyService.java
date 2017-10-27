package com.carlos.ifoodtest.services;

import com.carlos.ifoodtest.models.SpotifyAdapter;
import com.carlos.ifoodtest.models.Track;
import com.carlos.ifoodtest.models.spotify.SearchSpotifyResponse;
import com.carlos.ifoodtest.models.spotify.SpotifyAccessToken;
import com.carlos.ifoodtest.models.spotify.SpotifyPlaylist;
import com.carlos.ifoodtest.repositories.TrackRepository;
import com.carlos.ifoodtest.types.PlaylistType;
import com.carlos.ifoodtest.types.SpotifySearchType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class SpotifyService {

    public static final String SPOTIFY_TOKEN_URL = "https://accounts.spotify.com/api/token";
    public static final String SPOTIFY_API_SEARCH_URL = "https://api.spotify.com/v1/search";
    private static final Logger logger = LoggerFactory.getLogger(SpotifyService.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TrackRepository trackRepository;

    @Value("${spotify.credentials}")
    private String spotifyCredentials;

    @Autowired
    private SpotifyAccessToken spotifyAccessToken;


    public List<Track> sugestTracksByWheater(PlaylistType playlistType) {
        try{
            return trackRepository.findAllBySugestionList(playlistType.getMusicType());
        }catch (Exception ex){
            logger.error("Banco fora do ar", ex);
            return listTracksPlaylistTypeFinalTracks(playlistType);
        }
    }

    private List<Track> listTracksPlaylistTypeFinalTracks(PlaylistType playlistType) {

        SearchSpotifyResponse searchSpotifyResponse = listTracksPlaylistType(playlistType);

        return getTracksFromSearchResponse(playlistType, searchSpotifyResponse);
    }

    public SearchSpotifyResponse listTracksPlaylistType(PlaylistType playlistType) {
        if (playlistType.getSpotifyType() == SpotifySearchType.GENRE) {
            return getMusics(playlistType.getMusicType());
        } else {
            return getTracksOnPlayList(playlistType.getMusicType());
        }
    }

    public SearchSpotifyResponse getTracksOnPlayList(String musicType) {
        String accessToken = spotifyAccessToken.getToken();

        try{

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + accessToken);
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<SearchSpotifyResponse> response = restTemplate.exchange(
                    SPOTIFY_API_SEARCH_URL +"?q={musicType}&type=playlist",
                    HttpMethod.GET,
                    httpEntity, SearchSpotifyResponse.class, musicType);
            return response.getBody();
        } catch (HttpClientErrorException ex){
            if (tratamentoNaoAutorizado(ex)) return getTracksOnPlayList(musicType);
        }
        return null;
    }

    public SearchSpotifyResponse getMusics(String musicType) {
        String accessToken = spotifyAccessToken.getToken();


        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + accessToken);
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<SearchSpotifyResponse> response = restTemplate.exchange(
                    SPOTIFY_API_SEARCH_URL+"?q=genre:{musicType}&type=track",
                    HttpMethod.GET,
                    httpEntity, SearchSpotifyResponse.class, musicType);

            return response.getBody();
        } catch (HttpClientErrorException ex){
            if (tratamentoNaoAutorizado(ex)) return getMusics(musicType);
        }
        return null;

    }

    private boolean tratamentoNaoAutorizado(HttpClientErrorException ex) {
        if(ex.getRawStatusCode() == 401){
            logger.error("client não autorizado",ex);
            spotifyAccessToken.setToken( getTokenSafely());
            return true;
        } else {
            logger.error("Erro desconhecido",ex);
        }
        return false;
    }

    public SpotifyPlaylist getPlaylist(String url) {
        String accessToken = spotifyAccessToken.getToken();

        try{

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + accessToken);
            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<SpotifyPlaylist> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity, SpotifyPlaylist.class);

            return response.getBody();
        } catch (HttpClientErrorException ex){
            if (tratamentoNaoAutorizado(ex)) return getPlaylist(url);
        }
        return null;
    }

    private String getTokenSafely() {
        String accessToken = null;
        try {
            accessToken = getToken();
        } catch (IOException e) {
            logger.error("não foi possível conseguir o token",e);
        }
        return accessToken;
    }

    public String getToken() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", String.format("Basic %s", spotifyCredentials));
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "client_credentials");

        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(SPOTIFY_TOKEN_URL, HttpMethod.POST, httpEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    @Transactional
    public void updateTracksByGenre(PlaylistType playlistType) {
        SearchSpotifyResponse searchSpotifyResponse = listTracksPlaylistType(playlistType);

        List<Track> savedTrackList = trackRepository.findAllBySugestionList(playlistType.getMusicType());


        List<Track> trackSugestions = getTracksFromSearchResponse(playlistType, searchSpotifyResponse);

        if(trackSugestions != null){
            trackSugestions.forEach(
                    track -> track.setSugestionList(playlistType.getMusicType())
            );
        }

        trackRepository.delete(savedTrackList);
        trackRepository.save(trackSugestions);

    }

    private List<Track> getTracksFromSearchResponse(PlaylistType playlistType, SearchSpotifyResponse searchSpotifyResponse) {
        List<Track> trackSugestions = null;
        if (playlistType.getSpotifyType() == SpotifySearchType.GENRE) {
            trackSugestions = SpotifyAdapter.extractTracksFromGenre(searchSpotifyResponse);
        } else {

            if(!searchSpotifyResponse.getSpotifyPlaylists().getItems().isEmpty()){
                SpotifyPlaylist spotifyPlaylist = getPlaylist(searchSpotifyResponse.getSpotifyPlaylists().getItems().get(0).getHref());
                trackSugestions = SpotifyAdapter.extractTracksFromPlayList(spotifyPlaylist);
            }
        }
        return trackSugestions;
    }

    @PostConstruct
    public void initToken(){
        spotifyAccessToken.setToken(getTokenSafely());
    }

}
