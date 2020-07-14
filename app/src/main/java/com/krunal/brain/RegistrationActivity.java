package com.krunal.brain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail;
    private EditText editPassword;
    private EditText editUsername;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editLocation;
    private Button buttonSubmit;
    private Button buttonBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    public void init(){
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editUsername = findViewById(R.id.editUsername);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editLocation = findViewById(R.id.editLocation);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonBackToLogin = findViewById(R.id.buttonBackToLogin);
        buttonSubmit.setOnClickListener(this);
        buttonBackToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmit:
                registrationInApp();
                break;
            case R.id.buttonBackToLogin:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    public void registrationInApp(){
        AsyncHttpClient client = new AsyncHttpClient();
        try {

            StringEntity entity = new StringEntity(RequestBodyUtil.registrationBody(editEmail.getText().toString(),editPassword.getText().toString(),editUsername.getText().toString(),editFirstName.getText().toString(),editLastName.getText().toString(),editLocation.getText().toString()).toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(RegistrationActivity.this, RequestUrl.REGISTRATION_URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Login","responseBody: " + json.toString());
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
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
