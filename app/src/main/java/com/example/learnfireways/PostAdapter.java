package com.example.learnfireways;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends FirebaseRecyclerAdapter<Post,PostAdapter.PostViewHolder> {

   private Context context;

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, final int position, @NonNull Post model) {
        holder.title.setText(model.Title);
        holder.description.setText(model.discription);
        holder.author.setText(model.author);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Post")
                        .child(getRef(position).getKey())
                         .removeValue();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.content))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                LinearLayout layout=(LinearLayout)dialog.getHolderView();
                final EditText title=layout.findViewById(R.id.title);
                final EditText description=layout.findViewById(R.id.description);
                final EditText author=layout.findViewById(R.id.author);

                final Button update=layout.findViewById(R.id.update);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object>map=new HashMap<>();
                        map.put("Title",title.getText().toString());
                        map.put("author",author.getText().toString());
                        map.put("discription",description.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Post")
                                . child(getRef(position).getKey())
                                .setValue(map);
                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new PostViewHolder(view);


    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView title,description,author;
        ImageView edit,delete;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            author=itemView.findViewById(R.id.author);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);

        }
    }
}
