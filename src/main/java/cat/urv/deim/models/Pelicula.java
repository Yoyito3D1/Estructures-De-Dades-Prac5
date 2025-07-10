package cat.urv.deim.models;

public class Pelicula {
    private int id;
    private int year;
    private String title;
    private double totalRating;
    private int numRatings;

    public Pelicula(int id, int year, String title) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.totalRating = 0.0;
        this.numRatings = 0;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public double getAverageRating() {
        if (numRatings == 0) {
            return 0.0; // Retornar 0 si no hay valoraciones para evitar divisiones por cero
        }
        return totalRating / numRatings;
    }

    public void addRating(double rating) {
        totalRating += rating;
        numRatings++;
    }

}
