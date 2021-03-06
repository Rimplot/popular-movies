package studio.blackhand.popularmovies.utilities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import studio.blackhand.popularmovies.R;
import studio.blackhand.popularmovies.model.Movie;

public class ImageAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    public ArrayList<Movie> allMovies;

    public static final String POSTER_URI_STRING = "http://image.tmdb.org/t/p/";
    private static final String POSTER_WIDTH = "w185";

    public ImageAdapter(Context c, ArrayList<Movie> movies) {
        inflater = LayoutInflater.from(c);
        allMovies = movies;
    }

    public void clear() {
        allMovies.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return allMovies.size();
    }

    public Movie getItem(int position) {
        return allMovies.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // no view – create a new one
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        String posterUri = POSTER_URI_STRING + POSTER_WIDTH + allMovies.get(position).getPosterPath();
        Log.i("Poster URI:", posterUri);

        Picasso
            .get()
            .load(posterUri)
            .placeholder(R.drawable.transparent)
            .into((ImageView) convertView);

        return convertView;
    }
}
