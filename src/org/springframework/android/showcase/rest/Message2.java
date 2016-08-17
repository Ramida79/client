package org.springframework.android.showcase.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ramida on 2016-08-07.
 */
public class Message2 {

    private long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String pass;

    public Message2() {
    }

    public Message2( String username, String pass) {

        this.username = username;
        this.pass = pass;
    }





    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return this.pass;
    }

    public String toString() {
        return   " [Username:] " + this.getUsername() + " [Password:] " + this.getPass();
    }

}
