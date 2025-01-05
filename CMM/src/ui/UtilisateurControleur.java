package ui;

import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.GestionFilm;
import location.GestionUtilisateur;
import location.Gestionnaire;
import location.InformationPersonnelle;
import location.LocationException;
import location.NonConnecteException;
import location.Utilisateur;
import ui.AdministrationControleur;



/**
 * Controleur JavaFX de la fenêtre utilisateur.
 *
 * @author Eric Cariou
 *
 */
public class UtilisateurControleur {
  
  @FXML
  private CheckBox checkFilmLouable;
  
  @FXML
  private TextField entreeAdresseUtilisateur;
  
  @FXML
  private TextField entreeAgeLimiteFilm;
  
  @FXML
  private TextField entreeAgeUtilisateur;
  
  @FXML
  private TextField entreeAnneeFilm;
  
  @FXML
  private TextField entreeAuteurEvaluation;
  
  @FXML
  private TextField entreeEvaluationMoyenne;
  
  @FXML
  private TextField entreeGenresFilm;
  
  @FXML
  private TextField entreeMotDePasseUtilisateur;
  
  @FXML
  private TextField entreeNationaliteArtiste;
  
  @FXML
  private TextField entreeNomArtiste;
  
  @FXML
  private TextField entreeNomPrenomRealisateurFilm;
  
  @FXML
  private TextField entreeNomUtilisateur;
  
  @FXML
  private TextField entreePrenomArtiste;
  
  @FXML
  private TextField entreePrenomUtilisateur;
  
  @FXML
  private TextField entreePseudoUtilisateur;
  
  @FXML
  private TextField entreeTitreFilm;
  
  @FXML
  private Label labelListeFilms;
  
  @FXML
  private Label labelListeArtistes;
  
  @FXML
  private ListView<String> listeArtistes;
  
  @FXML
  private ListView<String> listeEvaluations;
  
  @FXML
  private ListView<String> listeFilms;
  
  @FXML
  private ListView<String> listeFilmsEnLocation;
  
  @FXML
  private ListView<String> listeGenresFilm;
  
  @FXML
  private ChoiceBox<Integer> listeNoteEvaluation;
  
  @FXML
  private TextArea texteCommentaire;
  
  @FXML
  private StackPane paneAffiche;
  Gestionnaire gestionnaire;
  GestionFilm gestionnaireFilm;
  GestionUtilisateur gestionnaireUtilisateur;

  @FXML
  void actionBoutonAfficherActeursFilmSelectionne(ActionEvent event) {
      String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
      if (filmSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un film !");
          return;
      }

      // Extraire le titre du film
      String titre = filmSelectionne.split(" \\(")[0]; // Diviser par " (" et prendre la première partie
      
      // Récupérer l'objet Film
      Film film = gestionnaire.getFilm(titre);
      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      listeArtistes.getItems().clear();
      Set<Artiste> acteurs = film.getActeurs();

      if (acteurs.isEmpty()) {
          afficherMessageErreur("Aucun acteur associé à ce film.");
      } else {
          for (Artiste acteur : acteurs) {
              listeArtistes.getItems().add(acteur.getNom() + " " + acteur.getPrenom());
          }
          afficherMessageSucces("Acteurs affichés pour le film sélectionné.");
      }
  }

  
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
      listeArtistes.getItems().clear();
      Set<Artiste> acteurs = gestionnaire.ensembleActeurs();
      
      if (acteurs == null || acteurs.isEmpty()) {
          afficherMessageErreur("Aucun acteur disponible !");
      } else {
          for (Artiste acteur : acteurs) {
              listeArtistes.getItems().add(acteur.getNom() + " " + acteur.getPrenom());
          }
          afficherMessageSucces("Liste des acteurs mise à jour.");
      }
  }

  
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
      listeArtistes.getItems().clear();
      Set<Artiste> realisateurs = gestionnaire.ensembleRealisateurs();
      
      if (realisateurs == null || realisateurs.isEmpty()) {
          afficherMessageErreur("Aucun réalisateur disponible !");
      } else {
          for (Artiste realisateur : realisateurs) {
              listeArtistes.getItems().add(realisateur.getNom() + " " + realisateur.getPrenom());
          }
          afficherMessageSucces("Liste des réalisateurs mise à jour.");
      }
  }

  
  @FXML
  void actionBoutonAfficherFilmLoue(ActionEvent event) throws NonConnecteException {
	  // Récupère le film sélectionné
	    String filmSelectionne = listeFilmsEnLocation.getSelectionModel().getSelectedItem();
	    if (filmSelectionne == null) {
	        afficherMessageErreur("Veuillez sélectionner un film !");
	        return;
	    }

       
	    String titre = filmSelectionne.split(" \\(")[0]; // Diviser par " (" et prendre la première partie

	      Film film = gestionnaire.getFilm(titre);
	    
	    
	    
	    if (film == null) {
	        afficherMessageErreur("Film introuvable !");
	        return;
	    }

	    // Remplit les champs avec les détails du film
	    entreeTitreFilm.setText(film.getTitre());
	    entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
	    entreeAgeLimiteFilm.setText(String.valueOf(film.getAgeLimite()));
	    entreeNomPrenomRealisateurFilm.setText(film.getRealisateur().getNom() + " " + film.getRealisateur().getPrenom());

	    // Concaténation des genres
	    String genres = String.join(", ", film.getGenres().stream()
	                                           .map(Genre::name)
	                                           .toList());
	    entreeGenresFilm.setText(genres);
    
  }

  
  @FXML
  void actionBoutonAfficherFilmRealisateurSelectionne(ActionEvent event) {
      String realisateurSelectionne = listeArtistes.getSelectionModel().getSelectedItem();
      if (realisateurSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un réalisateur !");
          return;
      }

      Artiste realisateur = gestionnaire.getRealisateur(realisateurSelectionne.split(" ")[0], 
                                                        realisateurSelectionne.split(" ")[1]);
      if (realisateur == null) {
          afficherMessageErreur("Réalisateur introuvable !");
          return;
      }

      listeFilms.getItems().clear();
      Set<Film> films = gestionnaire.ensembleFilmsRealisateur(realisateur);
      
      if (films == null || films.isEmpty()) {
          afficherMessageErreur("Aucun film trouvé pour ce réalisateur.");
      } else {
          for (Film film : films) {
              listeFilms.getItems().add(film.getTitre() + " (" + film.getAnnee() + ")");
          }
          afficherMessageSucces("Liste des films du réalisateur mise à jour.");
      }
  }

  
  
  @FXML
  void actionBoutonAfficherFilmsActeurSelectionne(ActionEvent event) {
	  String acteurSelectionne = listeArtistes.getSelectionModel().getSelectedItem();
      if (acteurSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un réalisateur !");
          return;
      }

      Artiste acteur = gestionnaire.getActeur(acteurSelectionne.split(" ")[0], 
                                                        acteurSelectionne.split(" ")[1]);
      if (acteur == null) {
          afficherMessageErreur("Acteur introuvable !");
          return;
      }

      listeFilms.getItems().clear();
      Set<Film> films = gestionnaire.ensembleFilmsActeur(acteur);
      
      if (films == null || films.isEmpty()) {
          afficherMessageErreur("Aucun film trouvé pour ce réalisateur.");
      } else {
          for (Film film : films) {
              listeFilms.getItems().add(film.getTitre() + " (" + film.getAnnee() + ")");
          }
          afficherMessageSucces("Liste des films du réalisateur mise à jour.");
      }
  }
  
  @FXML
  void actionBoutonAfficherFilmsGenre(ActionEvent event) {
      // Récupérer le genre sélectionné
      String genreSelectionne = listeGenresFilm.getSelectionModel().getSelectedItem();
      if (genreSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un genre !");
          return;
      }

      // Récupérer les films associés au genre
      Set<Film> films = gestionnaire.ensembleFilmsGenre(genreSelectionne.trim());
      listeFilms.getItems().clear();

      if (films == null || films.isEmpty()) {
          afficherMessageErreur("Aucun film trouvé pour ce genre.");
      } else {
          // Ajouter les films à la liste
          for (Film film : films) {
              listeFilms.getItems().add(film.getTitre() + " (" + film.getAnnee() + ")");
          }
          afficherMessageSucces("Liste des films du genre mise à jour.");
      }
  }


  @FXML
  void actionBoutonAfficherFilmsRealisateurSelectionne(ActionEvent event) {
      String realisateurSelectionne = listeArtistes.getSelectionModel().getSelectedItem();
      if (realisateurSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un réalisateur !");
          return;
      }

      String[] noms = realisateurSelectionne.split(" ");
      if (noms.length < 2) {
          afficherMessageErreur("Le nom et le prénom du réalisateur sont nécessaires !");
          return;
      }

      Artiste realisateur = gestionnaire.getRealisateur(noms[0], noms[1]);
      if (realisateur == null) {
          afficherMessageErreur("Réalisateur introuvable !");
          return;
      }

      listeFilms.getItems().clear();
      Set<Film> films = gestionnaire.ensembleFilmsRealisateur(realisateur);

      if (films == null || films.isEmpty()) {
          afficherMessageErreur("Aucun film trouvé pour ce réalisateur.");
      } else {
          for (Film film : films) {
              listeFilms.getItems().add(film.getTitre() + " (" + film.getAnnee() + ")");
          }
          afficherMessageSucces("Liste des films du réalisateur mise à jour.");
      }
  }

  
  @FXML
  void actionBoutonAfficherMonEvaluation(ActionEvent event) {
      String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
      if (filmSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un film !");
          return;
      }
      String titre = filmSelectionne.split(" \\(")[0]; // Diviser par " (" et prendre la première partie

      Film film = gestionnaire.getFilm(titre);
      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      Utilisateur utilisateur = gestionnaireUtilisateur.getUtilisateurConnecte();
	  Evaluation evaluation = utilisateur.getEvaluationParFilm(film);

	  if (evaluation == null) {
	      afficherMessageErreur("Vous n'avez pas encore évalué ce film.");
	  } else {
	      texteCommentaire.setText(evaluation.getCommentaire());
	      listeNoteEvaluation.setValue(evaluation.getNote());
	      afficherMessageSucces("Votre évaluation a été chargée.");
	  }
  }

  
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
      listeArtistes.getItems().clear();
      Set<Artiste> artistes = gestionnaireFilm.ensembleArtistes();
      artistes.addAll(gestionnaire.ensembleRealisateurs());

      if (artistes.isEmpty()) {
          afficherMessageErreur("Aucun artiste trouvé !");
      } else {
          for (Artiste artiste : artistes) {
              listeArtistes.getItems().add(artiste.getNom() + " " + artiste.getPrenom());
          }
          afficherMessageSucces("Liste des artistes mise à jour.");
      }
  }

  
  @FXML
  void actionBoutonAfficherTousFilms(ActionEvent event) {
      listeFilms.getItems().clear();
      Set<Film> films = gestionnaire.ensembleFilms();

      if (films == null || films.isEmpty()) {
          afficherMessageErreur("Aucun film disponible !");
      } else {
          for (Film film : films) {
              listeFilms.getItems().add(film.getTitre() + " (" + film.getAnnee() + ")");
          }
          afficherMessageSucces("Liste des films mise à jour.");
          remplirListeGenresFilm(); 
      }
  }

  
  @FXML
  void actionBoutonChercherActeur(ActionEvent event) {
      String nom = entreeNomArtiste.getText();
      String prenom = entreePrenomArtiste.getText();

      if (nom.isEmpty() || prenom.isEmpty()) {
          afficherMessageErreur("Veuillez entrer le nom et le prénom de l'acteur !");
          return;
      }

      Artiste acteur = gestionnaire.getActeur(nom, prenom);
      if (acteur == null) {
          afficherMessageErreur("Acteur introuvable !");
      } else {
          afficherMessageSucces("Acteur trouvé : " + acteur.getNom() + " " + acteur.getPrenom() + " (" + acteur.getNationalite() + ")");
      }
  }

  
  @FXML
  void actionBoutonChercherFilm(ActionEvent event) {
      String titre = entreeTitreFilm.getText().trim(); // Supprime les espaces superflus

      if (titre.isEmpty()) {
          afficherMessageErreur("Veuillez entrer le titre du film !");
          return;
      }

      Film film = gestionnaire.getFilm(titre);

      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      // Afficher le film dans la liste
      listeFilms.getItems().clear();
      listeFilms.getItems().add(film.getTitre() + " (" + film.getAnnee() + ")");

      // Remplir les champs d'information
      entreeTitreFilm.setText(film.getTitre());
      entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
      entreeAgeLimiteFilm.setText(String.valueOf(film.getAgeLimite()));
      entreeNomPrenomRealisateurFilm.setText(film.getRealisateur().getPrenom() + " " + film.getRealisateur().getNom());
      entreeGenresFilm.setText(film.getGenres().toString().replaceAll("[\\[\\]]", "")); // Supprime les crochets des genres

      afficherMessageSucces("Film trouvé : " + film.getTitre() + " (" + film.getAnnee() + ")");
  }


  
  @FXML
  void actionBoutonChercherRealisateur(ActionEvent event) {
      String nom = entreeNomArtiste.getText();
      String prenom = entreePrenomArtiste.getText();

      if (nom.isEmpty() || prenom.isEmpty()) {
          afficherMessageErreur("Veuillez entrer le nom et le prénom du réalisateur !");
          return;
      }

      Artiste realisateur = gestionnaire.getRealisateur(nom, prenom);
      if (realisateur == null) {
          afficherMessageErreur("Réalisateur introuvable !");
      } else {
          afficherMessageSucces("Réalisateur trouvé : " + realisateur.getNom() + " " + realisateur.getPrenom() + " (" + realisateur.getNationalite() + ")");
      }
  }

  
  @FXML
  void actionBoutonConnexion(ActionEvent event) {
      String pseudo = entreePseudoUtilisateur.getText();
      String motDePasse = entreeMotDePasseUtilisateur.getText();

      if (pseudo.isEmpty() || motDePasse.isEmpty()) {
          afficherMessageErreur("Pseudo et mot de passe doivent être remplis !");
          return;
      }

      boolean reussi = gestionnaire.connexion(pseudo, motDePasse);
      if (reussi) {
          afficherMessageSucces("Connexion réussie !");
      } else {
          afficherMessageErreur("Connexion échouée, vérifiez vos identifiants !");
      }
  }

  @FXML
  void actionBoutonCreerMonEvaluation(ActionEvent event) {
      String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
      if (filmSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un film !");
          return;
      }

      String titre = filmSelectionne.split(" \\(")[0];
      Film film = gestionnaire.getFilm(titre);

      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      int note = listeNoteEvaluation.getValue();
//      String commentaire = texteCommentaire.getText();
      String commentaire = texteCommentaire.getText().trim(); // Supprimer les espaces inutiles

      if (commentaire.isEmpty()) {
          commentaire = null; // Traiter les commentaires vides comme null
      }


      try {
          // Création et ajout de l'évaluation
          Evaluation evaluation = new Evaluation(note, commentaire, gestionnaireUtilisateur.getUtilisateurConnecte(), film);
          gestionnaire.ajouterEvaluation(film, evaluation);

          // Message de succès
          afficherMessageSucces("Votre évaluation a été ajoutée !");
          
          // Mise à jour de la liste des évaluations
          miseAJourListeEvaluations(film);

          // Mise à jour de la moyenne des évaluations
          double moyenne = film.calculmoyenneEval();
          if (moyenne >= 0) {
              entreeEvaluationMoyenne.setText(String.format("%.2f", moyenne)); // Formate à 2 décimales
          } else {
              entreeEvaluationMoyenne.clear(); // Vide si aucune évaluation
          }

      } catch (NonConnecteException e) {
          afficherMessageErreur("Vous devez être connecté pour évaluer un film !");
      } catch (LocationException e) {
          afficherMessageErreur(e.getMessage());
      }
  }


  
  @FXML
  void actionBoutonDeconnexion(ActionEvent event) {
      try {
          gestionnaire.deconnexion();
          afficherMessageSucces("Déconnexion réussie !");
      } catch (NonConnecteException e) {
          afficherMessageErreur("Aucun utilisateur n'est connecté !");
      }
  }

  
  @FXML
  void actionBoutonFinLocation(ActionEvent event) {
      String filmSelectionne = listeFilmsEnLocation.getSelectionModel().getSelectedItem();
      if (filmSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un film en location !");
          return;
      }

      Film film = gestionnaire.getFilm(filmSelectionne);
      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      try {
          gestionnaire.finLocationFilm(film);
          afficherMessageSucces("Location terminée pour le film sélectionné !");
          miseAJourFilmsLoues();
      } catch (NonConnecteException e) {
          afficherMessageErreur("Vous devez être connecté pour terminer une location !");
      } catch (LocationException e) {
          afficherMessageErreur(e.getMessage());
      }
  }

  
  @FXML
  void actionBoutonInscription(ActionEvent event) {
      String pseudo = entreePseudoUtilisateur.getText();
      String motDePasse = entreeMotDePasseUtilisateur.getText();
      String nom = entreeNomUtilisateur.getText();
      String prenom = entreePrenomUtilisateur.getText();
      String adresse = entreeAdresseUtilisateur.getText();
      int age;
      try {
          age = Integer.parseInt(entreeAgeUtilisateur.getText());
      } catch (NumberFormatException e) {
          afficherMessageErreur("L'âge doit être un nombre valide !");
          return;
      }

      InformationPersonnelle info = new InformationPersonnelle(nom, prenom, adresse, age);
      int resultat = gestionnaire.inscription(pseudo, motDePasse, info);

      switch (resultat) {
          case 0:
              afficherMessageSucces("Inscription réussie !");
              break;
          case 1:
              afficherMessageErreur("Pseudo déjà utilisé !");
              break;
          case 2:
              afficherMessageErreur("Pseudo ou mot de passe vide !");
              break;
          case 3:
              afficherMessageErreur("Informations personnelles incorrectes !");
              break;
      }
  }

  
  @FXML
  void actionBoutonLouerFilmSelectionne(ActionEvent event) {
      String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
      if (filmSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un film à louer !");
          return;
      }

      // Extraire uniquement le titre en retirant " (année)" à la fin
      String titre = filmSelectionne.split(" \\(")[0]; // Diviser par " (" et prendre la première partie

      Film film = gestionnaire.getFilm(titre);
      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      try {
          gestionnaire.louerFilm(film);
          afficherMessageSucces("Film loué avec succès !");
          miseAJourFilmsLoues();
      } catch (NonConnecteException e) {
          afficherMessageErreur("Vous devez être connecté pour louer un film !");
      } catch (LocationException e) {
          afficherMessageErreur(e.getMessage());
      }
  }


  
  @FXML
  void actionBoutonModifierMonEvaluation(ActionEvent event) {
      String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
      if (filmSelectionne == null) {
          afficherMessageErreur("Veuillez sélectionner un film !");
          return;
      }
      String titre = filmSelectionne.split(" \\(")[0]; // Diviser par " (" et prendre la première partie

      Film film = gestionnaire.getFilm(titre);
      if (film == null) {
          afficherMessageErreur("Film introuvable !");
          return;
      }

      int note = listeNoteEvaluation.getValue();
      String commentaire = texteCommentaire.getText();

      try {
          Evaluation nouvelleEvaluation = new Evaluation(note, commentaire, gestionnaireUtilisateur.getUtilisateurConnecte(), film);
          gestionnaire.modifierEvaluation(film, nouvelleEvaluation);
          miseAJourListeEvaluations(film);
          miseAJourEvaluationMoyenne(film);

          afficherMessageSucces("Votre évaluation a été modifiée !");
      } catch (NonConnecteException e) {
          afficherMessageErreur("Vous devez être connecté pour modifier une évaluation !");
      } catch (LocationException e) {
          afficherMessageErreur(e.getMessage());
      }
  }

  
  @FXML
  void actionSelectionArtiste(MouseEvent event) {
      String artisteSelectionne = listeArtistes.getSelectionModel().getSelectedItem();
      if (artisteSelectionne != null) {
          afficherMessageSucces("Artiste sélectionné : " + artisteSelectionne);
      }
  }

  
  @FXML
  void actionSelectionEvaluation(MouseEvent event) {
      String evaluationSelectionnee = listeEvaluations.getSelectionModel().getSelectedItem();
      if (evaluationSelectionnee != null) {
          afficherMessageSucces("Évaluation sélectionnée : " + evaluationSelectionnee);
      }
  }

  
  @FXML
  void actionSelectionFilm(MouseEvent event) {
      String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
      
      if (filmSelectionne != null) {
          afficherMessageSucces("Film sélectionné : " + filmSelectionne);
          
          // Extraire le titre du film
          String titre = filmSelectionne.split(" \\(")[0]; // Diviser par " (" et prendre la première partie
          
          // Récupérer l'objet Film
          Film film = gestionnaire.getFilm(titre);
          if (film == null) {
              afficherMessageErreur("Film introuvable !");
              return;
          }

          // Mise à jour des champs avec les détails du film
          entreeTitreFilm.setText(film.getTitre());
          entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
          entreeAgeLimiteFilm.setText(String.valueOf(film.getAgeLimite()));
          entreeNomPrenomRealisateurFilm.setText(film.getRealisateur().getPrenom() + " " + film.getRealisateur().getNom());

          // Concaténer les genres pour afficher dans un champ
          String genres = String.join(", ", film.getGenres().stream()
                                                 .map(Genre::name)
                                                 .toList());
          entreeGenresFilm.setText(genres);

          // Mettre à jour la checkbox pour indiquer si le film est louable
          checkFilmLouable.setSelected(film.isEstOuvertalocation());

          // Mettre à jour la liste des évaluations
          miseAJourListeEvaluations(film);
      }
  }
  
  
  @FXML
  public void remplirListeGenresFilm() {
      listeGenresFilm.getItems().clear(); // Vider la liste avant de la remplir

      // Récupérer les genres via le gestionnaire
      Set<Genre> genres = gestionnaire.ensembleGenres(); // Exemple de méthode à ajouter dans Gestionnaire

      if (genres != null && !genres.isEmpty()) {
          for (Genre genre : genres) {
              listeGenresFilm.getItems().add(genre.name()); // Ajouter les genres par leur nom
          }
          afficherMessageSucces("Liste des genres mise à jour !");
      } else {
          afficherMessageErreur("Aucun genre de film trouvé !");
      }
  }



  
  @FXML
  void initialize() {
	  gestionnaireFilm = AdministrationControleur.gestionFilm;
	  gestionnaireUtilisateur = new GestionUtilisateur();
	  gestionnaire = new Gestionnaire(gestionnaireUtilisateur, gestionnaireFilm);
	  listeNoteEvaluation.getItems().addAll(0, 1, 2, 3, 4, 5);


	  
	  
    // Mettre ici le code d'initialisation du contenu de la fenêtre
  }
  
  
  private void afficherMessageErreur(String message) {
	    System.err.println("Erreur : " + message);
	    // Ajoutez ici un affichage graphique (ex. : Alert, Label, etc.)
	}

	private void afficherMessageSucces(String message) {
	    System.out.println("Succès : " + message);
	    // Ajoutez ici un affichage graphique (ex. : Alert, Label, etc.)
	}
	
	private void miseAJourFilmsLoues() {
	    listeFilmsEnLocation.getItems().clear();
	    try {
	        Set<Film> filmsLoues = gestionnaire.filmsEnLocation();
	        for (Film film : filmsLoues) {
	            listeFilmsEnLocation.getItems().add(film.getTitre());
	        }
	    } catch (NonConnecteException e) {
	        afficherMessageErreur("Impossible de récupérer les films en location.");
	    }
	}
	
	private void miseAJourListeEvaluations(Film film) {
	    listeEvaluations.getItems().clear();
	    Set<Evaluation> evaluations = gestionnaire.ensembleEvaluationsFilm(film);

	    if (evaluations != null && !evaluations.isEmpty()) {
	        for (Evaluation evaluation : evaluations) {
	            // Construire la chaîne en fonction de la présence d'un commentaire
	            String item = "Auteur: " + evaluation.getUtilisateurPseudo() + 
	                          ", Note: " + evaluation.getNote();

	            if (evaluation.getCommentaire() != null && !evaluation.getCommentaire().isEmpty()) {
	                item += ", Commentaire: " + evaluation.getCommentaire();
	            }

	            listeEvaluations.getItems().add(item);
	        }
	    } else {
	        afficherMessageErreur("Aucune évaluation pour ce film.");
	    }
	}

    
	private void miseAJourEvaluationMoyenne(Film film) {
	    if (film == null) {
	        entreeEvaluationMoyenne.clear();
	        return;
	    }

	    double moyenne = film.calculmoyenneEval();
	    if (moyenne >= 0) {
	        entreeEvaluationMoyenne.setText(String.format("%.2f", moyenne)); // Affiche la moyenne avec deux décimales
	    } else {
	        entreeEvaluationMoyenne.clear(); // Vide le champ si aucune évaluation
	    }
	}

  
}
