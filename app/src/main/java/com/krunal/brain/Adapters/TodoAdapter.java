package com.krunal.brain.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krunal.brain.Fragments.AddCiteIdeaFragment;
import com.krunal.brain.Fragments.EditProfileFragment;
import com.krunal.brain.Fragments.OldIdeaFragment;
import com.krunal.brain.Models.TodoModel;
import com.krunal.brain.R;
import com.krunal.brain.drawer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private ArrayList<TodoModel.Datum> arrayUserTodos;
    private Context context;
    private String username;

    public TodoAdapter(Context context, ArrayList<TodoModel.Datum> arrayUserTodos, String username) {
        this.arrayUserTodos = arrayUserTodos;
        this.context = context;
        this.username = username;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.todo_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TodoModel.Datum userIdeaModel = arrayUserTodos.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());
        holder.txtPostedBy.setText("Composed By: " + userIdeaModel.getComposer().getUsername());

        holder.txtToDo.setVisibility(View.GONE);

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


        holder.txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayUserTodos.size();
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

}