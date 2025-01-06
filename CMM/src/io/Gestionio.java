package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import location.Artiste;
import location.Film;
import location.GestionFilm;
import location.GestionUtilisateur;
import location.InterUtilisateur;
import location.Utilisateur;

public class Gestionio implements InterSauvegarde , Serializable {
    private static final long serialVersionUID = 1L;
    
    private GestionUtilisateur gestionUtilisateur;
    private GestionFilm gestionFilm;
    
    
    public Gestionio(GestionUtilisateur gestionUtilisateur, GestionFilm gestionFilm) {
        this.gestionUtilisateur = gestionUtilisateur;
        this.gestionFilm = gestionFilm;
      }
    
    
    
@Override
public void sauvegarderDonnees(String nomFichier) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
        HashMap<String, Object> donnees = new HashMap<>();
        
        // Sauvegarde des films
        donnees.put("films", gestionFilm.ensembleFilms());
        
        // Sauvegarde des artistes
        donnees.put("artistes", gestionFilm.getArtistes());  // Ajoutez une méthode getArtistes()
        
        // Sauvegarde des utilisateurs
        donnees.put("utilisateurs", gestionUtilisateur.getUtilisateurs());
        
        oos.writeObject(donnees);
    }
}

@Override
public void chargerDonnees(String nomFichier) throws IOException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> donnees = (HashMap<String, Object>) ois.readObject();

            // Récupération des artistes (avant les films pour maintenir les références)
            Set<Artiste> artistes = (Set<Artiste>) donnees.get("artistes");
            gestionFilm.setArtistes(artistes);  // Ajoutez une méthode setArtistes()

            // Récupération des films
            Set<Film> films = (Set<Film>) donnees.get("films");
            gestionFilm.setFilms(films);

            // Récupération des utilisateurs
            Set<Utilisateur> utilisateurs = (Set<Utilisateur>) donnees.get("utilisateurs");
            gestionUtilisateur.setUtilisateurs(utilisateurs);

        } catch (ClassNotFoundException e) {
            throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}

}

