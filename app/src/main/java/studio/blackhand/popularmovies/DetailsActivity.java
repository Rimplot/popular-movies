package studio.blackhand.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import studio.blackhand.popularmovies.model.Movie;
import studio.blackhand.popularmovies.utilities.ImageAdapter;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_MOVIE = "movie_key";

    private static final String POSTER_WIDTH = "original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Movie movie = getIntent().getParcelableExtra(KEY_MOVIE);

        String posterUri = ImageAdapter.POSTER_URI_STRING + POSTER_WIDTH + movie.getPosterPath();

        ImageView moviePoster = findViewById(R.id.movie_poster);
        Picasso
            .get()
            .load(posterUri)
            .into(moviePoster, new Callback() {
                @Override
                public void onSuccess() {
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);
        TextView tvVoteAverage = findViewById(R.id.tv_vote_average);
        TextView tvPlotSynopsis = findViewById(R.id.tv_plot_synopsis);

        String rating = new DecimalFormat("#.#").format(movie.getVoteAverage()) + "/10";

        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteAverage.setText(rating);
        tvPlotSynopsis.setText(movie.getOverview());
    }
}
