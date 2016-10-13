package org.springframework.android.showcase.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by PK on 2016-10-10.
 */


@JsonIgnoreProperties(ignoreUnknown = true)

public class Login {

    @JsonProperty("duration")
    private String username;
    @JsonProperty("token")
    private String pass;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }
}
