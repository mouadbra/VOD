package location ;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe pour gérer les films et les artistes dans le système.
 */
public class GestionFilm {
    
    // Ensemble des films disponibles pour la location
    private Set<Film> filmsLouable;

    // Ensemble de tous les films
    private Set<Film> films;

    // Ensemble de tous les artistes (acteurs et réalisateurs)
    private Set<Artiste> artistes;

    // Constructeur
    public GestionFilm() {
        this.filmsLouable = new HashSet<>();
        this.films = new HashSet<>();
        this.artistes = new HashSet<>();
    }

    /**
     * Crée un nouvel artiste et l'ajoute à la collection d'artistes.
     *
     * @param nom le nom de l'artiste
     * @param prenom le prénom de l'artiste
     * @param nationalite la nationalité de l'artiste
     * @return l'artiste créé
     */
    public Artiste creerArtiste(String nom, String prenom, String nationalite) {
        Artiste artiste = new Artiste(nom, prenom, nationalite);
        artistes.add(artiste);
        return artiste;
    }

    /**
     * Supprime un artiste de la collection.
     *
     * @param artiste l'artiste à supprimer
     * @return true si l'artiste a été supprimé, false s'il n'existait pas
     */
    public boolean supprimerArtiste(Artiste artiste) {
        return artistes.remove(artiste);
    }

    /**
     * Crée un film et l'ajoute à la collection de films.
     *
     * @param titre le titre du film
     * @param realisateur le réalisateur du film
     * @param annee l'année de réalisation
     * @param ageLimite l'âge limite pour voir le film
     * @return le film créé
     */
    public Film creerFilm(String titre, Artiste realisateur, int annee, int ageLimite) {
        Film film = new Film(titre, realisateur, annee, ageLimite, new HashSet<>());
        films.add(film);
        return film;
    }

    /**
     * Ajoute des acteurs à un film.
     *
     * @param film le film auquel ajouter les acteurs
     * @param acteurs tableau d'acteurs à ajouter
     * @return true si tous les acteurs ont été ajoutés, false sinon
     */
    public boolean ajouterActeurs(Film film, Artiste[] acteurs) {
        boolean result = true;
        for (Artiste acteur : acteurs) {
            result &= film.ajouterActeur(acteur);
        }
        return result;
    }

    /**
     * Ajoute des genres à un film.
     *
     * @param film le film auquel ajouter les genres
     * @param genres tableau de genres à ajouter
     * @return true si tous les genres ont été ajoutés, false sinon
     */
    public boolean ajouterGenres(Film film, Genre[] genres) {
        boolean result = true;
        for (Genre genre : genres) {
            result &= film.ajouterGenre(genre);
        }
        return result;
    }

    /**
     * Ajoute une affiche à un film.
     *
     * @param film le film auquel ajouter l'affiche
     * @param file chemin du fichier de l'affiche
     * @return true si l'affiche a été ajoutée avec succès
     */
    public boolean ajouterAffiche(Film film, String file) {
        film.setAffiche(file);
        return true;
    }

    /**
     * Ouvre un film à la location.
     *
     * @param film le film à ouvrir
     * @return true si le film est maintenant louable
     */
    public boolean ouvrirLocation(Film film) {
        if (!filmsLouable.contains(film)) {
            filmsLouable.add(film);
            film.ouvrirLocation();
            return true;
        }
        return false;
    }

    /**
     * Ferme un film à la location.
     *
     * @param film le film à fermer
     * @return true si le film est maintenant non louable
     */
    public boolean fermerLocation(Film film) {
        if (filmsLouable.contains(film)) {
            filmsLouable.remove(film);
            film.fermerLocation();
            return true;
        }
        return false;
    }
}
