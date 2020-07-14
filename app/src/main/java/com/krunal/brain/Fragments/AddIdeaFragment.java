package com.krunal.brain.Fragments;

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

import com.krunal.brain.PreferenceManager;
import com.krunal.brain.R;
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

public class AddIdeaFragment extends Fragment implements View.OnClickListener {

    private EditText editTitle;
    private EditText editContext;
    private EditText editContent;
    private Button buttonSubmit;

    public static AddIdeaFragment newInstance() {
        AddIdeaFragment fragment = new AddIdeaFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_idea,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view){
        editTitle = view.findViewById(R.id.editTitle);
        editContext = view.findViewById(R.id.editContext);
        editContent = view.findViewById(R.id.editContent);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmit:
                addIdeaInApp();
                break;
        }
    }

    public void addIdeaInApp(){
        AsyncHttpClient client = new AsyncHttpClient();
        try {
            String username = new PreferenceManager(getActivity()).getUsername();
            StringEntity entity = new StringEntity(RequestBodyUtil.addIdeaBody(username,editTitle.getText().toString(),editContent.getText().toString(),editContext.getText().toString()).toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getActivity(), RequestUrl.ADD_IDEA_URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Add idea","responseBody: " + json.toString());
                        //getActivity().onBackPressed();
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
