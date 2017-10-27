package com.carlos.ifoodtest.models.openwheater;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MainTemp  {

    private static final long serialVersionUID = 3010864521391346282L;

    private int temp;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MainTemp mainTemp = (MainTemp) o;

        return new EqualsBuilder()
                .append(temp, mainTemp.temp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(temp)
                .toHashCode();
    }
}
