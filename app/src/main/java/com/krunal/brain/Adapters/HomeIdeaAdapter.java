package com.krunal.brain.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.krunal.brain.Fragments.AddCiteIdeaFragment;
import com.krunal.brain.Fragments.EditProfileFragment;
import com.krunal.brain.Fragments.OldIdeaFragment;
import com.krunal.brain.Models.IdeaModel;
import com.krunal.brain.R;
import com.krunal.brain.RequestUrl;
import com.krunal.brain.drawer;
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

public class HomeIdeaAdapter extends RecyclerView.Adapter<HomeIdeaAdapter.ViewHolder> {

    private ArrayList<IdeaModel.Datum> arrayUserIdeas;
    private Context context;
    private String username;

    public HomeIdeaAdapter(Context context, ArrayList<IdeaModel.Datum> arrayUserIdeas, String username) {
        this.arrayUserIdeas = arrayUserIdeas;
        this.context = context;
        this.username = username;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.idea_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final IdeaModel.Datum userIdeaModel = arrayUserIdeas.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());
        holder.txtPostedBy.setText("Composed By: " + userIdeaModel.getComposer().getUsername());

        if (userIdeaModel.getComposer().getUsername().equals("" + username)) {
            holder.txtCite.setVisibility(View.GONE);
            holder.txtToDo.setVisibility(View.GONE);
            holder.txtFollow.setVisibility(View.GONE);
        } else {
            holder.txtCite.setVisibility(View.VISIBLE);
            holder.txtToDo.setVisibility(View.VISIBLE);
            holder.txtFollow.setVisibility(View.VISIBLE);
        }

        if(!(""+userIdeaModel.getCiteIdeaId()).equals("null")){
            holder.txtContext.setTextColor(((drawer)context).getResources().getColor(R.color.colorCite));
        }

        holder.txtContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(""+userIdeaModel.getCiteIdeaId()).equals("null")){
                    ((drawer)context).getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,
                            OldIdeaFragment.newInstance(userIdeaModel.getCiteIdeaId()), EditProfileFragment.class.getSimpleName()).commit();
                }
            }
        });

        holder.txtCite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((drawer)context).getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,
                        AddCiteIdeaFragment.newInstance(""+userIdeaModel.getId(),userIdeaModel.getTitle()), EditProfileFragment.class.getSimpleName()).commit();
            }
        });

        holder.txtToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo("" + userIdeaModel.getId());
            }
        });


        holder.txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFollow(userIdeaModel.getComposer().getUsername());
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayUserIdeas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtContext;
        public TextView txtContent;
        public TextView txtPostedBy;
        public TextView txtCite;
        public TextView txtToDo;
        public TextView txtFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContext = (TextView) itemView.findViewById(R.id.txtContext);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            this.txtPostedBy = (TextView) itemView.findViewById(R.id.txtPostedBy);
            this.txtCite = (TextView) itemView.findViewById(R.id.txtCite);
            this.txtToDo = (TextView) itemView.findViewById(R.id.txtToDo);
            this.txtFollow = (TextView) itemView.findViewById(R.id.txtFollow);
        }
    }

    public void addFollow(final String usernameToBeFollowed){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("otherUsername", usernameToBeFollowed);
            jsonParams.put("username","" + username);

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(context, RequestUrl.ADD_FOLLOW_USER_URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Register","responseBody: " + json.toString());

                        Toast.makeText(context, "Followed "+usernameToBeFollowed+".", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addTodo(String id){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("ideaId", id);
            jsonParams.put("username","" + username);

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(context, RequestUrl.ADD_TO_DO_URL, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Register","responseBody: " + json.toString());

                        Toast.makeText(context, "Submitted to To Do List.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}