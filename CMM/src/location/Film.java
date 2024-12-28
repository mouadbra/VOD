package location;

//import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description des informations d'un film : titre, réalisateur, année, âge limite,
 * genres, acteurs, statut de location et évaluations.
 *
 * @author Mouad Brahmi
 */
public class Film {

  /**
   * Le titre du film.
   */
  private String titre;

  /**
   * Le réalisateur du film.
   */
  private Artiste realisateur;

  /**
   * L'année de réalisation du film.
   */
  private int annee;

  /**
   * L'âge minimum requis pour visionner le film.
   */
  private int ageLimite;

  /**
   * Les genres associés au film.
   */
  private Set<Genre> genres;

  /**
   * Les acteurs jouant dans le film.
   */
  private Set<Artiste> acteurs;

  /**
   * Indique si le film est disponible à la location.
   */
  private boolean estOuvertalocation;

  /**
   * Les évaluations données au film.
   */
  private List<Evaluation> evaluations;
  
  /**
   * L'affiche du film (chemin d'accès à une image).
   */
  private String affiche;


  /**
   * Crée un film avec toutes ses informations obligatoires.
   *
   * @param titre       le titre du film
   * @param realisateur le réalisateur du film
   * @param annee       l'année de réalisation du film
   * @param ageLimite   l'âge minimum requis pour visionner le film
   * @param genres      les genres associés au film
   */
  public Film(String titre, Artiste realisateur, int annee, int ageLimite, Set<Genre> genres) {
    if (titre == null) {
      throw new NullPointerException("Le titre ne peut pas être null.");
    }
    if (realisateur == null) {
      throw new NullPointerException("Le réalisateur ne peut pas être null.");
    }
    if (annee <= 0) {
      throw new IllegalArgumentException("L'année doit être un entier positif.");
    }
    if (ageLimite < 0) {
      throw new IllegalArgumentException("L'âge limite ne peut pas être négatif.");
    }
    this.titre = titre;
    this.realisateur = realisateur;
    this.annee = annee;
    this.ageLimite = ageLimite;
    this.genres = new HashSet<>(genres);
    this.acteurs = new HashSet<>();
    this.estOuvertalocation = false;
    this.evaluations = new ArrayList<>();
  }
  /**
   * Crée un film avec toutes ses informations obligatoires.
   *
   * @param titre       le titre du film
   * @param realisateur le réalisateur du film
   * @param annee       l'année de réalisation du film
   * @param ageLimite   l'âge minimum requis pour visionner le film
   */
  
  public Film(String titre, Artiste realisateur, int annee, int ageLimite) {
    if (titre == null) {
      throw new NullPointerException("Le titre ne peut pas être null.");
    }
    if (realisateur == null) {
      throw new NullPointerException("Le réalisateur ne peut pas être null.");
    }
    if (annee <= 0) {
      throw new IllegalArgumentException("L'année doit être un entier positif.");
    }
    if (ageLimite < 0) {
      throw new IllegalArgumentException("L'âge limite ne peut pas être négatif.");
    }
    this.titre = titre;
    this.realisateur = realisateur;
    this.annee = annee;
    this.ageLimite = ageLimite;
    this.genres = new HashSet<>();
    this.acteurs = new HashSet<>();
    this.estOuvertalocation = false;
    this.evaluations = new ArrayList<>();
  }




  /**
   * Renvoie le titre du film.
   *
   * @return le titre du film
   */
  public String getTitre() {
    return titre;
  }

  /**
   * Renvoie le réalisateur du film.
   *
   * @return le réalisateur du film
   */
  public Artiste getRealisateur() {
    return realisateur;
  }

  /**
   * Renvoie l'année de réalisation du film.
   *
   * @return l'année de réalisation du film
   */
  public int getAnnee() {
    return annee;
  }

  /**
   * Renvoie l'âge minimum requis pour visionner le film.
   *
   * @return l'âge limite du film
   */
  public int getAgeLimite() {
    return ageLimite;
  }

  /**
   * Renvoie les genres associés au film.
   *
   * @return l'ensemble non modifiable des genres du film
   */
  public Set<Genre> getGenres() {
    return genres;
  }

  /**
   * Renvoie les acteurs du film.
   *
   * @return l'ensemble non modifiable des acteurs du film
   */
  public Set<Artiste> getActeurs() {
    return acteurs;
  }

  /**
   * Indique si le film est disponible à la location.
   *
   * @return true si le film est ouvert à la location, false sinon
   */
  public boolean isEstOuvertalocation() {
    return estOuvertalocation;
  }
  /**
   * modifie l'etat de  location.
   *
   * 
   */
  
  public void modifierLocation(boolean etat) {
    estOuvertalocation = !etat;
  }
  /**
   * set l'etat de  location.
   *
   * 
   */
  
  public void setLocation(boolean etat) {
    estOuvertalocation = etat;
  }
  

  /**
   * Renvoie les évaluations du film.
   *
   * @return la liste non modifiable des évaluations du film
   */
  public List<Evaluation> getEvaluations() {
    return evaluations;
  }

  /**
   * Renvoie la moyenne d'évaluation du film.
   *
   * @return la moyenne des évaluations du film
   */
  public double calculmoyenneEval() {
    double somme = 0;
    if (this.evaluations.isEmpty()) {
      return 0;
    } else {
      for (Evaluation evaluation : this.evaluations) {
        somme += evaluation.getNote();
      }
    }
    return somme / this.evaluations.size();
  }
  
  /**
   * Définit l'affiche du film.
   *
   * @param affiche le chemin ou l'URL de l'affiche du film
   */
  public void setAffiche(String affiche) {
    if (affiche == null || affiche.trim().isEmpty()) {
      throw new IllegalArgumentException("L'affiche ne peut pas être vide ou nulle.");
    }
    this.affiche = affiche;
  }

  /**
   * Renvoie l'affiche du film.
   *
   * @return l'affiche du film
   */
  public String getAffiche() {
    return affiche;
  }  

  
  
  
  
  
  
  
  
  
  /**
   * Supprime une évaluation associée à ce film.
   *
   * @param evaluation l'évaluation à supprimer
   * @throws IllegalArgumentException si l'évaluation est nulle 
   *         ou ne fait pas partie des évaluations du film
   */
  public void supprimerEvaluation(Evaluation evaluation) {
    if (evaluation == null) {
      throw new IllegalArgumentException("L'évaluation à supprimer ne peut pas être nulle.");
    }
    if (!evaluations.contains(evaluation)) {
      throw new IllegalArgumentException("L'évaluation spécifiée ne fait pas partie"
      + " des évaluations de ce film.");
    }
    evaluations.remove(evaluation);
  }

  
  /**
   * Ajoute une évaluation pour ce film.
   *
   * @param utilisateur l'utilisateur qui évalue le film
   * @param evaluation l'évaluation à ajouter
   * @throws IllegalArgumentException si l'utilisateur ou l'évaluation est null
   */
  public void ajouterEvaluation(Utilisateur utilisateur, Evaluation evaluation) {
    if (utilisateur == null) {
      throw new IllegalArgumentException("L'utilisateur ne peut pas être null.");
    }
    if (evaluation == null) {
      throw new IllegalArgumentException("L'évaluation ne peut pas être null.");
    }

    // Ajouter l'évaluation à la liste des évaluations du film
    evaluations.add(evaluation);
  }

  
  
  
  
  
  
  
  
  
  @Override
  public String toString() {
    return "Film{" 
      +
        "titre='" + titre + '\'' 
      +
        ", realisateur=" + realisateur
      +
        ", annee=" + annee 
      +
        ", ageLimite=" + ageLimite 
      +
        ", genres=" + genres 
      +
        ", acteurs=" + acteurs 
      +
        ", estOuvertalocation=" + estOuvertalocation 
      +
        ", evaluations=" + evaluations
      +
        
      ", affiche='" + (affiche != null ? affiche : "Pas d'affiche") + '\'' 
      
      +
        '}';
  }
}
