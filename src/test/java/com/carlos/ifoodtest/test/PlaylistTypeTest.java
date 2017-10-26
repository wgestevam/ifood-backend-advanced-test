package com.carlos.ifoodtest.test;

import com.carlos.ifoodtest.types.PlaylistType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(value = JUnit4.class)
public class PlaylistTypeTest {

    @Test
    public void getHotEnumTest() {
        PlaylistType playlistType = PlaylistType.getEnumByTemperature(31);

        Assert.assertEquals(PlaylistType.HOT, playlistType);
    }

    @Test
    public void getGoodWeatherEnumTest() {
        PlaylistType playlistType = PlaylistType.getEnumByTemperature(25);

        Assert.assertEquals(PlaylistType.GOOD_WEATHER, playlistType);
    }

    @Test
    public void getBitChillyTest() {
        PlaylistType playlistType = PlaylistType.getEnumByTemperature(14);

        Assert.assertEquals(PlaylistType.BIT_CHILLY, playlistType);
    }


    @Test
    public void getFreezingTest() {
        PlaylistType playlistType = PlaylistType.getEnumByTemperature(9);

        Assert.assertEquals(PlaylistType.FREEZING, playlistType);
    }

}
