package studio.blackhand.popularmovies.model;

import java.util.List;

public class Movie {

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
    private List<Integer> genreIds;

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
            List<Integer> genreIds) {

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
