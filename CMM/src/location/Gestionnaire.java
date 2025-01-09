package location;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



/**
 * Classe implements les methodes de l'interface InterUtilisateur.
 * 
 *
 * @author Mouad Brahmi, Mokhtar Rida
 */
public class Gestionnaire implements InterUtilisateur, Serializable {
  private static final long serialVersionUID = 1L;
  private GestionUtilisateur gestionUtilisateur;
  private GestionFilm gestionFilm;
  /**
   * Constructeur .
   */
  
  public Gestionnaire(GestionUtilisateur gestionUtilisateur, GestionFilm gestionFilm) {
    this.gestionUtilisateur = gestionUtilisateur;
    this.gestionFilm = gestionFilm;
  }
    
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
  @Override
  public int inscription(String pseudo, String mdp, InformationPersonnelle info) {
    if (pseudo == null || pseudo.isEmpty() || mdp == null || mdp.isEmpty()) {
      return 2; // Pseudo ou mot de passe vide
    }
    if (info == null || info.getNom() == null || info.getPrenom() == null || info.getAge() < 0) {
      return 3; // Informations personnelles invalides
    }
    if (gestionUtilisateur.estPseudoExistant(pseudo)) {
      return 1; // Pseudo déjà utilisé
    }
    gestionUtilisateur.ajouteUtilisateur(new Utilisateur(pseudo, mdp, info));
    return 0; // Inscription réussie
  }

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
  
  public boolean connexion(String pseudo, String mdp) {
    Utilisateur utilisateur = gestionUtilisateur.getUtilisateurParPseudo(pseudo);
    if (utilisateur == null) {
      return false; 
    } else {
      gestionUtilisateur.connecterUtilisateur(utilisateur);
      return utilisateur.getMotDePasse().equals(mdp);
    }
  }
  
  /**
   * Déconnecte l'utilisateur actuellement connecté.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */

  @Override
  public void deconnexion() throws NonConnecteException {
    // Vérifie si un utilisateur est connecté
    if (gestionUtilisateur.getUtilisateurConnecte() == null) {
      throw new NonConnecteException("erreur : utilisateur ne doit pas etre null");
    }

    // Délègue la déconnexion à la classe GestionUtilisateur
    gestionUtilisateur.deconnecterUtilisateur();
  }
  
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

  @Override
  public void louerFilm(Film film) throws NonConnecteException, LocationException {
    // Vérifie si un utilisateur est connecté
    Utilisateur utilisateur = gestionUtilisateur.getUtilisateurConnecte();
    if (utilisateur == null) {
      throw new NonConnecteException("Aucun utilisateur n'est connecté. Impossible"
      + " de louer un film.");
    }

    // Vérifie si le film est ouvert à la location
    if (!film.isEstOuvertalocation()) {
      throw new LocationException("Le film n'est pas ouvert à la location.");
    }

    // Vérifie si l'utilisateur a déjà 3 films en location
    if (utilisateur.getFilmsEnLocation().size() >= 3) {
      throw new LocationException("Vous avez atteint la limite de 3 films en location.");
    }

    // Vérifie l'âge de l'utilisateur
    if (utilisateur.getInfo().getAge() < film.getAgeLimite()) {
      throw new LocationException("Votre âge est insuffisant pour louer ce film.");
    }

    utilisateur.ajouterFilmenLocation(film);
  }

  
  /**
   * Termine la location d'un film.
   *
   * @param film le film dont la location est terminée
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de problème, notamment si l'utilisateur
   *         n'avait pas ce film en location, l'exception contiendra un message
   *         précisant le problème
   */

  @Override
  public void finLocationFilm(Film film) throws NonConnecteException, LocationException {
    // Vérifie si un utilisateur est connecté
    Utilisateur utilisateur = gestionUtilisateur.getUtilisateurConnecte();
    if (utilisateur == null) {
      throw new NonConnecteException("Aucun utilisateur n'est connecté. "
      + "Impossible de terminer une location.");
    }

    // Vérifie si le film fait partie des films en location
    if (!utilisateur.getFilmsEnLocation().contains(film)) {
      throw new LocationException("Ce film n'est pas actuellement en location par l'utilisateur.");
    }

    // Retire le film de la liste des films en location
    utilisateur.retirerFilmEnLocation(film);
  }

  /**
   * Information sur le fait qu'un film est ouvert à la location.
   *
   * @param film le film dont on veut vérifier la possibilité de location
   * @return <code>true</code> si le film est ouvert à la location, <code>false</code> sinon
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */

  @Override
  public boolean estLouable(Film film) throws NonConnecteException {
    // Vérifie si un utilisateur est connecté
    if (gestionUtilisateur.getUtilisateurConnecte() == null) {
      throw new NonConnecteException("Aucun utilisateur n'est connecté.");
    }

    // Vérifie si le film est ouvert à la location via GestionFilm
    return film.isEstOuvertalocation();
  }

  /**
   * Renvoie l'ensemble des films actuellement en location par l'utilisateur
   * connecté.
   *
   * @return les films en location par l'utilisateur connecté ou
   *         <code>null</code> si aucun film actuellement en location
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */

  @Override
  public Set<Film> filmsEnLocation() throws NonConnecteException {
    // Vérifie si un utilisateur est connecté
    Utilisateur utilisateurConnecte = gestionUtilisateur.getUtilisateurConnecte();
    if (utilisateurConnecte == null) {
      throw new NonConnecteException("Aucun utilisateur n'est connecté.");
    }

    // Retourne les films en location par l'utilisateur connecté
    return new HashSet<>(utilisateurConnecte.getFilmsEnLocation());
  }

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
  
  @Override
  public void ajouterEvaluation(Film film, Evaluation eval) 
      throws NonConnecteException, LocationException {
    Utilisateur utilisateurConnecte = gestionUtilisateur.getUtilisateurConnecte();
    if (utilisateurConnecte == null) {
      throw new NonConnecteException("Aucun utilisateur n'est connecté.");
    } 
    if (!utilisateurConnecte.gethistoriqueFilmsEnLocation().contains(film) 
        && eval.getCommentaire() != null) {
      throw new LocationException("vous pouvez pas evalue ce film, il existe pas "
      + "dans votre historique de location");

    }
    for (Evaluation e : utilisateurConnecte.getEvaluations()) {
      if (e.getFilm().equals(film)) {
        throw new LocationException("vous avez déjà évalué ce film");
      }
    }

        
    utilisateurConnecte.ajouterEvaluation(eval);
    film.ajouterEvaluation(utilisateurConnecte, eval);
  }

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

  @Override
  public void modifierEvaluation(Film film, Evaluation eval) 
      throws NonConnecteException, LocationException {
    Utilisateur utilisateurConnecte = gestionUtilisateur.getUtilisateurConnecte();
    if (utilisateurConnecte == null) {
      throw new NonConnecteException("Aucun utilisateur n'est connecté.");
    }

    Evaluation evaluation = utilisateurConnecte.getEvaluationParFilm(film);

    if (evaluation == null) {
      throw new LocationException("evaluation n'existe pas");

    }
    if (!utilisateurConnecte.gethistoriqueFilmsEnLocation().contains(film) 
        && eval.getCommentaire() != null) {
      throw new LocationException("vous pouvez pas evalue ce film, il existe pas "
        + "dans votre historique de location");

    }
    film.supprimerEvaluation(evaluation);
    utilisateurConnecte.supprimerEvaluation(evaluation);
    ajouterEvaluation(film, eval);
  }
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films ou <code>null</code> si aucun film n'existe
   */
  
  @Override
  public Set<Film> ensembleFilms() {
    return gestionFilm.ensembleFilms();
  }

  
  
  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return l'ensemble des acteurs ou <code>null</code> si aucun acteur
   *         n'existe
   */
  @Override
  public Set<Artiste> ensembleActeurs() {
    Set<Artiste> acteurs = gestionFilm.ensembleActeurs();
    return (acteurs == null || acteurs.isEmpty()) ? null : new HashSet<>(acteurs);
  }
  /**
   * Renvoie l'ensemble des réalisateurs.
   *
   * @return l'ensemble des réalisateurs ou <code>null</code> si aucun
   *         réalisateur n'existe
   */
  
  @Override
  public Set<Artiste> ensembleRealisateurs() {
    Set<Artiste> realisateurs = gestionFilm.ensembleRealisateurs();
    return (realisateurs == null || realisateurs.isEmpty()) ? null : new HashSet<>(realisateurs);
  }

  /**
   * Cherche un acteur à partir de son nom et son prénom.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'acteur s'il a été trouvé ou <code>null</code> sinon
   */
  
  @Override
  public Artiste getActeur(String nom, String prenom) {
    if (nom == null || nom.trim().isEmpty() || prenom == null || prenom.trim().isEmpty()) {
      return null; // Paramètres invalides
    }
    return gestionFilm.getActeur(nom, prenom);
  }

  /**
   * Cherche un réalisateur à partir de son nom et son prénom.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return le réalisateur s'il a été trouvé ou <code>null</code> sinon
   */
  
  @Override
  public Artiste getRealisateur(String nom, String prenom) {
    if (nom == null || nom.trim().isEmpty() || prenom == null || prenom.trim().isEmpty()) {
      return null; // Paramètres invalides
    }
    return gestionFilm.getRealisateur(nom, prenom);
  }
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */

  @Override
  public Film getFilm(String titre) {
    if (titre == null || titre.trim().isEmpty()) {
      return null; // Paramètre invalide
    }
    return gestionFilm.getFilm(titre);
  }

  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param realisateur le réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que le paramètre était invalide
   */
  
  @Override
  public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
    if (realisateur == null) {
      return null; // Paramètre invalide
    }
    return gestionFilm.ensembleFilmsRealisateur(realisateur);
  }

  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que les paramètres étaient invalides
   */

  @Override
  public Set<Film> ensembleFilmsRealisateur(String nom, String prenom) {
    Artiste realisateur = gestionFilm.getRealisateur(nom, prenom);
    if (realisateur == null) {
      return null;
    }
    return ensembleFilmsRealisateur(realisateur);
  }

  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param acteur l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le paramètre était invalide
   */
  
  @Override
  public Set<Film> ensembleFilmsActeur(Artiste acteur) {
    Set<Film> films = gestionFilm.ensembleFilmsActeur(acteur);
    return (films == null || films.isEmpty()) ? null : new HashSet<>(films);
  }
  

  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que les paramètres étaient invalides
   */
  
  @Override
  public Set<Film> ensembleFilmsActeur(String nom, String prenom) {
    Artiste acteur = gestionFilm.getActeur(nom, prenom);
    if (acteur == null) {
      return null;
    }
    return ensembleFilmsActeur(acteur);
  }

  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé
   */
  
  @Override
  public Set<Film> ensembleFilmsGenre(Genre genre) {
    Set<Film> films = gestionFilm.ensembleFilmsGenre(genre);
    return (films == null || films.isEmpty()) ? null : new HashSet<>(films);
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film (doit correspondre à un élément de
   *        l'énumération {@link location.Genre Genre})
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le genre était invalide
   *
   */
  
  @Override
  public Set<Film> ensembleFilmsGenre(String genre) {
    if (genre == null || genre.trim().isEmpty()) {
      System.out.println("Genre vide ou null !");
      return null; // Genre invalide
    }

    try {
      // Utilisez exactement le nom tel qu'il est dans l'énumération (PascalCase)
      Genre genreEnum = Genre.valueOf(genre.trim());
      System.out.println("Genre recherché : " + genreEnum);

      return ensembleFilmsGenre(genreEnum);
    } catch (IllegalArgumentException e) {
      System.out.println("Genre invalide : " + genre);
      return null;
    }
  }
  
  /**
   * Renvoie l'ensemble des genres présents dans tous les films de la collection.

   * @return un ensemble (Set) contenant tous les genres de films disponibles
   *         dans la collection. L'ensemble est vide si aucun film n'est présent.
   *
   */
  public Set<Genre> ensembleGenres() {
    Set<Genre> genres = new HashSet<>();

    // Parcourir tous les films et ajouter leurs genres dans l'ensemble
    for (Film film : gestionFilm.ensembleFilms()) {
      genres.addAll(film.getGenres());
    }

    return genres;
  }
  
  
  
  
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param film le film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour que le film ou que le film était invalide
   *         (valeur <code>null</code> par exemple)
   */
  
  @Override
  public Set<Evaluation> ensembleEvaluationsFilm(Film film) {
    if (film == null || film.getEvaluations().isEmpty()) {
      return null;
    }
    return new HashSet<>(film.getEvaluations());
  }

  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param titre le titre du film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour le film ou que le titre du film était
   *         inconnu ou invalide (valeur <code>null</code> par exemple)
   */
  
  @Override
  public Set<Evaluation> ensembleEvaluationsFilm(String titre) {
    Film film = gestionFilm.getFilm(titre);
    return (film == null) ? null : ensembleEvaluationsFilm(film);
  }
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param film le film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de film invalide (n'existant pas ou valeur
   *         <code>null</code>)
   */
  
  @Override
  public double evaluationMoyenne(Film film) {
    if (film == null) {
      return -2; // Film invalide
    }
    double moyenne = film.calculmoyenneEval();
    return moyenne > 0 ? moyenne : -1; // -1 si aucune évaluation
  }


  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param titre le titre du film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de titre de film invalide (il n'existe pas de film
   *         avec ce titre ou valeur <code>null</code>)
   */

  @Override
  public double evaluationMoyenne(String titre) {
    Film film = gestionFilm.getFilm(titre);
    return (film == null) ? -2 : evaluationMoyenne(film);
  }




}
