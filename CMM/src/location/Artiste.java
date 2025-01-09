package location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un artiste (acteur ou réalisateur) avec ses informations
 * personnelles et sa filmographie.
 *
 * @author Bouberraga Cherif
 */
public class Artiste implements Serializable {
  private static final long serialVersionUID = 1L;


  /**
  * Le nom de l'artiste.
  */
  private String nom;
  /**
   * Le prenom de l'artiste.
   */
  private String prenom;
  /**
   * La nationalité de l'artiste.
   */
  private String nationalite;
  /**
   * La liste des films associés à cet artiste.
   */
  private List<Film> filmographie; // Liste des films associés à cet artiste

  /**
   * Créer une évaluation avec commentaire.
   *
   * @param nom       le nom de l'artiste.
   * @param prenom le prénom de l'artiste.
   * @param nationalite la nationalité de l'artiste. 
   */
  public Artiste(String nom, String prenom, String nationalite) {  
    this.nom = nom;
    this.prenom = prenom;
    this.nationalite = nationalite;
    this.filmographie = new ArrayList<>(); // une liste initialisée vide.
  }

  /**
 * Renvoie le nom de l'artiste.
 *
 * @return le nom de l'artiste.
 */
  public String getNom() {
    return nom;
  }

  /**
   * Met à jour du nom ajouté de l'artiste.
   *
   * @param nom le nouveau nom modifié de l'artiste.
   */
  public void setNom(String nom) {
    if (nom != null && !nom.isEmpty()) {
      this.nom = nom;
    }
  }

  /**
   * Renvoie le prénom de l'artiste.
   *
   * @return le prénom de l'artiste.
   */
  public String getPrenom() {
    return prenom;
  }

  /**
   * Met à jour du prenom de l'artiste ajouté.
   *
   * @param prenom le nouveau nom de l'artiste modifié.
   */
  public void setPrenom(String prenom) {
    if (prenom != null) {       
      this.prenom = prenom;
    }
  }

  /**
   * Renvoie la nationalité de l'artiste.
   *
   * @return la nationalité de l'artiste.
   */
  public String getNationalite() {
    return nationalite;
  }

  /**
   * Met à jour de la nationalité de l'artiste ajoutée.
   *
   * @param nationalite la nouvelle nationalité modifié.
   */
  public void setNationalite(String nationalite) {
    if (nationalite != null && !nationalite.isEmpty()) {
      this.nationalite = nationalite;
    }
  }

  public List<Film> getFilmographie() {
    return new ArrayList<>(filmographie); // Retourne une copie pour éviter la modification externe
  }

  // Méthodes
  /**
   * Ajoute un film à la filmographie de l'artiste.
   *
   * @param film Le film à ajouter
   */
  public void ajouterFilm(Film film) {
    if (film != null && !filmographie.contains(film)) {
      filmographie.add(film);
    }
  }

  

  

  // Redéfinitions
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Artiste artiste = (Artiste) obj;
    return Objects.equals(nom, artiste.nom) && Objects.equals(prenom, artiste.prenom) 
      && Objects.equals(nationalite, artiste.nationalite);
               
  }

  @Override
  public int hashCode() {
    return Objects.hash(nom, prenom, nationalite);
  }

  @Override
  public String toString() {
    return "Artiste{" 
            + "nom='" + nom + '\'' 
            + ", prenom='" + prenom + '\'' 
            + ", nationalite='" + nationalite + '\'' 
            + ", filmographie=" + filmographie 
           +  '}';
  }
}
