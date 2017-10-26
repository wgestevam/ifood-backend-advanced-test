package com.carlos.ifoodtest.models.openwheater;

public class MainTemp {

    private int temp;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "MainTemp{" +
                "temp=" + temp +
                '}';
    }
}
