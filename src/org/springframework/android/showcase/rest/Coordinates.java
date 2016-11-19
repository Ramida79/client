package org.springframework.android.showcase.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by PK on 2016-10-13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Coordinates {

    @JsonProperty("x")
    private Integer x;
    @JsonProperty("y")
    private Integer y;
    @JsonProperty("z")
    private Integer z;


    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public void setZ(Integer z) {
        this.z = z;
    }


    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }
    public Integer getZ() { return z; }


}
