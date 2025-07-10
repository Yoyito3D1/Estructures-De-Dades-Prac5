package cat.urv.deim.models;

import cat.urv.deim.io.FileLoader;
import java.util.List;

public class RecomanacionsPelis {
    public static void main(String[] args) {
        // Càrrega del conjunt de dades
        FileLoader datasetLoader = new FileLoader();
        datasetLoader.loadMovies("movies.txt");
        datasetLoader.loadRatings("movie_users_10_20.txt");

        // Obtenir el graf amb les pel·lícules i usuaris carregats
        Graf graf = datasetLoader.getGraf();

        // Creació del sistema de recomanació
        SistemaRecomanacions recommenderSystem = new SistemaRecomanacions(graf);

        // Obtenir recomanacions per a un usuari específic, s'ha d'introduïr l'id de l'usuari i el nombre de recomanacions que es desitgin
        List<Pelicula> recommendations = recommenderSystem.getTopRecommendationsForUser(2165002, 3);

        // Mostrar recomanacions
        System.out.println("Les recomanacions s'han basat en les recomanacions globals de tots els usuaris de la base de dades i les que més ben valorades han estat son les següents: ");
        int i = 1;
        for (Pelicula movie : recommendations) {
            System.out.println(i + ". " + movie.getTitle() + "\n" + "Any de l'estrena: " + movie.getYear() + "\n" + "Valoració: " + movie.getAverageRating() + "/5");
            i++;
        }
    }
}
