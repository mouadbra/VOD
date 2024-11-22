package location;

import java.util.HashSet;
import java.util.Set;

public class Gestionnaire implements InterUtilisateur {
     
	
	private GestionUtilisateur gestionUtilisateur;
    private GestionFilm gestionFilm;

    public Gestionnaire(GestionUtilisateur gestionUtilisateur, GestionFilm gestionFilm) {
        this.gestionUtilisateur = gestionUtilisateur;
        this.gestionFilm = gestionFilm;
    }
    
    
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

	
	
	public boolean connexion(String pseudo, String mdp) {
	    Utilisateur utilisateur = gestionUtilisateur.getUtilisateurParPseudo(pseudo);
	    if (utilisateur == null) {
	        return false; 
	    } else {
	    	gestionUtilisateur.connecterUtilisateur(pseudo, mdp);
	        return utilisateur.getMotDePasse().equals(mdp);
	    }
	}


	@Override
	public void deconnexion() throws NonConnecteException {
	    // Vérifie si un utilisateur est connecté
	    if (gestionUtilisateur.getUtilisateurConnecte() == null) {
	        throw new NonConnecteException("erreur : utilisateur ne doit pas etre null");
	    }

	    // Délègue la déconnexion à la classe GestionUtilisateur
	    gestionUtilisateur.deconnecterUtilisateur();
	}


	@Override
	public void louerFilm(Film film) throws NonConnecteException, LocationException {
	    // Vérifie si un utilisateur est connecté
	    Utilisateur utilisateur = gestionUtilisateur.getUtilisateurConnecte();
	    if (utilisateur == null) {
	        throw new NonConnecteException("Aucun utilisateur n'est connecté. Impossible de louer un film.");
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

	    // Ajoute le film à la liste des films en location de l'utilisateur
	    if (!utilisateur.ajouterFilmenLocation(film)) {
	        throw new LocationException("Erreur lors de l'ajout du film en location.");
	    }
	}


	@Override
	public void finLocationFilm(Film film) throws NonConnecteException, LocationException {
	    // Vérifie si un utilisateur est connecté
	    Utilisateur utilisateur = gestionUtilisateur.getUtilisateurConnecte();
	    if (utilisateur == null) {
	        throw new NonConnecteException("Aucun utilisateur n'est connecté. Impossible de terminer une location.");
	    }

	    // Vérifie si le film fait partie des films en location
	    if (!utilisateur.getFilmsEnLocation().contains(film)) {
	        throw new LocationException("Ce film n'est pas actuellement en location par l'utilisateur.");
	    }

	    // Retire le film de la liste des films en location
	    utilisateur.retirerFilmEnLocation(film);
	}


	@Override
	public boolean estLouable(Film film) throws NonConnecteException {
	    // Vérifie si un utilisateur est connecté
	    if (gestionUtilisateur.getUtilisateurConnecte() == null) {
	        throw new NonConnecteException("Aucun utilisateur n'est connecté.");
	    }

	    // Vérifie si le film est ouvert à la location via GestionFilm
	    return film.isEstOuvertalocation();
	}


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


	public void ajouterEvaluation(Film film, Evaluation eval) throws NonConnecteException, LocationException {
        Utilisateur utilisateurConnecte = gestionUtilisateur.getUtilisateurConnecte();
        if (utilisateurConnecte == null) {
            throw new NonConnecteException("Aucun utilisateur n'est connecté.");
        }
        else
        {
        	if(!utilisateurConnecte.gethistoriqueFilmsEnLocation().contains(film)) {
                throw new LocationException("vous pouvez pas evalue ce film, il existe pas "
                		+ "dans votre historique de location");
	
        	}
        	if(utilisateurConnecte.getEvaluations().contains(eval)) {
        		 throw new LocationException("vous avez deja evalue ce film");
        	}
        }
        utilisateurConnecte.ajouterEvaluation(eval);
        film.ajouterEvaluation(utilisateurConnecte, eval);
    }
     
	
	
	 @Override
	    public void modifierEvaluation(Film film, Evaluation eval) throws NonConnecteException, LocationException {
	        Utilisateur utilisateurConnecte = gestionUtilisateur.getUtilisateurConnecte();
	        if (utilisateurConnecte == null) {
	            throw new NonConnecteException("Aucun utilisateur n'est connecté.");
	        }
	         
	        Evaluation evaluation = utilisateurConnecte.getEvaluationParFilm(film);
	       
	        	if(evaluation == null) {
	                throw new LocationException("evaluation n'existe pas");
		
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
   * @see location.Genre
   */
  
  @Override
  public Set<Film> ensembleFilmsGenre(String genre) {
    try {
      Genre genreEnum = Genre.valueOf(genre.toUpperCase());
      return ensembleFilmsGenre(genreEnum);
    } catch (IllegalArgumentException e) {
      return null; // Genre invalide
    }
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
