package studio.blackhand.popularmovies.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import studio.blackhand.popularmovies.model.Movie;

public class JsonUtils {

    public static final String JSON_KEY_RESULTS = "results";

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_TITLE = "title";
    public static final String JSON_KEY_ORIGINAL_TITLE = "original_title";
    public static final String JSON_KEY_ORIGINAL_LANGUAGE = "original_language";
    public static final String JSON_KEY_POSTER_PATH = "poster_path";
    public static final String JSON_KEY_ADULT = "adult";
    public static final String JSON_KEY_OVERVIEW = "overview";
    public static final String JSON_KEY_RELEASE_DATE = "release_date";
    public static final String JSON_KEY_POPULARITY = "popularity";
    public static final String JSON_KEY_VOTE_COUNT = "vote_count";
    public static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    public static final String JSON_KEY_GENRE_IDS = "genre_ids";

    public static List<Movie> parseJson(String json) {
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray movieJsonArray = jsonObject.optJSONArray(JSON_KEY_RESULTS);

            for (int i = 0; i < movieJsonArray.length(); i++) {
                movies.add(parseMovieJson(movieJsonArray.get(i).toString()));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    public static Movie parseMovieJson(String json) {

        Integer id;
        String title;
        String originalTitle;
        String originalLanguage;
        String posterPath;
        boolean adult;
        String overview;
        String releaseDate;
        double popularity;
        Integer voteCount;
        double voteAverage;
        List<Integer> genreIds = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            id = jsonObject.optInt(JSON_KEY_ID);
            title = jsonObject.optString(JSON_KEY_TITLE);
            originalTitle = jsonObject.optString(JSON_KEY_ORIGINAL_TITLE);
            originalLanguage = jsonObject.optString(JSON_KEY_ORIGINAL_LANGUAGE);
            posterPath = jsonObject.optString(JSON_KEY_POSTER_PATH);
            adult = jsonObject.optBoolean(JSON_KEY_ADULT);
            overview = jsonObject.optString(JSON_KEY_OVERVIEW);
            releaseDate = jsonObject.optString(JSON_KEY_RELEASE_DATE);
            popularity = jsonObject.optDouble(JSON_KEY_POPULARITY);
            voteCount = jsonObject.optInt(JSON_KEY_VOTE_COUNT);
            voteAverage = jsonObject.optDouble(JSON_KEY_VOTE_AVERAGE);
            JSONArray genreIdsArray = jsonObject.getJSONArray(JSON_KEY_GENRE_IDS);

            if (genreIdsArray != null) {
                for (int i = 0; i < genreIdsArray.length(); i++){
                    genreIds.add(Integer.valueOf(genreIdsArray.get(i).toString()));
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return new Movie(
                id,
                title,
                originalTitle,
                originalLanguage,
                posterPath,
                adult,
                overview,
                releaseDate,
                popularity,
                voteCount,
                voteAverage,
                genreIds
        );

    }

}
