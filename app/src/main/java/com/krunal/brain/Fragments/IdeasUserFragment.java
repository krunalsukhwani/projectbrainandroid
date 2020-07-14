package com.krunal.brain.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krunal.brain.Adapters.IdeasAdapter;
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

public class IdeasUserFragment extends Fragment {

    private RecyclerView recyclerView;

    private IdeasAdapter todoAdapter;

    private ArrayList<IdeaModel.Datum> arrayUserTodos = new ArrayList<>();

    public static IdeasUserFragment newInstance() {
        IdeasUserFragment fragment = new IdeasUserFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        initList();
        initialization();
    }

    private void initList() {
        todoAdapter = new IdeasAdapter(getActivity(), arrayUserTodos , "" + new PreferenceManager(getActivity()).getUsername());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(todoAdapter);
    }

    public void initialization(){
        Log.i("TAG", "initialization: ");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(RequestUrl.GET_IDEA_URL + new PreferenceManager(getActivity()).getUsername() + "/ideas", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"

                //Gson gson = new GsonBuilder().create();
                //PostModel postModel = gson.fromJson(new String(responseBody), PostModel.class);

                //Log.i("Register","responseBody: " + postModel.getTitle());
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Log.i("Get ideas","responseBody: " + json.toString());
                    Gson gson = new GsonBuilder().create();
                    IdeaModel ideaModel = gson.fromJson(new String(responseBody), IdeaModel.class);

                    arrayUserTodos.addAll(ideaModel.getData());
                    todoAdapter.notifyDataSetChanged();

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
}

