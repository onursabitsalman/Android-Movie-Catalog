package com.example.dolby.moviescatalog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private Toolbar mMainToolbar;
    private Button mAddMovieBtn;

    private ListView mListView;

    private DatabaseReference mMoviesDatabase,mWillDelete;

    private ArrayList<Movie> movieArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainToolbar = (Toolbar) findViewById(R.id.main_appBar);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setTitle("All Movies");

        movieArrayList = new ArrayList<Movie>();

        mListView = (ListView) findViewById(R.id.main_listView);

        mMoviesDatabase = FirebaseDatabase.getInstance().getReference().child("Movies");

        mMoviesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String title = ds.child("title").getValue().toString();
                    movieArrayList.add(new Movie(title));
                    Collections.sort(movieArrayList, new titleComparator());
                }



                //ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(Movie.class,R.layout.item_movie,R.id.item_movieName,movieArrayList);
                final CustomAdapter adapter = new CustomAdapter(MainActivity.this,movieArrayList);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final String movieTitle = adapter.getTitle(position);
                        Log.v("movie title" , movieTitle);

                        mMoviesDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds:dataSnapshot.getChildren()){
                                    String titleDatabase = ds.child("title").getValue().toString();

                                    if (movieTitle.equals(titleDatabase)){

                                        String topicDatabase = ds.child("topic").getValue().toString();
                                        String yearDatabase = ds.child("year").getValue().toString();
                                        String directorDatabase = ds.child("director").getValue().toString();
                                        mWillDelete=ds.getRef();
                                        Intent infoIntent = new Intent(MainActivity.this,InfoActivity.class);
                                        infoIntent.putExtra("title",titleDatabase);
                                        infoIntent.putExtra("topic",topicDatabase);
                                        infoIntent.putExtra("year",yearDatabase);
                                        infoIntent.putExtra("director",directorDatabase);

                                        startActivity(infoIntent);


                                        //Toast.makeText(MainActivity.this,titleDatabase + " " + topicDatabase + " " + yearDatabase + " " + directorDatabase,Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAddMovieBtn = (Button) findViewById(R.id.main_addMovie_btn);
        mAddMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addMovieIntent = new Intent(MainActivity.this,AddMovieActivity.class);
                startActivity(addMovieIntent);


            }
        });
        Collections.sort(movieArrayList, new titleComparator());
    }

    public class titleComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie m1, Movie m2) {

            return m1.getTitle().compareToIgnoreCase(m2.getTitle());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();




    }
}
