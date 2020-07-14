package com.krunal.brain;

import org.json.JSONObject;

public class RequestBodyUtil {
    public static JSONObject loginBody(String email, String password) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("email", email);
            jsonParams.put("password", password);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonParams;
    }

    public static JSONObject registrationBody(String email, String password,String username, String firstname, String lastname, String location) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("email", email);
            jsonParams.put("password", password);
            jsonParams.put("username",username);
            jsonParams.put("firstname",firstname);
            jsonParams.put("lastname",lastname);
            jsonParams.put("location",location);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonParams;
    }

    public static JSONObject editProfileBody(String username, String firstname, String lastname, String location) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username",username);
            jsonParams.put("firstname",firstname);
            jsonParams.put("lastname",lastname);
            jsonParams.put("location",location);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonParams;
    }

    public static JSONObject addIdeaBody(String username, String title, String content, String context) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username",username);
            jsonParams.put("title",title);
            jsonParams.put("content",content);
            jsonParams.put("context",context);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonParams;
    }
}
