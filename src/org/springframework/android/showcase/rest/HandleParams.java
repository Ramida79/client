package org.springframework.android.showcase.rest;

/**
 * Created by PK on 2016-12-01.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ramida on 2016-08-07.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class HandleParams {


    @JsonProperty("x")
    private long x;
    @JsonProperty("y")
    private long y;
    @JsonProperty("z")
    private long z;

    @JsonProperty("x_current")
    private long x_current;
    @JsonProperty("y_current")
    private long y_current;
    @JsonProperty("z_current")
    private long z_current;

    public long getX(){return x;};
    public long getY(){return y;};
    public long getZ(){return z;};



    public void setAll(long X,long Y,long Z,long xc,long yc, long zc){
        x=X;y=X;z=Z;
        x_current=xc;y_current=yc;z_current=zc;


    }

}
