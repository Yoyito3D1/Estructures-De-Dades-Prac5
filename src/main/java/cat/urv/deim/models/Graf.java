package cat.urv.deim.models;

public class Graf {
    private HashMapIndirecte<Integer, Pelicula> pelicules;
    private HashMapIndirecte<Integer, User> usuaris;

    public Graf() {
        pelicules = new HashMapIndirecte<>();
        usuaris = new HashMapIndirecte<>();
    }

    public void addPelicula(Pelicula pelicula) {
        pelicules.inserir(pelicula.getId(), pelicula);
    }

    public void addUser(User user) {
        usuaris.inserir(user.getId(), user);
    }

    public Pelicula getPelicula(int movieId) { return pelicules.element(movieId); }

    public User getUsuari(int userId) {
        return usuaris.element(userId);
    }

    public LlistaDoblementEncadenada<User> getTotsUsuaris() {
        LlistaDoblementEncadenada<User> usuarisLlista = new LlistaDoblementEncadenada<>();
        Object[] userIds = usuaris.obtenirClaus();
        for (Object userId : userIds) {
            usuarisLlista.inserir(usuaris.element((Integer) userId));
        }
        return usuarisLlista;
    }





}
