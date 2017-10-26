package com.carlos.ifoodtest.services;

import com.carlos.ifoodtest.models.SpotifyAdapter;
import com.carlos.ifoodtest.models.Track;
import com.carlos.ifoodtest.models.spotify.SearchSpotifyResponse;
import com.carlos.ifoodtest.models.spotify.SpotifyPlaylist;
import com.carlos.ifoodtest.repositories.TrackRepository;
import com.carlos.ifoodtest.types.PlaylistType;
import com.carlos.ifoodtest.types.SpotifySearchType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class SpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TrackRepository trackRepository;

    public List<Track> sugestTracksByWheater(PlaylistType playlistType) {
        return trackRepository.findAllBySugestionList(playlistType.getMusicType());
    }

    public SearchSpotifyResponse listTracksPlaylistType(PlaylistType playlistType) {
        if (playlistType.getSpotifyType() == SpotifySearchType.GENRE) {
            return getMusics(playlistType.getMusicType());
        } else {
            return getTracksOnPlayList(playlistType.getMusicType());
        }
    }

    public SearchSpotifyResponse getTracksOnPlayList(String musicType) {
        String accessToken = null;
        try {
            accessToken = getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<SearchSpotifyResponse> response = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q={musicType}&type=playlist",
                HttpMethod.GET,
                httpEntity, SearchSpotifyResponse.class, musicType);

        logger.debug(response.getBody().toString());

        return response.getBody();
    }

    public SearchSpotifyResponse getMusics(String musicType) {
        String accessToken = null;
        try {
            accessToken = getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<SearchSpotifyResponse> response = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=genre:{musicType}&type=track",
                HttpMethod.GET,
                httpEntity, SearchSpotifyResponse.class, musicType);

        logger.debug(response.getBody().toString());

        return response.getBody();
    }

    public SpotifyPlaylist getPlaylist(String url) {
        String accessToken = null;
        try {
            accessToken = getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<SpotifyPlaylist> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity, SpotifyPlaylist.class);

        logger.debug(response.getBody().toString());

        return response.getBody();
    }

    public String getToken() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Basic Y2E0NjdmODYyNjJkNDdkZmI5ZDMyY2M3YTdhNDgyOWI6ZTVlZDZiMWFhY2EyNDU2MmI3YzU5MmM5YjFlZjM5Njk=");
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "client_credentials");

        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("https://accounts.spotify.com/api/token", HttpMethod.POST, httpEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return (String) jsonNode.get("access_token").asText();
    }

    @Transactional
    public void updateTracksByGenre(PlaylistType playlistType) {
        SearchSpotifyResponse searchSpotifyResponse = listTracksPlaylistType(playlistType);

        List<Track> savedTrackList = trackRepository.findAllBySugestionList(playlistType.getMusicType());


        List<Track> trackSugestions = null;
        if (playlistType.getSpotifyType() == SpotifySearchType.GENRE) {
            trackSugestions = SpotifyAdapter.extractTracksFromGenre(searchSpotifyResponse);
        } else {

            if(!searchSpotifyResponse.getSpotifyPlaylists().getItems().isEmpty()){
                SpotifyPlaylist spotifyPlaylist = getPlaylist(searchSpotifyResponse.getSpotifyPlaylists().getItems().get(0).getHref());
                trackSugestions = SpotifyAdapter.extractTracksFromPlayList(spotifyPlaylist);
            }
        }

        if(trackSugestions != null){
            trackSugestions.stream().forEach(
                    track -> track.setSugestionList(playlistType.getMusicType())
            );
        }

        trackRepository.delete(savedTrackList);
        trackRepository.save(trackSugestions);

    }
}
