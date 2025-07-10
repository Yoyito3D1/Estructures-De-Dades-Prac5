package cat.urv.deim.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SistemaRecomanacions {
    private Graf graf;

    public SistemaRecomanacions(Graf graf) {
        this.graf = graf;
    }

    public List<Pelicula> getTopRecommendationsForUser(int userId, int topN) {
        User user = graf.getUsuari(userId);
        List<Pelicula> recommendations = new ArrayList<>();

        List<Pelicula> highRatedMovies = new ArrayList<>();
        for (int movieId : user.getRatedMovies()) {
            Pelicula movie = graf.getPelicula(movieId);
            if (movie.getAverageRating() >= 4.0) {
                highRatedMovies.add(movie);
            }
        }

        // Cerca els altres usuaris que han valorat aquestes pel·lícules amb una puntuació de 4 o més
        List<Integer> otherUsers = new ArrayList<>();
        for (Pelicula movie : highRatedMovies) {
            for (User otherUser : graf.getTotsUsuaris()) {
                if (otherUser.getId() != userId && otherUser.hasRatedMovie(movie.getId()) && otherUser.getRating(movie.getId()) >= 4.0) {
                    otherUsers.add(otherUser.getId());
                }
            }
        }

        // Recomana les pel·lícules vistes pels altres usuaris que compleixen els requisits
        for (int otherUserId : otherUsers) {
            User otherUser = graf.getUsuari(otherUserId);
            for (int movieId : otherUser.getRatedMovies()) {
                Pelicula movie = graf.getPelicula(movieId);
                if (!user.hasRatedMovie(movieId) && movie.getAverageRating() >= 4.0) {
                    recommendations.add(movie);
                }
            }
        }

        // Ordena les recomanacions segons la valoració mitjana i obté les primeres N pel·lícules recomanades
        recommendations.sort(Comparator.comparingDouble(Pelicula::getAverageRating).reversed());
        recommendations = recommendations.subList(0, Math.min(topN, recommendations.size()));

        return recommendations;
    }
}
