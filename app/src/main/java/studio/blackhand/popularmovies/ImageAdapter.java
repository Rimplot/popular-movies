package studio.blackhand.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = LayoutInflater.from(mContext);
            imageView = inflater
                    .inflate(R.layout.grid_item, null)
                    .findViewById(R.id.grid_item);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.pic, R.drawable.pic,
            R.drawable.pic, R.drawable.pic,
            R.drawable.pic, R.drawable.pic,
            R.drawable.pic, R.drawable.pic,
            R.drawable.pic, R.drawable.pic
    };
}
