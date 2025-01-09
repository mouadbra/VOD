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
import java.util.Map;
import java.util.Set;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.GestionFilm;
import location.GestionUtilisateur;
import location.Gestionnaire;
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
    
  //private GestionUtilisateur gestionUtilisateur;
  GestionUtilisateur gestionUtilisateur = GestionUtilisateur.getInstance();
  private GestionFilm gestionFilm;
  private Gestionnaire gestion;
  
  /**
   * Constructeur .
   */
  public Gestionio(GestionUtilisateur gestionUtilisateur, GestionFilm gestionFilm, Gestionnaire gestion) {
    //this.gestionUtilisateur = gestionUtilisateur;
    this.gestionFilm = gestionFilm;
    this.gestionUtilisateur = GestionUtilisateur.getInstance();
    this.gestion = gestion;
    
        
    new File(POSTERS_DIRECTORY).mkdirs();
  }

  @Override
    public void sauvegarderDonnees(String nomFichier) throws IOException {
	  System.out.println("Debug - État de GestionUtilisateur avant sauvegarde:");
	    System.out.println("Instance de gestionUtilisateur: " + (gestionUtilisateur != null ? "non null" : "null"));
	    Set<Utilisateur> utilisateursASauvegarder = gestionUtilisateur.getUtilisateurs();
	    System.out.println("Utilisateurs à sauvegarder: " + utilisateursASauvegarder);
	    for (Utilisateur u : utilisateursASauvegarder) {
	        System.out.println("- Utilisateur: " + u.getPseudo());
	    }
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
      
      Set<Utilisateur> utilisateurs = gestionUtilisateur.getUtilisateurs();
      //System.out.println("Nombre d'utilisateurs à sauvegarder : " + utilisateurs.size());
      System.out.println("Collection utilisateurs récupérée de GestionUtilisateur : " + 
              (utilisateurs != null ? utilisateurs.size() : "null"));
      if (utilisateurs != null) {
          for (Utilisateur u : utilisateurs) {
              System.out.println("Utilisateur trouvé : " + u.getPseudo());
          }
      }
      donnees.put("utilisateurs", gestionUtilisateur.getUtilisateurs());
      System.out.println("Sauvegarde - Contenu de la map : " + donnees.get("utilisateurs"));
      
      
      Map<Film, Set<Evaluation>> evaluations = new HashMap<>();
      for (Film film : films) {
          Set<Evaluation> evals = gestion.ensembleEvaluationsFilm(film);
          if (evals != null) {
              evaluations.put(film, evals);
          }
      }
      donnees.put("evaluations", evaluations); 

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
        
        if (utilisateurs != null) {
            System.out.println("Nombre d'utilisateurs chargés : " + utilisateurs.size());
            gestionUtilisateur.setUtilisateurs(utilisateurs);
        } else {
            System.out.println("Aucun utilisateur trouvé dans le fichier");
        }
        @SuppressWarnings("unchecked")
        Map<Film, Set<Evaluation>> evaluations = (Map<Film, Set<Evaluation>>) donnees.get("evaluations");
        if (evaluations != null) {
            System.out.println("Chargement des évaluations...");
            for (Map.Entry<Film, Set<Evaluation>> entry : evaluations.entrySet()) {
                Film film = entry.getKey();
                Set<Evaluation> filmEvaluations = entry.getValue();
                // Assurez-vous que le film existe dans gestionFilm
                Film filmExistant = gestionFilm.getFilm(film.getTitre());
                if (filmExistant != null && filmEvaluations != null) {
                    for (Evaluation eval : filmEvaluations) {
                    	String pseudoUtilisateur = eval.getUtilisateurPseudo();
                        Utilisateur utilisateur = gestionUtilisateur.getUtilisateurParPseudo(pseudoUtilisateur);
                        filmExistant.ajouterEvaluation(utilisateur,eval);
                    }
                }
            }
            System.out.println("Évaluations chargées avec succès");
        } else {
            System.out.println("Aucune évaluation trouvée dans le fichier");
        }
        

      } catch (ClassNotFoundException e) {
        throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
      }
    }
  }
}