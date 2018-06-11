package studio.blackhand.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return images.length;
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

        Picasso
            .get()
            .load(images[position])
            .into((ImageView) convertView);

        return convertView;
    }

    public static String[] images = {
            "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
            "http://image.tmdb.org/t/p/w185/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg",
            "http://image.tmdb.org/t/p/w185/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
            "http://image.tmdb.org/t/p/w185/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg",
            "http://image.tmdb.org/t/p/w185/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",
            "http://image.tmdb.org/t/p/w185/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            "http://image.tmdb.org/t/p/w185/uxzzxijgPIY7slzFvMotPv8wjKA.jpg",
    };
}
