package location;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe pour gérer les films et les artistes dans le système,
 * implémentant les services d'administration définis par l'interface InterAdministration.
 */
public class GestionFilm implements InterAdministration {

  // Ensemble des films disponibles pour la location
  private Set<Film> filmsLouable;

  // Ensemble de tous les films
  private Set<Film> films;

  // Ensemble de tous les artistes (acteurs et réalisateurs)
  private Set<Artiste> artistes;

  /**
     * Constructeur par défaut.
     */
  public GestionFilm() {
    this.filmsLouable = new HashSet<>();
    this.films = new HashSet<>();
    this.artistes = new HashSet<>();
  }

  @Override
  public Artiste creerArtiste(String nom, String prenom, String nationalite) {
    if (nom == null || nom.isEmpty() || nationalite == null) {
      return null;
    }

    // Vérifier qu'un artiste avec le même nom et prénom n'existe pas déjà
    for (Artiste artiste : artistes) {
      if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
        return null;
      }
    }

    // Création et ajout de l'artiste
    Artiste nouvelArtiste = new Artiste(nom, prenom, nationalite);
    artistes.add(nouvelArtiste);
    return nouvelArtiste;
  }

  @Override
  public boolean supprimerArtiste(Artiste artiste) {
    if (artiste == null) {
      return false;
    }

    // Vérifier si l'artiste est lié à un film
    for (Film film : films) {
      if (film.getRealisateur().equals(artiste) || film.getActeurs().contains(artiste)) {
        return false;
      }
    }

    return artistes.remove(artiste);
  }

  @Override
  public Film creerFilm(String titre, Artiste realisateur, int annee, int ageLimite) {
    if (titre == null || titre.isEmpty() || realisateur == null || annee <= 0 || ageLimite < 0) {
      return null;
    }

    // Vérifier qu'un film avec le même titre n'existe pas déjà
    for (Film film : films) {
      if (film.getTitre().equals(titre)) {
        return null;
      }
    }

    // Création et ajout du film
    Film nouveauFilm = new Film(titre, realisateur, annee, ageLimite, new HashSet<>());
    films.add(nouveauFilm);
    return nouveauFilm;
  }

  @Override
  public boolean ajouterActeurs(Film film, Artiste... acteurs) {
    if (film == null || acteurs == null) {
      return false;
    }

    boolean ajoutEffectue = false;
    for (Artiste acteur : acteurs) {
      if (!film.getActeurs().contains(acteur)) {
        film.getActeurs().add(acteur);
        ajoutEffectue = true;
      }
    }
    return ajoutEffectue;
  }

  @Override
 public boolean ajouterGenres(Film film, Genre... genres) {
    if (film == null || genres == null) {
      return false;
    }

    boolean ajoutEffectue = false;
    for (Genre genre : genres) {
      if (!film.getGenres().contains(genre)) {
        film.getGenres().add(genre);
        ajoutEffectue = true;
      }
    }
    return ajoutEffectue;
  }

  @Override
  public boolean ajouterAffiche(Film film, String file) throws IOException {
    if (film == null || file == null || file.isEmpty()) {
      return false;
    }

    // Supposons que le fichier est valide et peut être ajouté
    // Vous pouvez ajouter ici des validations supplémentaires (format, taille, etc.)
    film.setAffiche(file); // Ajout d'une méthode `setAffiche` dans la classe Film
    return true;
  }

  @Override
  public boolean supprimerFilm(Film film) {
    if (film == null || !films.contains(film)) {
      return false;
    }

    // Supprimer le film des listes associées
    films.remove(film);
    filmsLouable.remove(film);
    return true;
  }

  @Override
public Set<Film> ensembleFilms() {
    return new HashSet<>(films);
  }


  @Override
public Set<Artiste> ensembleActeurs() {
    Set<Artiste> acteurs = new HashSet<>();
    for (Film film : films) {
      acteurs.addAll(film.getActeurs());
    }
    return acteurs;
  }

  @Override
    public Set<Artiste> ensembleRealisateurs() {
    Set<Artiste> realisateurs = new HashSet<>();
    for (Film film : films) {
      realisateurs.add(film.getRealisateur());
    }
    return realisateurs;
  }

  @Override
    public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
        if (realisateur == null) {
            return null;
        }

        Set<Film> resultats = new HashSet<>();
        for (Film film : films) {
            if (film.getRealisateur().equals(realisateur)) {
                resultats.add(film);
            }
        }
        return resultats.isEmpty() ? null : resultats;
    }

    @Override
    public Set<Film> ensembleFilmsActeur(Artiste acteur) {
        if (acteur == null) {
            return null;
        }

        Set<Film> resultats = new HashSet<>();
        for (Film film : films) {
            if (film.getActeurs().contains(acteur)) {
                resultats.add(film);
            }
        }
        return resultats.isEmpty() ? null : resultats;
    }

    @Override
    public Artiste getArtiste(String nom, String prenom) {
        for (Artiste artiste : artistes) {
            if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
                return artiste;
            }
        }
        return null;
    }

    @Override
    public Film getFilm(String titre) {
        for (Film film : films) {
            if (film.getTitre().equals(titre)) {
                return film;
            }
        }
        return null;
    }

    @Override
    public boolean ouvrirLocation(Film film) {
        if (film == null || filmsLouable.contains(film)) {
            return false;
        }
        filmsLouable.add(film);
        film.setLocation(true);
        return true;
    }

    @Override
    public boolean fermerLocation(Film film) {
        if (film == null || !filmsLouable.contains(film)) {
            return false;
        }
        filmsLouable.remove(film);
        film.setLocation(false);
        return true;
    }
    
    
    
    public Set<Film> ensembleFilmsGenre(Genre genre) {
        if (genre == null) {
            return null;
        }
        Set<Film> result = new HashSet<>();
        for (Film film : films) {
            if (film.getGenres().contains(genre)) {
                result.add(film);
            }
        }
        return result.isEmpty() ? null : result;
    }

    public Artiste getActeur(String nom, String prenom) {
        for (Artiste artiste : ensembleActeurs()) {
            if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
                return artiste;
            }
        }
        return null;
    }

    public Artiste getRealisateur(String nom, String prenom) {
        for (Artiste artiste : ensembleRealisateurs()) {
            if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
                return artiste;
            }
        }
        return null;
    }

}
