package com.example.dolby.moviescatalog;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Comparator;

public class InfoActivity extends AppCompatActivity {

    private EditText title_content,topic_content,year_content,director_content;
    String titleContent;
    DatabaseReference mMoviesDatabase=FirebaseDatabase.getInstance().getReference().child("Movies");
    DatabaseReference infoRef;
    private Toolbar mInfoToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mInfoToolbar = (Toolbar) findViewById(R.id.info_appBar);
        setSupportActionBar(mInfoToolbar);
        getSupportActionBar().setTitle("Movie Informations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_content = (EditText) findViewById(R.id.info_title_content);
        topic_content = (EditText) findViewById(R.id.info_topic_content);
        year_content = (EditText) findViewById(R.id.info_year_content);
        director_content = (EditText) findViewById(R.id.info_director_content);

        titleContent = getIntent().getStringExtra("title");
        String topicContent = getIntent().getStringExtra("topic");
        String yearContent = getIntent().getStringExtra("year");
        String directorContent = getIntent().getStringExtra("director");

        title_content.setText(titleContent);
        topic_content.setText(topicContent);
        year_content.setText(yearContent);
        director_content.setText(directorContent);

        mMoviesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String titleDatabase = ds.child("title").getValue().toString();

                    if (titleContent.equals(titleDatabase)){

                        infoRef=ds.getRef();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void delete(View view){

        infoRef.removeValue();
        Intent intent = new Intent(InfoActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void edit(View view){

        infoRef.child("title").setValue(title_content.getText().toString());
        infoRef.child("topic").setValue(topic_content.getText().toString());
        infoRef.child("year").setValue(year_content.getText().toString());
        infoRef.child("director").setValue(director_content.getText().toString());
        Intent intent = new Intent(InfoActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void trailer(View view) {

        Intent youtubeInt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+titleContent));
        startActivity(youtubeInt);

    }
}
