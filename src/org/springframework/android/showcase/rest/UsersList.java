package org.springframework.android.showcase.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by Ramida on 2016-08-07.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersList {

    @JsonProperty("users")
    private List<Users> users;

    public UsersList() {
    }

    public UsersList(List<Users> states) {
        this.users = states;
    }

    public List<Users> getUsers() {
        return this.users;
    }

    public void setUsers(List<Users> states) {
        this.users = states;
    }

}
