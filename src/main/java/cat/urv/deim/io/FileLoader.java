package cat.urv.deim.io;

import cat.urv.deim.models.Graf;
import cat.urv.deim.models.Pelicula;
import cat.urv.deim.models.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {
    private Graf graf;

    public FileLoader() {
        graf = new Graf();
    }

    public void loadMovies(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("###");
                if (data.length == 3) {
                    int movieId = Integer.parseInt(data[0]);
                    int year = Integer.parseInt(data[1]);
                    String title = data[2];
                    Pelicula movie = new Pelicula(movieId, year, title);
                    graf.addPelicula(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRatings(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int movieId = 0;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(":")) {
                    movieId = Integer.parseInt(line.substring(0, line.length() - 1));
                } else {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        int userId = Integer.parseInt(data[0]);
                        double rating = Double.parseDouble(data[1]);
                        User user = graf.getUsuari(userId);
                        if (user == null) {
                            user = new User(userId);
                            graf.addUser(user);
                        }
                        if (!user.hasRatedMovie(movieId)) {
                            user.addRating(movieId, rating);
                        }
                        Pelicula movie = graf.getPelicula(movieId);
                        if (movie != null) {
                            movie.addRating(rating);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Graf getGraf() {
        return graf;
    }
}
