package com.example.learnfireways;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText title,discription,author;
    private Button button,read;
    private TextView text;
    private DatabaseReference post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            init();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               save();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  readRealTime();
            }
        });
    }

    private void readRealTime() {
         post.child("-LvBt7G7a6V_k1zGXZQq")
                 .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         String post="Title :"+dataSnapshot.child("Title").getValue(String.class)+"\n"+"Description :"
                                 +dataSnapshot.child("discription").getValue(String.class)+"\n"+
                                 "Author :"+dataSnapshot.child("author").getValue(String.class);
                         text.setText(post);
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
    }

    private void save() {
        HashMap<String,Object>map=new HashMap<>();
        map.put("Title",title.getText().toString());
        map.put("discription",discription.getText().toString());
        map.put("author",author.getText().toString());

      post.push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("ohk", "onComplete: ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("not ohk", "onFailure: "+e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("successful", "onSuccess: ");
            }
        });
    }

    private void init() {
        title=findViewById(R.id.title);
        discription=findViewById(R.id.discription);
        author=findViewById(R.id.author);
        button=findViewById(R.id.button);
        read=findViewById(R.id.read);
        text=findViewById(R.id.text);

          post = FirebaseDatabase.getInstance().getReference().child("Post");

    }
}
