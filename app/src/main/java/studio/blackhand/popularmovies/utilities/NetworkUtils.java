package studio.blackhand.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Scanner;

import studio.blackhand.popularmovies.BuildConfig;

public class NetworkUtils {

    private final static String THEMOVIEDB_BASE_URL = "http://api.themoviedb.org";
    private final static String API_VERSION = "3";
    private final static String API_KEY = BuildConfig.API_KEY;

    private final static String ENDPOINT_POPULAR = "movie/popular";
    private final static String ENDPOINT_TOP_RATED = "movie/top_rated";

    public final static String MODE_POPULAR = "mode_popular";
    public final static String MODE_TOP_RATED = "top_rated";

    public static String buildQueryUrlString(String queryType, Integer page) throws InvalidParameterException {
        String urlString = THEMOVIEDB_BASE_URL + "/"
                + API_VERSION + "/";

        switch (queryType) {
            case MODE_POPULAR:
                urlString += ENDPOINT_POPULAR + "/";
                break;
            case MODE_TOP_RATED:
                urlString += ENDPOINT_TOP_RATED + "/";
                break;
            default:
                throw new InvalidParameterException("Invalid value of queryType");
        }

        urlString += "?api_key=" + API_KEY;
        urlString += "&page=" + page;

        return urlString;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isOnline(@NonNull Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
