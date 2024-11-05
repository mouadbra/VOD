package location;

import java.io.IOException;
import java.util.Set;

/**
 * Services de gestion des films, du point de vue de l'administrateur de
 * l'application.
 *
 * @author Eric Cariou
 */
public interface InterAdministration {
  
  /**
   * Création d'un nouvel artiste. Il ne doit pas déjà exister un artiste avec
   * le même nom et le même prénom.
   *
   * @param nom le nom de l'artiste (chaine non vide)
   * @param prenom le prénom de l'artiste (chaine vide "" si l'artiste n'a qu'un
   *        nom et pas de prénom)
   * @param nationalite la nationalité de l'artiste
   * @return l'artiste créé ou <code>null</code> en cas d'erreur (les paramètres
   *         sont invalides ou il existe déjà un artiste avec les mêmes valeurs)
   */
  Artiste creerArtiste(String nom, String prenom, String nationalite);
  
  /**
   * Supprime un artiste de l'ensemble des artistes. Ne peut pas être réalisé si
   * l'artiste est associé à au moins un film en tant qu'acteur ou réalisateur.
   *
   * @param artiste l'artiste à supprimer
   * @return <code>true</code> si la suppression a été réalisée,
   *         <code>false</code> sinon
   */
  boolean supprimerArtiste(Artiste artiste);
  
  /**
   * Création d'un nouveau film. Il ne doit pas déjà exister un film avec le
   * même titre.
   *
   * @param titre le titre du film (chaine non vide)
   * @param realisateur le réalisateur du film (non <code>null</code>)
   * @param annee l'année de réalisation du film
   * @param ageLimite l'âge minimum pour pouvoir regarder le film (0 si pas
   *        d'âge limite)
   * @return le film créé ou <code>null</code> en cas de problème (il existe
   *         déjà un film au même titre ou des paramètres n'étaient pas valides)
   */
  Film creerFilm(String titre, Artiste realisateur, int annee, int ageLimite);
  
  /**
   * Ajoute des acteurs à un film.
   *
   * @param film le film à modifier
   * @param acteurs la liste des acteurs à ajouter. Si des acteurs de la liste
   *        sont déjà associés au film, ils ne sont pas ajoutés en double.
   * @return <code>true</code> si au moins un acteur de la liste a été ajouté
   *         aux acteurs du film, <code>false</code> sinon
   */
  boolean ajouterActeurs(Film film, Artiste... acteurs);
  
  /**
   * Ajoute des genres à un film.
   *
   * @param film le film à modifier
   * @param genres la liste des genres à ajouter. Si des genres de la liste sont
   *        déjà associés au film, ils ne sont pas ajoutés en double.
   * @return <code>true</code> si au moins un genre de la liste a été ajouté aux
   *         genres du film, <code>false</code> sinon
   */
  boolean ajouterGenres(Film film, Genre... genres);
  
  /**
   * Ajoute une affiche à un film. Si le film avait déjà une affiche, elle est
   * remplacée par la nouvelle.
   *
   * @param film le film auquel ajouter une affiche
   * @param file le chemin du fichier qui contient l'image de l'affiche (au
   *        format JPG, PNG ...)
   * @return <code>true</code> si l'affiche a été modifiée (le format et la
   *         taille étaient valides)
   * @throws IOException en cas d'erreur de lecture du fichier
   */
  boolean ajouterAffiche(Film film, String file) throws IOException;
  
  /**
   * Supprime un film de l'ensemble des films.
   *
   * @param film le film à supprimer
   * @return <code>true</code> si le film a été supprimé ou <code>false</code>
   *         en cas de problème (le film n'existait pas ou le paramètre était
   *         égal à <code>null</code>)
   */
  boolean supprimerFilm(Film film);
  
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films
   */
  Set<Film> ensembleFilms();
  
  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return l'ensemble des acteurs
   */
  Set<Artiste> ensembleActeurs();
  
  /**
   * Renvoie l'ensemble des réalisateurs.
   *
   * @return l'ensemble des réalisateurs
   */
  Set<Artiste> ensembleRealisateurs();
  
  /**
   * Renvoie l'ensemble des films d'un réalisateur.
   *
   * @param realisateur le réalisateur dont on veut les films
   * @return l'ensemble des films du réalisateur ou <code>null</code> s'il n'en
   *         existe pas
   */
  Set<Film> ensembleFilmsRealisateur(Artiste realisateur);
  
  /**
   * Renvoie l'ensemble des films d'un acteur.
   *
   * @param acteur l'acteur dont on veut les films
   * @return l'ensemble des films de l'acteur ou <code>null</code> s'il n'en
   *         existe pas
   */
  Set<Film> ensembleFilmsActeur(Artiste acteur);
  
  /**
   * Cherche un artiste à partir de son nom et son prénom.
   *
   * @param nom le nom de l'artiste
   * @param prenom le prénom de l'artiste
   * @return l'artiste s'il a été trouvé ou <code>null</code> sinon
   */
  Artiste getArtiste(String nom, String prenom);
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  Film getFilm(String titre);
  
  
  /**
   * Ouvre un film à la location. Ne fait rien si le film était déjà ouvert à la
   * location.
   *
   * @param film le film à ouvrir à la location
   * @return <code>true</code> si le film est ouvert à la location,
   *         <code>false</code> en cas de problème (le film n'a pas été trouvé
   *         ou valeur <code>null</code>)
   */
  boolean ouvrirLocation(Film film);
  
  /**
   * Ferme la location d'un film. Ne fait rien si le film n'était pas ouvert à
   * la location.
   *
   * @param film le film dont il faut fermer la location
   * @return <code>true</code> si le film est fermé à la location,
   *         <code>false</code> en cas de problème (le film n'a pas été trouvé
   *         ou valeur <code>null</code>)
   */
  boolean fermerLocation(Film film);
}
