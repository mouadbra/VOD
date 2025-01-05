package location;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant un utilisateur du système de location de films.
 * Contient les informations personnelles de l'utilisateur, ses films en location,
 * ses évaluations, et son état de connexion.
 */
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
  private String pseudo;
  private String motDePasse;
  private InformationPersonnelle info;
  private Set<Film> filmsEnLocation;
  private Set<Film> historiquefilmsEnLocation;

  private Set<Evaluation> evaluations;
  private boolean estConnecte;

  /**
     * Constructeur de la classe Utilisateur.
     *
     * @param pseudo Le pseudo unique de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @param info Les informations personnelles de l'utilisateur.
     */
  public Utilisateur(String pseudo, String motDePasse, InformationPersonnelle info) {
    if (pseudo == null || motDePasse == null || info == null) {
      throw new IllegalArgumentException("Les arguments pseudo, motDePasse et info"
      + " ne doivent pas être null.");
    }
    this.pseudo = pseudo;
    this.motDePasse = motDePasse;
    this.info = info;
    this.filmsEnLocation = new HashSet<>();
    this.historiquefilmsEnLocation = new HashSet<>();
    this.evaluations = new HashSet<>();
    this.estConnecte = false;
  }


  /**
     * Retourne le pseudo de l'utilisateur.
     *
     * @return Le pseudo de l'utilisateur.
     */
  public String getPseudo() {
    return pseudo;
  }

  /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
  public String getMotDePasse() {
    return motDePasse;
  }

  /**
     * Retourne les informations personnelles de l'utilisateur.
     *
     * @return Les informations personnelles de l'utilisateur.
     */
  public InformationPersonnelle getInfo() {
    return info;
  }

  /**
     * Retourne l'ensemble des films actuellement en location par l'utilisateur.
     *
     * @return Un ensemble de films en location.
     */
  public Set<Film> getFilmsEnLocation() {
    return filmsEnLocation;
  }

  /**
    * Retourne toutes les films louer deja par l'utilisateur.
    *
    * @return Un ensemble de films.
    */
    
  public Set<Film> gethistoriqueFilmsEnLocation() {
    return historiquefilmsEnLocation;
  }
   
  /**
     * Retourne l'ensemble des évaluations faites par l'utilisateur.
     *
     * @return Un ensemble d'évaluations.
     */
  public Set<Evaluation> getEvaluations() {
    return evaluations;
  }

  /**
     * Retourne l'état de connexion de l'utilisateur.
     *
     * @return true si l'utilisateur est connecté, false sinon.
     */
  public boolean isEstConnecte() {
    return estConnecte;
  }


  /**
     * Met à jour le pseudo de l'utilisateur.
     *
     * @param pseudo Le nouveau pseudo de l'utilisateur.
     */
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  /**
     * Met à jour le mot de passe de l'utilisateur.
     *
     * @param motDePasse Le nouveau mot de passe de l'utilisateur.
     */
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  /**
     * Met à jour l'état de connexion de l'utilisateur.
     *
     * @param estConnecte Le nouvel état de connexion (true si connecté, false sinon).
     */
  public void setEstConnecte(boolean estConnecte) {
    this.estConnecte = estConnecte;
  }
  
  /**
   * Ajoute un film à la liste des films en location de l'utilisateur.
   * Ajoute un film à l'historique des films loués par l'utilisateur.
   *
   * @param film Le film à ajouter.
   */
  public void ajouterFilmenLocation(Film film) {

    filmsEnLocation.add(film);
    historiquefilmsEnLocation.add(film);

  }


  /**
     * Retire un film de la liste des films en location de l'utilisateur.
     *
     * @param film Le film à retirer.
     */
  public void retirerFilmEnLocation(Film film) {
    filmsEnLocation.remove(film);
  }

  /**
     * Ajoute une évaluation à la liste des évaluations de l'utilisateur.
     *
     * @param evaluation L'évaluation à ajouter.
     */
  public void ajouterEvaluation(Evaluation evaluation) {
    evaluations.add(evaluation);
  }
  /**
     * supprimer une évaluation à la liste des évaluations de l'utilisateur.
     *
     * @param evaluation L'évaluation à supprimer.
     */
  
  public void supprimerEvaluation(Evaluation evaluation) {
    evaluations.remove(evaluation);
  }
    
    
  /**
     * Recherche un evaluqtion par son film.
     *
     * @param film Le film evalue par l'utilisateur.
     * @return evaluation correspondant, ou null si non trouvé.
     */
  public Evaluation getEvaluationParFilm(Film film) {
    for (Evaluation v : evaluations) {
      if (v.getFilm().equals(film)) {
        return v;
      }
    }
    return null;
  
  }
  
  
  
}
