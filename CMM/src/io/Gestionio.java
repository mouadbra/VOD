package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Set;
import location.Artiste;
import location.Film;
import location.GestionFilm;
import location.GestionUtilisateur;
import location.Utilisateur;

/**
 * Classe permettant la sauvegarde et le chargement des utilisateurs, films et artistes.
 * Implémente InterSauvegarde et est sérialisable..
 *
 * @author Mouad Brahmi
 * 
 */
public class Gestionio implements InterSauvegarde, Serializable {
  private static final long serialVersionUID = 1L;
  private static final String POSTERS_DIRECTORY = "posters/";
    
  private GestionUtilisateur gestionUtilisateur;
  private GestionFilm gestionFilm;
  //private Gestionnaire gestion;
  
  /**
   * Constructeur .
   */
  public Gestionio(GestionUtilisateur gestionUtilisateur, GestionFilm gestionFilm) {
    this.gestionUtilisateur = gestionUtilisateur;
    this.gestionFilm = gestionFilm;
    //this.gestion = gestion;
    
        
    new File(POSTERS_DIRECTORY).mkdirs();
  }

  @Override
    public void sauvegarderDonnees(String nomFichier) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
      HashMap<String, Object> donnees = new HashMap<>();
            
      // Save films and copy their posters
      Set<Film> films = gestionFilm.ensembleFilms();
      for (Film film : films) {
        if (film.getAffiche() != null && !film.getAffiche().isEmpty()) {
          File sourceFile = new File(film.getAffiche());
          if (sourceFile.exists()) {
            String newPosterPath = POSTERS_DIRECTORY 
                +
                film.getTitre()
                + "_" 
                + 
                                             sourceFile.getName();
            Files.copy(sourceFile.toPath(), new File(newPosterPath).toPath(), 
                                 StandardCopyOption.REPLACE_EXISTING);
            film.setAffiche(newPosterPath);
          }
        }
      }
      donnees.put("films", films);

      // Save other data
      donnees.put("artistes", gestionFilm.getArtistes());
      donnees.put("utilisateurs", gestionUtilisateur.getUtilisateurs());
      
      /*Map<Film, Set<Evaluation>> evaluations = new HashMap<>();
      for (Film film : films) {
          Set<Evaluation> evals = gestion.ensembleEvaluationsFilm(film);
          if (evals != null) {
              evaluations.put(film, evals);
          }
      }
      donnees.put("evaluations", evaluations); */

      oos.writeObject(donnees);
    }
  }

  @Override
    public void chargerDonnees(String nomFichier) throws IOException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
      try {
        @SuppressWarnings("unchecked")
                HashMap<String, Object> donnees = (HashMap<String, Object>) ois.readObject();

        @SuppressWarnings("unchecked")
                Set<Artiste> artistes = (Set<Artiste>) donnees.get("artistes");
        gestionFilm.setArtistes(artistes);

               
        @SuppressWarnings("unchecked")
                Set<Film> films = (Set<Film>) donnees.get("films");
        for (Film film : films) {
          if (film.getAffiche() != null && !film.getAffiche().isEmpty()) {
            File posterFile = new File(film.getAffiche());
            if (!posterFile.exists()) {
              film.setAffiche(null); // Clear reference if file doesn't exist
            }
          }
        }
        gestionFilm.setFilms(films);

              
        @SuppressWarnings("unchecked")
                Set<Utilisateur> utilisateurs = (Set<Utilisateur>) donnees.get("utilisateurs");
        gestionUtilisateur.setUtilisateurs(utilisateurs);
        
        

      } catch (ClassNotFoundException e) {
        throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
      }
    }
  }
}