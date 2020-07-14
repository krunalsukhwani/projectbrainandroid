package com.krunal.brain.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TodoModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public TodoModel() {
    }

    /**
     *
     * @param data
     */
    public TodoModel(List<Datum> data) {
        super();
        this.data = data;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Composer {

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
        public Composer() {
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
        public Composer(String email, String username, String firstname, String lastname, String location, List<Object> followers, List<Object> todo) {
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
    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("context")
        @Expose
        private String context;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("composer")
        @Expose
        private Composer composer;

        /**
         * No args constructor for use in serialization
         *
         */
        public Datum() {
        }

        /**
         *
         * @param composer
         * @param context
         * @param id
         * @param title
         * @param content
         */
        public Datum(Integer id, String title, String context, String content, Composer composer) {
            super();
            this.id = id;
            this.title = title;
            this.context = context;
            this.content = content;
            this.composer = composer;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Composer getComposer() {
            return composer;
        }

        public void setComposer(Composer composer) {
            this.composer = composer;
        }

    }

}