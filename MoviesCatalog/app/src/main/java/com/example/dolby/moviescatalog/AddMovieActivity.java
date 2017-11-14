package com.example.dolby.moviescatalog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMovieActivity extends AppCompatActivity {

    private Toolbar mAddMovieToolbar;

    private Button mSaveMovieBtn;
    private EditText mTitle,mTopic,mYear,mDirector;

    private ProgressDialog mAddProgress;

    DatabaseReference mMovieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        mAddMovieToolbar = (Toolbar) findViewById(R.id.addMovie_appBar);
        setSupportActionBar(mAddMovieToolbar);
        getSupportActionBar().setTitle("Set Movie Informations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddProgress = new ProgressDialog(this);

        mMovieDatabase = FirebaseDatabase.getInstance().getReference().child("Movies");

        mTitle = (EditText) findViewById(R.id.movieTittleET);
        mTopic = (EditText) findViewById(R.id.movieTopicET);
        mYear = (EditText) findViewById(R.id.movieYearET);
        mDirector = (EditText) findViewById(R.id.movieDirectorET);

        mSaveMovieBtn = (Button) findViewById(R.id.addMovie_save_btn);
        mSaveMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                String topic = mTopic.getText().toString();
                String year = mYear.getText().toString();
                String director = mDirector.getText().toString();

                if (!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(topic)&&!TextUtils.isEmpty(year)&&!TextUtils.isEmpty(director)){

                    mAddProgress.setTitle("Adding Movie");
                    mAddProgress.setMessage("Please wait while we add the movie!");
                    mAddProgress.setCanceledOnTouchOutside(false);
                    mAddProgress.show();

                    addMovie(title,topic,year,director);


                } else {
                    Toast.makeText(AddMovieActivity.this,"Cannot add this movie, please check informations!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addMovie(String title,String topic,String year,String director){

        mMovieDatabase = mMovieDatabase.push();

        mMovieDatabase.child("title").setValue(title);
        mMovieDatabase.child("topic").setValue(topic);
        mMovieDatabase.child("year").setValue(year);
        mMovieDatabase.child("director").setValue(director);

        mAddProgress.dismiss();

        Intent mainIntent = new Intent(AddMovieActivity.this,MainActivity.class);
        startActivity(mainIntent);

    }
}
