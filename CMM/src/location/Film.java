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
   * Crée un film avec toutes ses informations obligatoires.
   *
   * @param titre       le titre du film
   * @param realisateur le réalisateur du film
   * @param annee       l'année de réalisation du film
   * @param ageLimite   l'âge minimum requis pour visionner le film
   * @param genres      les genres associés au film
   */
  public Film(String titre, Artiste realisateur, int annee, int ageLimite, Set<Genre> genres) {
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

  @Override
  public String toString() {
    return "Film{" +
        "titre='" + titre + '\'' +
        ", realisateur=" + realisateur +
        ", annee=" + annee +
        ", ageLimite=" + ageLimite +
        ", genres=" + genres +
        ", acteurs=" + acteurs +
        ", estOuvertalocation=" + estOuvertalocation +
        ", evaluations=" + evaluations +
        '}';
  }
}
