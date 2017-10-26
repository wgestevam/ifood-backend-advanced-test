package com.carlos.ifoodtest.types;

public enum PlaylistType {

    HOT("party", SpotifySearchType.PLAYLIST),
    GOOD_WEATHER("pop", SpotifySearchType.GENRE),
    BIT_CHILLY("rock", SpotifySearchType.GENRE),
    FREEZING("classical", SpotifySearchType.GENRE);

    private final String musicType;

    private final SpotifySearchType spotifyType;

    PlaylistType(String musicType, SpotifySearchType spotifyType) {
        this.musicType = musicType;
        this.spotifyType = spotifyType;
    }

    public static PlaylistType getEnumByTemperature(int temperature) {
        if (temperature > 30) {
            return HOT;
        } else if (temperature >= 15) {
            return GOOD_WEATHER;
        } else if (temperature >= 10) {
            return BIT_CHILLY;
        } else {
            return FREEZING;
        }
    }

    public SpotifySearchType getSpotifyType() {
        return spotifyType;
    }

    public String getMusicType() {
        return musicType;
    }

}
