package studio.blackhand.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import studio.blackhand.popularmovies.model.Movie;

public class JsonUtils {

    private static final String JSON_KEY_RESULTS = "results";

    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_ORIGINAL_TITLE = "original_title";
    private static final String JSON_KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String JSON_KEY_POSTER_PATH = "poster_path";
    private static final String JSON_KEY_ADULT = "adult";
    private static final String JSON_KEY_OVERVIEW = "overview";
    private static final String JSON_KEY_RELEASE_DATE = "release_date";
    private static final String JSON_KEY_POPULARITY = "popularity";
    private static final String JSON_KEY_VOTE_COUNT = "vote_count";
    private static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    private static final String JSON_KEY_GENRE_IDS = "genre_ids";

    public static ArrayList<Movie> parseJson(String json) {
        ArrayList<Movie> movies = new ArrayList<>();

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

    private static Movie parseMovieJson(String json) {

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
        ArrayList<Integer> genreIds = new ArrayList<>();

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
