package studio.blackhand.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    private Integer id;
    private String title;
    private String originalTitle;
    private String originalLanguage;
    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private double popularity;
    private Integer voteCount;
    private double voteAverage;
    private ArrayList<Integer> genreIds;

    public Movie(
            Integer id,
            String title,
            String originalTitle,
            String originalLanguage,
            String posterPath,
            boolean adult,
            String overview,
            String releaseDate,
            double popularity,
            Integer voteCount,
            double voteAverage,
            ArrayList<Integer> genreIds) {

        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.genreIds = genreIds;
    }

    private Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.originalTitle = parcel.readString();
        this.originalLanguage = parcel.readString();
        this.posterPath = parcel.readString();
        this.adult = parcel.readInt() != 0;
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
        this.popularity = parcel.readDouble();
        this.voteCount = parcel.readInt();
        this.voteAverage = parcel.readDouble();

        ArrayList<Integer> genres = new ArrayList<>();
        parcel.readList(genres, ArrayList.class.getClassLoader());
        this.genreIds = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(posterPath);
        parcel.writeInt(adult ? 1 : 0);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeDouble(voteAverage);
        parcel.writeList(genreIds);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }
}
