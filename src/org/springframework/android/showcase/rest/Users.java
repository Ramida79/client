package org.springframework.android.showcase.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ramida on 2016-08-07.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {


    @JsonProperty("id")
    private long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("role")
    private String role;

    @JsonProperty("password")
    private String password;

    public String getPassword()
    {return this.password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public String toString() {
        return "Id:[" + this.getId() + "] Subject:[" + this.getUsername() + "] Text:[" + this.getRole() + "]";
    }

}
