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

    @JsonProperty("cx")
    private long x_current;
    @JsonProperty("cy")
    private long y_current;
    @JsonProperty("cz")
    private long z_current;

    @JsonProperty("state")
    String state;

    public long getX(){return x;};
    public long getY(){return y;};
    public long getZ(){return z;};

    public long getX_current(){return x_current;};
    public long getY_current(){return y_current;};
    public long getZ_current(){return z_current;};

    public String getState(){return state;};


    public void setAll(long X,long Y,long Z,long xc,long yc, long zc, String st){
        x=X;y=X;z=Z;
        x_current=xc;y_current=yc;z_current=zc;
        state=st;

    }

}
