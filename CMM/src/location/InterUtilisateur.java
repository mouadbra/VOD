package location;

import java.util.Set;

/**
 * Services de location de films, du point de vue de l'utilisateur de
 * l'application.
 *
 * @author Eric Cariou
 */
public interface InterUtilisateur {
  
  /**
   * Inscription d'un utilisateur. Le pseudo choisi ne doit pas déjà exister
   * parmi les utilisateurs déjà inscrits.
   *
   * @param pseudo le pseudo (unique) de l'utilisateur
   * @param mdp le mot de passe de l'utilisateur (ne pas doit pas être vide ou
   *        <code>null</code>)
   * @param info les informations personnelles sur l'utilisateur
   * @return un code précisant le résultat de l'inscription : 0 si l'inscription
   *         s'est bien déroulée, 1 si le pseudo était déjà utilisé, 2 si le
   *         pseudo ou le mot de passe était vide, 3 si les informations
   *         personnelles ne sont pas bien précisées
   */
  int inscription(String pseudo, String mdp, InformationPersonnelle info);
  
  /**
   * Connexion de l'utilisateur. Une fois connecté, l'utilisateur pourra accéder
   * aux services de location et déposer des commentaires sur les films qu'il a
   * loués.
   *
   * @param pseudo le pseudo de l'utilisateur
   * @param mdp le mot de passe de l'utilsateur
   * @return <code>true</code> si la connexion s'est bien déroulée,
   *         <code>false</code> en cas de couple pseudo/mot de passe invalide
   */
  boolean connexion(String pseudo, String mdp);
  
  /**
   * Déconnecte l'utilisateur actuellement connecté.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  void deconnexion() throws NonConnecteException;
  
  /**
   * L'utilisateur connecté loue un film. Il peut le louer s'il a moins de 3
   * films en cours de location et s'il a l'âge suffisant pour voir le film.
   *
   * @param film le film à louer
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de refus de location, l'exception
   *         contiendra un message précisant le problème (déjà 3 films loués,
   *         âge insuffisant ou autre)
   */
  void louerFilm(Film film) throws NonConnecteException, LocationException;
  
  /**
   * Termine la location d'un film.
   *
   * @param film le film dont la location est terminée
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de problème, notamment si l'utilisateur
   *         n'avait pas ce film en location, l'exception contiendra un message
   *         précisant le problème
   */
  void finLocationFilm(Film film)
      throws NonConnecteException, LocationException;
  
  /**
   * Information sur le fait qu'un film est ouvert à la location.
   *
   * @param film le film dont on veut vérifier la possibilité de location
   * @return <code>true</code> si le film est ouvert à la location, <code>false</code> sinon
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  boolean estLouable(Film film) throws NonConnecteException;
  
  /**
   * Renvoie l'ensemble des films actuellement en location par l'utilisateur
   * connecté.
   *
   * @return les films en location par l'utilisateur connecté ou
   *         <code>null</code> si aucun film actuellement en location
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  Set<Film> filmsEnLocation() throws NonConnecteException;
  
  /**
   * Ajoute à un film une évaluation de la part de l'utilisateur connecté.
   * L'utilisateur doit avoir loué le film pour le commenter (que le film soit
   * actuellement en sa location ou qu'il ait été loué puis retourné
   * préalablement). L'utilisateur ne doit pas déjà avoir déposé une évaluation
   * pour ce film.
   *
   * @param film le film à évaluer
   * @param eval l'évaluation du film
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException en cas d'erreur pour ajouter l'évaluation,
   *         l'exception contiendra un message précisant le problème
   */
  void ajouterEvaluation(Film film, Evaluation eval)
      throws NonConnecteException, LocationException;
  
  /**
   * Modifie l'évaluation que l'utilisateur connecté avait déjà déposée sur un
   * film. Ne peut se faire que si l'utilisateur avait déjà évalué le film.
   *
   * @param film le film dont l'utilisateur modifie l'évaluation
   * @param eval la nouvelle évaluation qui remplace la précédente
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException en cas d'erreur pour modifier l'évaluation,
   *         l'exception contiendra un message précisant le problème
   */
  void modifierEvaluation(Film film, Evaluation eval)
      throws NonConnecteException, LocationException;
  
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films ou <code>null</code> si aucun film n'existe
   */
  Set<Film> ensembleFilms();
  
  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return l'ensemble des acteurs ou <code>null</code> si aucun acteur
   *         n'existe
   */
  Set<Artiste> ensembleActeurs();
  
  /**
   * Renvoie l'ensemble des réalisateurs.
   *
   * @return l'ensemble des réalisateurs ou <code>null</code> si aucun
   *         réalisateur n'existe
   */
  Set<Artiste> ensembleRealisateurs();
  
  /**
   * Cherche un acteur à partir de son nom et son prénom.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'acteur s'il a été trouvé ou <code>null</code> sinon
   */
  Artiste getActeur(String nom, String prenom);
  
  /**
   * Cherche un réalisateur à partir de son nom et son prénom.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return le réalisateur s'il a été trouvé ou <code>null</code> sinon
   */
  Artiste getRealisateur(String nom, String prenom);
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  Film getFilm(String titre);
  
  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param realisateur le réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que le paramètre était invalide
   */
  Set<Film> ensembleFilmsRealisateur(Artiste realisateur);
  
  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que les paramètres étaient invalides
   */
  Set<Film> ensembleFilmsRealisateur(String nom, String prenom);
  
  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param acteur l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le paramètre était invalide
   */
  Set<Film> ensembleFilmsActeur(Artiste acteur);
  
  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que les paramètres étaient invalides
   */
  Set<Film> ensembleFilmsActeur(String nom, String prenom);
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé
   */
  Set<Film> ensembleFilmsGenre(Genre genre);
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film (doit correspondre à un élément de
   *        l'énumération {@link location.Genre Genre})
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le genre était invalide
   * @see location.Genre
   */
  Set<Film> ensembleFilmsGenre(String genre);
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param film le film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour que le film ou que le film était invalide
   *         (valeur <code>null</code> par exemple)
   */
  Set<Evaluation> ensembleEvaluationsFilm(Film film);
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param titre le titre du film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour le film ou que le titre du film était
   *         inconnu ou invalide (valeur <code>null</code> par exemple)
   */
  Set<Evaluation> ensembleEvaluationsFilm(String titre);
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param film le film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de film invalide (n'existant pas ou valeur
   *         <code>null</code>)
   */
  double evaluationMoyenne(Film film);
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param titre le titre du film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de titre de film invalide (il n'existe pas de film
   *         avec ce titre ou valeur <code>null</code>)
   */
  double evaluationMoyenne(String titre);
}
