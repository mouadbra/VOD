package location;

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
	    if (gestionUtilisateur.getUtilisateurConnecte() == null) {
	        throw new NonConnecteException("Aucun utilisateur n'est connecté.");
	    }
	    
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
        film.ajouterEvaluation(utilisateurConnecte, film, eval);
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

	@Override
	public Set<Film> ensembleFilms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Artiste> ensembleActeurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Artiste> ensembleRealisateurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Artiste getActeur(String nom, String prenom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Artiste getRealisateur(String nom, String prenom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film getFilm(String titre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Film> ensembleFilmsRealisateur(String nom, String prenom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Film> ensembleFilmsActeur(Artiste acteur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Film> ensembleFilmsActeur(String nom, String prenom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Film> ensembleFilmsGenre(Genre genre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Film> ensembleFilmsGenre(String genre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Evaluation> ensembleEvaluationsFilm(Film film) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Evaluation> ensembleEvaluationsFilm(String titre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double evaluationMoyenne(Film film) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluationMoyenne(String titre) {
		// TODO Auto-generated method stub
		return 0;
	}

}
