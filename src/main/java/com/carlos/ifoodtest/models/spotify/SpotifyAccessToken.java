package com.carlos.ifoodtest.models.spotify;

import org.springframework.stereotype.Component;

@Component
public class SpotifyAccessToken {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
