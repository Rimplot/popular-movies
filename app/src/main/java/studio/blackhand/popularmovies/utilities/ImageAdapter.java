package studio.blackhand.popularmovies.utilities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import studio.blackhand.popularmovies.MainActivity;
import studio.blackhand.popularmovies.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private static String POSTER_URI_STRING = "http://image.tmdb.org/t/p/";
    private static String POSTER_WIDTH = "w185";

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return MainActivity.movies.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // no view – create a new one
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        String posterUri = POSTER_URI_STRING + POSTER_WIDTH + MainActivity.movies.get(position).getPosterPath();
        Log.i("Poster URI:", posterUri);

        Picasso
            .get()
            .load(posterUri)
            .into((ImageView) convertView);

        return convertView;
    }

    public static String[] images = {
            "http://image.tmdb.org/t/p/w185/ePyN2nX9t8SOl70eRW47Q29zUFO.jpg",
            "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
            "http://image.tmdb.org/t/p/w185/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg",
            "http://image.tmdb.org/t/p/w185/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
            "http://image.tmdb.org/t/p/w185/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg",
            "http://image.tmdb.org/t/p/w185/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
            "http://image.tmdb.org/t/p/w185/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            "http://image.tmdb.org/t/p/w185/uxzzxijgPIY7slzFvMotPv8wjKA.jpg",
    };
}
