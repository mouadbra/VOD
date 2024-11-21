package location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un artiste (acteur ou réalisateur) avec ses informations
 * personnelles et sa filmographie.
 *
 * @author Bouberraga Cherif
 */
public class Artiste {

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
    this.filmographie = new ArrayList<>();
  }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom != null && !nom.isEmpty()) {
            this.nom = nom;
        }
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        if (prenom != null) {
            this.prenom = prenom;
        }
    }

    public String getNationalite() {
        return nationalite;
    }

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
        return Objects.equals(nom, artiste.nom) &&
               Objects.equals(prenom, artiste.prenom) &&
               Objects.equals(nationalite, artiste.nationalite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom, nationalite);
    }

    @Override
    public String toString() {
        return "Artiste{" +
               "nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", nationalite='" + nationalite + '\'' +
               ", filmographie=" + filmographie +
               '}';
    }
}
