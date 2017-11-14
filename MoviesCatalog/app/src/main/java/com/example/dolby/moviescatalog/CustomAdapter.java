package com.example.dolby.moviescatalog;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    LayoutInflater layoutInflater;
    ArrayList<Movie> movieList;

    public CustomAdapter(Activity activity, ArrayList<Movie> movieList){
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = movieList.get(position);
        View item = layoutInflater.inflate(R.layout.item_movie,null);
        TextView title = (TextView) item.findViewById(R.id.item_movieName);
        title.setText(movie.getTitle());
        return item;
    }

    public String getTitle(int position){
        Movie movie = movieList.get(position);
        String titlee = movie.getTitle();
        return titlee;
    }
}
