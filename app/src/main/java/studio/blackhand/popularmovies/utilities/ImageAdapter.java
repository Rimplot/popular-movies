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

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Movie> allMovies;

    private static String POSTER_URI_STRING = "http://image.tmdb.org/t/p/";
    private static String POSTER_WIDTH = "w185";

    public ImageAdapter(Context c, ArrayList<Movie> movies) {
        movies = new ArrayList<>();
        movies.add(new Movie(0, "", "", "", "/ePyN2nX9t8SOl70eRW47Q29zUFO.jpg", false, "", "", 0, 0, 0, new ArrayList<Integer>()));
        movies.add(new Movie(0, "", "", "", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", false, "", "", 0, 0, 0, new ArrayList<Integer>()));
        movies.add(new Movie(0, "", "", "", "/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg", false, "", "", 0, 0, 0, new ArrayList<Integer>()));

        mContext = c;
        inflater = LayoutInflater.from(mContext);
        allMovies = movies;
    }

    public int getCount() {
        return allMovies.size();
    }

    public Object getItem(int position) {
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
            .into((ImageView) convertView);

        return convertView;
    }
}
