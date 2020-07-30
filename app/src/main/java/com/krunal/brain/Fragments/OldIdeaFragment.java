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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krunal.brain.Models.IdeaModel;
import com.krunal.brain.Models.OldIdeaModel;
import com.krunal.brain.PreferenceManager;
import com.krunal.brain.R;
import com.krunal.brain.RequestBodyUtil;
import com.krunal.brain.RequestUrl;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class OldIdeaFragment extends Fragment{

    public TextView txtTitle;
    public TextView txtContext;
    public TextView txtContent;
    public TextView txtPostedBy;
    public TextView txtCite;
    public TextView txtToDo;
    public TextView txtFollow;
    private String citeIdeaId;

    public static OldIdeaFragment newInstance(String citeIdeaId) {
        OldIdeaFragment fragment = new OldIdeaFragment();
        fragment.citeIdeaId = citeIdeaId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_old_idea,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view){
        this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        this.txtContext = (TextView) view.findViewById(R.id.txtContext);
        this.txtContent = (TextView) view.findViewById(R.id.txtContent);
        this.txtPostedBy = (TextView) view.findViewById(R.id.txtPostedBy);
        this.txtCite = (TextView) view.findViewById(R.id.txtCite);
        this.txtToDo = (TextView) view.findViewById(R.id.txtToDo);
        this.txtFollow = (TextView) view.findViewById(R.id.txtFollow);
        getIdea();
    }

    public void getIdea(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(RequestUrl.GET_IDEABYID_URL+"?id="+citeIdeaId, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"

                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Log.i("Get ideas","responseBody: " + json.toString());
                    Gson gson = new GsonBuilder().create();
                    OldIdeaModel ideaModel = gson.fromJson(new String(responseBody), OldIdeaModel.class);
                    Log.i("Todo Idea","responseBody: " + ideaModel.getTitle().toString());

                    txtTitle.setText("" + ideaModel.getTitle());
                    txtContext.setText("" + ideaModel.getContext());
                    txtContent.setText("" + ideaModel.getContent());
                    txtPostedBy.setText("Composed By: " + ideaModel.getComposer().getUsername());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
}
