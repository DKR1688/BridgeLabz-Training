import java.util.*;

class Movie {
    private String name;
    private double rating;
    private int releaseYear;

    public Movie(String name, double rating, int releaseYear) {
        this.name = name;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return name + " (" + releaseYear + ")- " + rating;
    }
}

public class Top5TrendingMovies {
    public static void main(String[] args) {

        List<Movie> movies = Arrays.asList(
            new Movie("LOST", 8.9, 2023),
            new Movie("Breaking Bad", 7.2, 2017),
            new Movie("Strangers Thing", 9.1, 2022),
            new Movie("GOT", 8.5, 2021),
            new Movie("From", 9.0, 2024),
            new Movie("Tenet", 8.7, 2020),
            new Movie("Interstellar", 7.9, 2023)
        );

        System.out.println("Top 5 Trending Movies:");

        movies.stream().filter(m -> m.getReleaseYear() >= 2019)
        				.sorted(Comparator.comparing(Movie::getRating).reversed())
        				.limit(5)
        				.forEach(System.out::println);
    }
}
