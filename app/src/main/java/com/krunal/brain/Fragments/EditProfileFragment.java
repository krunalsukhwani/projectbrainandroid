package com.krunal.brain.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krunal.brain.LoginActivity;
import com.krunal.brain.Models.RegistrationModel;
import com.krunal.brain.PreferenceManager;
import com.krunal.brain.R;
import com.krunal.brain.RegistrationActivity;
import com.krunal.brain.RequestBodyUtil;
import com.krunal.brain.RequestUrl;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editEmail;
    private EditText editUsername;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editLocation;
    private Button buttonSubmit;

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_update_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view){
        editUsername = view.findViewById(R.id.editUsername);
        editEmail = view.findViewById(R.id.editEmail);
        editFirstName = view.findViewById(R.id.editFirstName);
        editLastName = view.findViewById(R.id.editLastName);
        editLocation = view.findViewById(R.id.editLocation);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
        editUsername.setText(new PreferenceManager(getActivity()).getUsername());
        editFirstName.setText(new PreferenceManager(getActivity()).get("firstname"));
        editLastName.setText(new PreferenceManager(getActivity()).get("lastname"));
        editLocation.setText(new PreferenceManager(getActivity()).get("location"));
        editEmail.setText(new PreferenceManager(getActivity()).get("email"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmit:
                editProfileInApp();
                break;
        }
    }

    public void editProfileInApp(){
        AsyncHttpClient client = new AsyncHttpClient();
        try {

            StringEntity entity = new StringEntity(RequestBodyUtil.editProfileBody(editUsername.getText().toString(),editFirstName.getText().toString(),editLastName.getText().toString(),editLocation.getText().toString()).toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getActivity(), RequestUrl.EDIT_PROFILE_URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Edit Profile","responseBody: " + json.toString());
                        Gson gson = new GsonBuilder().create();
                        RegistrationModel registrationModel = gson.fromJson(new String(responseBody), RegistrationModel.class);

                        Log.i("Update Profile","responseBody: " + registrationModel.getUsername());

                        new PreferenceManager(getActivity()).saveLoginData(registrationModel.getUsername(),
                                registrationModel.getFirstname(), registrationModel.getFirstname(), registrationModel.getEmail(),
                                registrationModel.getLocation());

                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();

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
