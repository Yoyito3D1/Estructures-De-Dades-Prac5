package cat.urv.deim.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class User implements Comparable<User> {
    private int id;
    private HashMapIndirecte<Integer, Double> movieRatings;

    public User(int id) {
        this.id = id;
        this.movieRatings = new HashMapIndirecte<>();
    }

    public void addRating(int movieId, Double rating) {
        movieRatings.inserir(movieId, rating);
    }

    public int getId() { return id; }

    public boolean hasRatedMovie(int movieId) {
        return movieRatings.buscar(movieId);
    }

    public double getRating(int movieId) {
        return movieRatings.getOrDefault(movieId, 0.0);
    }

    public int compareTo(User user) { return Integer.compare(this.id, user.id); }

    public Set<Integer> getRatedMovies() {
        Object[] ratedMoviesArray = movieRatings.obtenirClaus();
        Integer[] ratedMovies = Arrays.copyOf(ratedMoviesArray, ratedMoviesArray.length, Integer[].class);
        return new HashSet<>(Arrays.asList(ratedMovies));
    }
}
