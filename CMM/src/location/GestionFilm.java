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
  
  /**
   * Supprime un artiste du système.
   *
   * @param artiste l'artiste à supprimer
   * @return <code>true</code> si l'artiste a été supprimé avec succès,
   *         <code>false</code> si l'artiste est <code>null</code> ou est lié à un film
   */
  
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
  
  /**
   * Crée un nouveau film dans le système.
   *
   * @param titre le titre du film (ne doit pas être <code>null</code> ou vide)
   * @param realisateur le réalisateur du film (ne doit pas être <code>null</code>)
   * @param annee l'année de sortie du film (doit être un entier positif)
   * @param ageLimite l'âge minimum requis pour visionner le film (doit être positif ou nul)
   * @return le film créé ou <code>null</code> si
   *                  les paramètres sont invalides
   *                   ou si un film avec le même titre existe déjà
   */
  


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
    Film nouveauFilm = new Film(titre, realisateur, annee, ageLimite);
    films.add(nouveauFilm);
    return nouveauFilm;
  }

  /**
   * Ajoute des acteurs à un film.
   *
   * @param film le film auquel ajouter les acteurs (ne doit pas être <code>null</code>)
   * @param acteurs une liste d'acteurs à ajouter (ne doit pas être <code>null</code>)
   * @return <code>true</code> si au moins un acteur a été ajouté avec succès,
   *         <code>false</code> si le film ou la liste d'acteurs est <code>null</code>
   */
  
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
  
  /**
   * Ajoute des genres à un film.
   *
   * @param film le film auquel ajouter les genres (ne doit pas être <code>null</code>)
   * @param genres une liste de genres à ajouter (ne doit pas être <code>null</code>)
   * @return <code>true</code> si au moins un genre a été ajouté avec succès,
   *         <code>false</code> si le film ou la liste de genres est <code>null</code>
   */
  
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
  
  /**
   * Ajoute une affiche à un film.
   *
   * @param film le film auquel ajouter l'affiche (ne doit pas être <code>null</code>)
   * @param file le chemin ou l'URL de l'affiche (ne doit pas être <code>null</code> ou vide)
   * @return <code>true</code> si l'affiche a été ajoutée avec succès, <code>false</code> sinon
   * @throws IOException si une erreur survient lors du traitement de l'affiche
   */
  
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
  
  /**
   * Supprime un film du système.
   *
   * @param film le film à supprimer (ne doit pas être <code>null</code>)
   * @return <code>true</code> si le film a été supprimé avec succès,
   *         <code>false</code> si le film est <code>null</code> ou n'existe pas dans le système
   */
  
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
  
  /**
   * Renvoie l'ensemble des films disponibles dans le système.
   *
   * @return un ensemble contenant tous les films ou un ensemble vide s'il n'y a aucun film
   */
  
  @Override
public Set<Film> ensembleFilms() {
    return films;
  }

  /**
   * Renvoie l'ensemble des acteurs présents dans le système.
   *
   * @return un ensemble contenant tous les acteurs ou un ensemble vide s'il n'y a aucun acteur
   */
  
  @Override
public Set<Artiste> ensembleActeurs() {
    Set<Artiste> acteurs = new HashSet<>();
    for (Film film : films) {
      acteurs.addAll(film.getActeurs());
    }
    return acteurs;
  }
  
  /**
   * Renvoie l'ensemble des réalisateurs présents dans le système.
   *
   * @return un ensemble contenant tous les réalisateurs
   *          ou un ensemble vide s'il n'y a aucun réalisateur
   */
  
  @Override
    public Set<Artiste> ensembleRealisateurs() {
    Set<Artiste> realisateurs = new HashSet<>();
    for (Film film : films) {
      realisateurs.add(film.getRealisateur());
    }
    return realisateurs;
  }
  
  /**
   * Renvoie l'ensemble des films réalisés par un réalisateur donné.
   *
   * @param realisateur le réalisateur dont on souhaite obtenir les films
   *        (ne doit pas être <code>null</code>)
   * @return un ensemble contenant les films du réalisateur 
   *         ou <code>null</code> si aucun film n'a été trouvé
   */
  
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
  
  /**
   * Renvoie l'ensemble des films dans lesquels un acteur donné a joué.
   *
   * @param acteur l'acteur dont on souhaite obtenir les films (ne doit pas être <code>null</code>)
   * @return un ensemble contenant les films de l'acteur
   *        ou <code>null</code> si aucun film n'a été trouvé
   */
  
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
  
  /**
   * Cherche un artiste à partir de son nom et prénom.
   *
   * @param nom le nom de l'artiste
   * @param prenom le prénom de l'artiste
   * @return l'artiste s'il a été trouvé ou <code>null</code> sinon
   */
  
  @Override
  public Artiste getArtiste(String nom, String prenom) {
    for (Artiste artiste : artistes) {
      if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
        return artiste;
      }
    }
    return null;
  }
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film (ne doit pas être <code>null</code> ou vide)
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  
  @Override
  public Film getFilm(String titre) {
    for (Film film : films) {
      if (film.getTitre().equals(titre)) {
        return film;
      }
    }
    return null;
  }

  
  /**
   * Ouvre la location pour un film donné.
   *
   * @param film le film à ouvrir à la location (ne doit pas être <code>null</code>)
   * @return <code>true</code> si le film a été ouvert à la location avec succès,
   *         <code>false</code> si le film est <code>null</code> ou déjà disponible à la location
   */
  
  @Override
  public boolean ouvrirLocation(Film film) {
    if (film == null || filmsLouable.contains(film)) {
      return false;
    }
    filmsLouable.add(film);
    film.setLocation(true);
    return true;
  }
  

  /**
   * Ferme la location pour un film donné.
   *
   * @param film le film à fermer à la location (ne doit pas être <code>null</code>)
   * @return <code>true</code> si le film a été fermé à la location avec succès,
   *         <code>false</code> si le film est <code>null</code>
   *          ou n'est pas actuellement disponible à la location
   */
  
  @Override
  public boolean fermerLocation(Film film) {
    if (film == null || !filmsLouable.contains(film)) {
      return false;
    }
    filmsLouable.remove(film);
    film.setLocation(false);
    return true;
  }
    
    
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre à rechercher (ne doit pas être <code>null</code>)
   * @return un ensemble contenant tous les films appartenant au genre donné,
   *         ou <code>null</code> si aucun film ne correspond
   */
  
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
  
  
  /**
   * Cherche un acteur à partir de son nom et de son prénom.
   *
   * @param nom le nom de l'acteur (ne doit pas être <code>null</code> ou vide)
   * @param prenom le prénom de l'acteur (ne doit pas être <code>null</code> ou vide)
   * @return l'acteur correspondant aux nom et prénom donnés,
   *         ou <code>null</code> si aucun acteur ne correspond
   */
  
  public Artiste getActeur(String nom, String prenom) {
    for (Artiste artiste : ensembleActeurs()) {
      if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
        return artiste;
      }
    }
    return null;
  }

  
  /**
   * Cherche un réalisateur à partir de son nom et de son prénom.
   *
   * @param nom le nom du réalisateur (ne doit pas être <code>null</code> ou vide)
   * @param prenom le prénom du réalisateur (ne doit pas être <code>null</code> ou vide)
   * @return le réalisateur correspondant aux nom et prénom donnés,
   *         ou <code>null</code> si aucun réalisateur ne correspond
   */ 
  
  public Artiste getRealisateur(String nom, String prenom) {
    for (Artiste artiste : ensembleRealisateurs()) {
      if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
        return artiste;
      }
    }
    return null;
  }

  
  
  
}
