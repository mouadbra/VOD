package location;

import java.util.Objects;

/**
 * Description de la classe evaluation : note entre 0 et 5, 
 * utilisateur de la classe Utilisateur qui met une note, 
 * film à noter de la classe Film,
 * commentaire(que si l'utilisateur est connecté et a déjà le film ).
 *
 * @author Bouberraga Cherif
 */

public class Evaluation {
  /**
  * La note ajoutée entre 0 et 5.
  */
  private int note;
  /**
  * Le commentaire ajouté.
  */
  private String commentaire;
  /**
   * L'utilisateur de la classe Utilisateur.
   */
  private Utilisateur utilis;
  /**
   * Le film de la table Film.
   */
  private Film film; // Nouvel attribut pour lier l'évaluation à un film.

  /**
   * Créer une évaluation avec commentaire.
   *
   * @param note       la note ajoutée
   * @param commentaire le commentaire ajouté 
   * @param utilis       l'utilisateur déjà construit dans la classe Utilisateur 
   * @param film   le film déjà créé dans la classe Film 
   */
  public Evaluation(int note, String commentaire, Utilisateur utilis, Film film) {

    if (utilis == null) {
      throw new NullPointerException("L'utilisateur ne doit pas être null.");
    }
    if (film == null) {
      throw new NullPointerException("Le film ne doit pas être null.");
    }
    if (note < 0 || note > 5) {
      throw new IllegalArgumentException("La note doit être entre 0 et 5.");
    }
        
    this.note = note;
    this.commentaire = commentaire;
    this.utilis = utilis;
    this.film = film;
  }
    

  /**
   * Créer une évaluation sans commentaire.
   *
   * @param note       la note ajoutée. 
   * @param utilis  l'utilisateur qui effectue l'evaluation et/ou l'ajout du commentaire. 
   * @param film   le film que l'utilisateur veut evaluer et/ou ajouter un commentaire.
   */
  public Evaluation(int note, Utilisateur utilis, Film film) {
    if (utilis == null) {
      throw new NullPointerException("L'utilisateur ne doit pas être null.");
    }
    if (film == null) {
      throw new NullPointerException("Le film ne doit pas être null.");
    }

    if (note < 0 || note > 5) {
      throw new IllegalArgumentException("La note doit être entre 0 et 5.");
    }

    this.note = note;
    this.utilis = utilis;
    this.film = film;
    this.commentaire = null;  // commentaire vide par défaut
  }
  
  /**
   * Renvoie la note ajoutée du film.
   *
   * @return la note ajouté du film par l'utilisateur.
   */
  
  public int getNote() {
    return note;
  }
  /**
   * Met à jour la note ajoutée par l'utilisateur.
   *
   * @param note la nouvelle note ajoutée.
   */
  
  public void setNote(int note) {
    if (note < 0 || note > 5) {
      throw new IllegalArgumentException("La note doit être entre 0 et 5.");
    }
    this.note = note;
  }
  /**
   * Renvoie le commentaire ajouté.
   *
   * @return le commentaire ajouté par l'utilisateur pour le film.
   */
  
  public String getCommentaire() {
    return commentaire;
  }

  /**
   * Met à jour la commentaire ajouté par l'utilisateur.
   *
   * @param commentaire le nouveau commentaire ajouté.
   */
  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }
  /**
   * Renvoie le pseudo de l'utilisateur qui effectue l'evluation et/ou l'ajout du commentaire.
   *
   * @return le commentaire ajouté par l'utilisateur pour le film.
   */
  
  public String getUtilisateurPseudo() {
    return utilis.getPseudo();  
  }
  /**
   * Renvoie le film evalué.
   *
   * @return le film que l'utilisateur evalue et/ou ajoute un commentaire.
   */

  public Film getFilm() {
    return film;
  }

  /**
   * Met à jour la commentaire ajouté par l'utilisateur.
   *
   * @param film le nouveau film que l'utilisateur va evaluer.
   */
  public void setFilm(Film film) {
    this.film = film;
  }

  /**
   * l'affichage.

   */
  public void afficherEvaluation() {
    System.out.println("Film : " + film.getTitre() 
        + " | Note : " + note 
        + " | Commentaire : " + commentaire 
        + " | Utilisateur : " + utilis.getPseudo());
  }
  
  
  
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Evaluation that = (Evaluation) o;
      return film.equals(that.film) && utilis.equals(that.utilis);
  }

  @Override
  public int hashCode() {
      return Objects.hash(film, utilis);
  }

  
}
