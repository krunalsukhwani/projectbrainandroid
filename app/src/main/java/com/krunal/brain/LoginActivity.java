package com.krunal.brain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krunal.brain.Models.LoginModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;
    private Button buttonRegister;
    private EditText editEmail;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                loginInApp();
                break;

            case R.id.buttonRegister:
                startActivity(new Intent(this, RegistrationActivity.class));
                finish();
                break;
        }
    }

    public void loginInApp(){
        AsyncHttpClient client = new AsyncHttpClient();
        try {

            StringEntity entity = new StringEntity(RequestBodyUtil.loginBody(editEmail.getText().toString(),editPassword.getText().toString()).toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(LoginActivity.this, RequestUrl.LOGIN_URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Login","responseBody: " + json.toString());
                        Gson gson = new GsonBuilder().create();
                        LoginModel loginModel = gson.fromJson(new String(responseBody), LoginModel.class);
                        new PreferenceManager(LoginActivity.this).saveLoginData(loginModel.getUsername(),
                                loginModel.getFirstname(), loginModel.getFirstname(), loginModel.getEmail(),
                                loginModel.getLocation());
                        startActivity(new Intent(LoginActivity.this, drawer.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
