package com.krunal.brain.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("followers")
    @Expose
    private List<Object> followers = null;
    @SerializedName("todo")
    @Expose
    private List<Object> todo = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginModel() {
    }

    /**
     *
     * @param todo
     * @param firstname
     * @param followers
     * @param location
     * @param email
     * @param username
     * @param lastname
     */
    public LoginModel(String email, String username, String firstname, String lastname, String location, List<Object> followers, List<Object> todo) {
        super();
        this.email = email;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.location = location;
        this.followers = followers;
        this.todo = todo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Object> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Object> followers) {
        this.followers = followers;
    }

    public List<Object> getTodo() {
        return todo;
    }

    public void setTodo(List<Object> todo) {
        this.todo = todo;
    }

}