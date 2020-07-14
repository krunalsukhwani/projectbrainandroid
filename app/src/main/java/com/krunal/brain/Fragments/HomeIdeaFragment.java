package com.krunal.brain.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krunal.brain.Adapters.HomeIdeaAdapter;
import com.krunal.brain.Models.IdeaModel;
import com.krunal.brain.PreferenceManager;
import com.krunal.brain.R;
import com.krunal.brain.RequestUrl;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeIdeaFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private Button btnSearch;

    private EditText edtSearch;

    private RecyclerView recyclerView;
    private HomeIdeaAdapter ideasAdapter;

    private ArrayList<IdeaModel.Datum> arrayUserIdeas = new ArrayList<>();

    private boolean isFirstTime = true;

    public static HomeIdeaFragment newInstance() {
        HomeIdeaFragment fragment = new HomeIdeaFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        btnSearch = view.findViewById(R.id.btnSearch);

        edtSearch = view.findViewById(R.id.edtSearch);

        btnSearch.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);

        initialization();

        initList();
    }

    private void initList() {
        ideasAdapter = new HomeIdeaAdapter(getActivity(), arrayUserIdeas , "" + new PreferenceManager(getActivity()).getUsername());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ideasAdapter);
    }

    public void initialization(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(RequestUrl.GET_ALL_IDEA_URL, new AsyncHttpResponseHandler() {

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
                    IdeaModel ideaModel = gson.fromJson(new String(responseBody), IdeaModel.class);
                    Log.i("Todo Idea","responseBody: " + ideaModel.getData().toString());

                    arrayUserIdeas = new ArrayList<>();

                    arrayUserIdeas.addAll(ideaModel.getData());

                    initList();

                    initList();
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

    public void initializationByUsername(String username){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(RequestUrl.GET_IDEA_URL+ username + "/ideas", new AsyncHttpResponseHandler() {

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
                    IdeaModel ideaModel= gson.fromJson(new String(responseBody), IdeaModel.class);

                    arrayUserIdeas = new ArrayList<>();

                    arrayUserIdeas.addAll(ideaModel.getData());

                    initList();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isFirstTime && s.toString().trim().length() == 0) {
            initialization();
        }

        isFirstTime = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                if (!edtSearch.getText().toString().trim().equals("")) {
                    initializationByUsername("" + edtSearch.getText().toString().trim());
                }
                break;
        }
    }
}
