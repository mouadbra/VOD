package ui;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionFilm;
import location.GestionUtilisateur;
import location.Gestionnaire;

/**
 * Controleur JavaFX de la fenêtre d'administration.
 *
 * @author Eric Cariou
 *
 */

public class AdministrationControleur {

  @FXML
    private CheckBox checkBoxLocationFilm;

  @FXML
    private TextField entreeAffiche;

  @FXML
    private TextField entreeAnneeFilm;

  @FXML
    private TextField entreeNationaliteArtiste;

  @FXML
    private TextField entreeNomArtiste;

  @FXML
    private TextField entreeNomPrenomRealisateur;

  @FXML
    private TextField entreePrenomArtiste;

  @FXML
    private TextField entreeTitreFilm;

  @FXML
    private Label labelListeArtistes;

  @FXML
    private Label labelListeFilms;

  @FXML
    private ListView<String> listeArtistes;

  @FXML
    private ChoiceBox<String> listeChoixAgeLimite;

  @FXML
    private ListView<String> listeFilms;

  @FXML
    private ListView<String> listeGenresFilm;

  @FXML
    private ListView<String> listeTousGenres;

  static GestionFilm gestionFilm;
  
  static GestionUtilisateur gestionUtilisateur;
  
  static Gestionnaire gestionnaire;

  @FXML
    void initialize() {
    gestionFilm = new GestionFilm();
    gestionUtilisateur = new GestionUtilisateur();
    gestionnaire = new Gestionnaire(gestionUtilisateur, gestionFilm);
    // Initialisation des limites d'âge pour la liste déroulante
    listeChoixAgeLimite.getItems().addAll("0", "10", "12", "16", "18");

    // Initialisation des genres disponibles
    listeTousGenres.getItems().addAll(
            Arrays.stream(Genre.values())
            .map(Enum::name)
            .collect(Collectors.toList())
    );

    // Chargement initial des artistes et des films dans leurs listes respectives
    mettreAjourListeartistes();
    mettreaJourlistefilms();
  }

  private void mettreAjourListeartistes() {
    listeArtistes.getItems().clear();
    if (gestionFilm != null) {
      for (Artiste artiste : gestionFilm.ensembleActeurs()) {
        listeArtistes.getItems().add(artiste.getNom() + " " + artiste.getPrenom());
      }
    }
    labelListeArtistes.setText("Liste des artistes");
  }

  private void mettreaJourlistefilms() {
    listeFilms.getItems().clear();
    if (gestionFilm != null) {
      for (Film film : gestionFilm.ensembleFilms()) {
        listeFilms.getItems().add(film.getTitre());
      }
    }
    labelListeFilms.setText("Liste des films");
  }


  @FXML
  void actionBoutonEnregistrerArtiste(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    String nationalite = entreeNationaliteArtiste.getText();

    if (nom.isEmpty() || prenom.isEmpty() || nationalite.isEmpty()) {
      afficherAlerte("Erreur", "Tous les champs de l'artiste doivent être remplis.");
      return;
    }

    Artiste nouvelArtiste = gestionFilm.creerArtiste(nom, prenom, nationalite);
    if (nouvelArtiste != null) {
      listeArtistes.getItems().add(nom + " " + prenom);
    } else {
      afficherAlerte("Erreur", "L'artiste existe déjà.");
    }
  }

  @FXML
  void actionBoutonSupprimerArtiste(ActionEvent event) {
    String artisteSelectionne = listeArtistes.getSelectionModel().getSelectedItem();
    if (artisteSelectionne == null) {
      afficherAlerte("Erreur", "Veuillez sélectionner un artiste à supprimer.");
      return;
    }

    String[] details = artisteSelectionne.split(" ");
    Artiste artiste = gestionFilm.getArtiste(details[0], details[1]);
    if (gestionFilm.supprimerArtiste(artiste)) {
      listeArtistes.getItems().remove(artisteSelectionne);
    } else {
      afficherAlerte("Erreur", "Impossible de supprimer l'artiste (lié à un film).");
    }
  }

  @FXML
  void actionBoutonEnregistrerFilm(ActionEvent event) {
    String artisteSelectionne = listeArtistes.getSelectionModel().getSelectedItem();

    if (artisteSelectionne == null || artisteSelectionne.isEmpty()) {
      afficherMessageErreur("Veuillez sélectionner un réalisateur dans la liste.");
      return;
    }

    // Sépare le nom et le prénom
    String[] nomPrenom = artisteSelectionne.split(" ");
    if (nomPrenom.length < 2) {
      afficherMessageErreur("Format invalide pour le réalisateur sélectionné."
          + " Assurez-vous qu'il contient à la fois un nom et un prénom.");
      
      return;
    }

    String nom = nomPrenom[0];
    String prenom = nomPrenom[1];

    // Récupération des informations saisies
    String titre = entreeTitreFilm.getText();
    String anneeString = entreeAnneeFilm.getText();
    String ageLimiteString = listeChoixAgeLimite.getValue();

    if (titre.isEmpty() || anneeString.isEmpty() || ageLimiteString == null) {
      afficherMessageErreur("Veuillez remplir tous les champs requis pour le film.");
      return;
    }

    int annee;
    int ageLimite;
    try {
      annee = Integer.parseInt(anneeString);
      ageLimite = Integer.parseInt(ageLimiteString);
    } catch (NumberFormatException e) {
      afficherMessageErreur("L'année et l'âge limite doivent être des nombres valides.");
      return;
    }

    Artiste realisateur = gestionFilm.getArtiste(nom, prenom);
    if (realisateur == null) {
      afficherMessageErreur("Le réalisateur sélectionné n'existe pas.");
      return;
    }

    // Création du film
    Film film = gestionFilm.creerFilm(titre, realisateur, annee, ageLimite);
    if (film == null) {
      afficherMessageErreur("Impossible de créer le film."
          + " Un film avec ce titre existe peut-être déjà.");
    } else {
      if (checkBoxLocationFilm.isSelected()) {
        gestionFilm.ouvrirLocation(film);
      } else {
        gestionFilm.fermerLocation(film);
      }
      mettreaJourlistefilms();
      afficherMessageSucces("Film créé avec succès.");
    }
  }


  @FXML
  void actionBoutonSupprimerFilm(ActionEvent event) {
    String filmSelectionne = listeFilms.getSelectionModel().getSelectedItem();
    if (filmSelectionne == null) {
      return;
    }

    Film film = gestionFilm.getFilm(filmSelectionne);
    if (gestionFilm.supprimerFilm(film)) {
      listeFilms.getItems().remove(filmSelectionne);
    } else {
      afficherAlerte("Erreur", "Impossible de supprimer ce film.");
    }
  }

  @FXML
  void actionBoutonParcourirAffiche(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une affiche");
    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
      entreeAffiche.setText(file.getAbsolutePath());
    }
  }

  private void afficherAlerte(String titre, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titre);
    alert.setContentText(message);
    alert.showAndWait();
  }
    

    
  @FXML
  void actionBoutonChercherFilm(ActionEvent event) {
    String titreFilm = entreeTitreFilm.getText();

    if (titreFilm == null || titreFilm.isEmpty()) {
      afficherMessageErreur("Veuillez entrer un titre de film à rechercher.");
      return;
    }

    Film film = gestionFilm.getFilm(titreFilm);
    if (film == null) {
      afficherMessageErreur("Aucun film trouvé avec le titre : " + titreFilm);
      return;
    }

    // Mettre à jour les champs d'information sur le film
    entreeTitreFilm.setText(film.getTitre());
    entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
    listeChoixAgeLimite.setValue(String.valueOf(film.getAgeLimite()));
    entreeNomPrenomRealisateur.setText(film.getRealisateur().getNom() + " " 
          + film.getRealisateur().getPrenom());
    listeGenresFilm.getItems().clear();

    for (Genre genre : film.getGenres()) {
      listeGenresFilm.getItems().add(genre.name());
    }

    checkBoxLocationFilm.setSelected(film.isEstOuvertalocation());
    afficherMessageSucces("Film trouvé et affiché.");
  }

    
  @FXML
  void actionBoutonChoisirArtisteSelectionneRealisateur(ActionEvent event) {
    String selectionArtiste = listeArtistes.getSelectionModel().getSelectedItem();

    if (selectionArtiste == null || selectionArtiste.isEmpty()) {
      afficherMessageErreur("Veuillez sélectionner un artiste dans la liste.");
      return;
    }

    // Extraire le nom et le prénom de l'artiste sélectionné
    String[] nomPrenom = selectionArtiste.split(" ");
    if (nomPrenom.length < 2) {
      afficherMessageErreur("Format invalide pour l'artiste sélectionné.");
      return;
    }

    String nom = nomPrenom[0];
    String prenom = nomPrenom[1];

    Artiste realisateur = gestionFilm.getArtiste(nom, prenom);
    if (realisateur == null) {
      afficherMessageErreur("L'artiste sélectionné n'est pas un réalisateur.");
      return;
    }

    // Mettre à jour le champ réalisateur
    entreeNomPrenomRealisateur.setText(realisateur.getNom() + " " + realisateur.getPrenom());
    afficherMessageSucces("Le réalisateur a été choisi avec succès.");
  }

    

    
  @FXML
  void actionBoutonNouveauArtiste(ActionEvent event) {
    // Effacer le contenu des champs liés à l'artiste
    entreeNomArtiste.clear();
    entreePrenomArtiste.clear();
    entreeNationaliteArtiste.clear();
    listeArtistes.getSelectionModel().clearSelection();

    afficherMessageSucces("Les champs ont été réinitialisés pour un nouvel artiste.");
  }

    
  @FXML
  void actionBoutonNouveauFilm(ActionEvent event) {
    // Effacer le contenu des champs liés au film
    entreeTitreFilm.clear();
    entreeAnneeFilm.clear();
    listeChoixAgeLimite.getSelectionModel().clearSelection();
    entreeNomPrenomRealisateur.clear();
    listeGenresFilm.getItems().clear();
    checkBoxLocationFilm.setSelected(false);
    listeFilms.getSelectionModel().clearSelection();

    afficherMessageSucces("Les champs ont été réinitialisés pour un nouveau film.");
  }

    

    
  @FXML
  void actionMenuApropos(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("À propos");
    alert.setHeaderText("Application de gestion de films");
    alert.setContentText("Cette application permet de gérer des artistes et des films, "
                + "ainsi que de gérer la location de films pour les utilisateurs.\n"
                + "Développée dans le cadre du projet L3 Informatique - UBO.");
    alert.showAndWait();
  }

    


    

    
  @FXML
  void actionListeSelectionArtiste(MouseEvent event) {
    String selectionArtiste = listeArtistes.getSelectionModel().getSelectedItem();

    if (selectionArtiste == null || selectionArtiste.isEmpty()) {
      return; // Rien n'est sélectionné
    }

    // Extraire le nom et le prénom de l'artiste sélectionné
    String[] nomPrenom = selectionArtiste.split(" ");
    if (nomPrenom.length < 2) {
      afficherMessageErreur("Format invalide pour l'artiste sélectionné.");
      return;
    }

    String nom = nomPrenom[0];
    String prenom = nomPrenom[1];

    Artiste artiste = gestionFilm.getArtiste(nom, prenom);
    if (artiste != null) {
      entreeNomArtiste.setText(artiste.getNom());
      entreePrenomArtiste.setText(artiste.getPrenom());
      entreeNationaliteArtiste.setText(artiste.getNationalite());
    } else {
      afficherMessageErreur("Artiste introuvable dans le système.");
    }
  }

    
  @FXML
  void actionListeSelectionFilm(MouseEvent event) {
    String selectionFilm = listeFilms.getSelectionModel().getSelectedItem();

    if (selectionFilm == null || selectionFilm.isEmpty()) {
      return; // Rien n'est sélectionné
    }

    Film film = gestionFilm.getFilm(selectionFilm);
    if (film != null) {
      entreeTitreFilm.setText(film.getTitre());
      entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
      listeChoixAgeLimite.setValue(String.valueOf(film.getAgeLimite()));
      entreeNomPrenomRealisateur.setText(film.getRealisateur().getNom() + " " 
            + film.getRealisateur().getPrenom());

      listeGenresFilm.getItems().clear();
      for (Genre genre : film.getGenres()) {
        listeGenresFilm.getItems().add(genre.name());
      }

      checkBoxLocationFilm.setSelected(film.isEstOuvertalocation());
    } else {
      afficherMessageErreur("Film introuvable dans le système.");
    }
  }

  @FXML
  void actionBoutonAfficherFilmsActeurSelectionne(ActionEvent event) {
    String selectionActeur = listeArtistes.getSelectionModel().getSelectedItem();

    if (selectionActeur == null || selectionActeur.isEmpty()) {
      afficherMessageErreur("Veuillez sélectionner un acteur.");
      return;
    }

    // Extraire le nom et le prénom de l'acteur sélectionné
    String[] nomPrenom = selectionActeur.split(" ");
    if (nomPrenom.length < 2) {
      afficherMessageErreur("Format invalide pour l'acteur sélectionné.");
      return;
    }

    String nom = nomPrenom[0];
    String prenom = nomPrenom[1];

    Artiste acteur = gestionFilm.getActeur(nom, prenom);
    if (acteur == null) {
      afficherMessageErreur("Acteur non trouvé dans le système.");
      return;
    }

    // Afficher les films de cet acteur
    Set<Film> filmsActeur = gestionFilm.ensembleFilmsActeur(acteur);
    listeFilms.getItems().clear();

    if (filmsActeur != null) {
      for (Film film : filmsActeur) {
        listeFilms.getItems().add(film.getTitre());
      }
    } else {
      afficherMessageErreur("Aucun film trouvé pour cet acteur.");
    }

    labelListeFilms.setText("Films de l'acteur : " 
            + acteur.getNom() + " " + acteur.getPrenom());
  }

  @FXML
  void actionBoutonAfficherFilmsRealisateurSelectionne(ActionEvent event) {
    String selectionArtiste = listeArtistes.getSelectionModel().getSelectedItem();

    if (selectionArtiste == null || selectionArtiste.isEmpty()) {
      afficherMessageErreur("Veuillez sélectionner un réalisateur dans la liste.");
      return;
    }

    // Extraire le nom et le prénom de l'artiste sélectionné
    String[] nomPrenom = selectionArtiste.split(" ");
    if (nomPrenom.length < 2) {
      afficherMessageErreur("Format invalide pour le réalisateur sélectionné.");
      return;
    }

    String nom = nomPrenom[0];
    String prenom = nomPrenom[1];

    Artiste realisateur = gestionFilm.getRealisateur(nom, prenom);
    if (realisateur == null) {
      afficherMessageErreur("L'artiste sélectionné n'est pas un réalisateur.");
      return;
    }

    Set<Film> films = gestionFilm.ensembleFilmsRealisateur(realisateur);
    listeFilms.getItems().clear();

    if (films != null) {
      for (Film film : films) {
        listeFilms.getItems().add(film.getTitre());
      }
    } else {
      afficherMessageErreur("Aucun film trouvé pour ce réalisateur.");
    }

    labelListeFilms.setText("Films de " + realisateur.getNom() + " " + realisateur.getPrenom());
  }

    
  @FXML
  void actionBoutonAfficherTousActeursFilm(ActionEvent event) {
    String selectionFilm = listeFilms.getSelectionModel().getSelectedItem();

    if (selectionFilm == null || selectionFilm.isEmpty()) {
      afficherMessageErreur("Veuillez sélectionner un film dans la liste.");
      return;
    }

    Film film = gestionFilm.getFilm(selectionFilm);
    if (film == null) {
      afficherMessageErreur("Film introuvable dans le système.");
      return;
    }

    Set<Artiste> acteurs = film.getActeurs();
    listeArtistes.getItems().clear();

    if (acteurs != null) {
      for (Artiste acteur : acteurs) {
        listeArtistes.getItems().add(acteur.getNom() + " " + acteur.getPrenom());
      }
    } else {
      afficherMessageErreur("Aucun acteur trouvé pour ce film.");
    }

    labelListeArtistes.setText("Acteurs du film : " + film.getTitre());
  }

    
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
    try {
      listeArtistes.getItems().clear();
      Set<Artiste> artistes = gestionFilm.ensembleArtistes();
      System.out.println("Artistes récupérés : " + artistes);


      if (artistes.isEmpty()) {
        System.out.println("Aucun artiste trouvé.");
      }

      for (Artiste artiste : artistes) {
        listeArtistes.getItems().add(artiste.getNom() + " " + artiste.getPrenom());
      }

      labelListeArtistes.setText("Liste de tous les artistes");
    } catch (Exception e) {
      e.printStackTrace();
      afficherAlerte("Erreur", "Une erreur s'est produite lors de l'affichage des artistes.");
    }
  }



    
  @FXML
  void actionBoutonAjouterActeurFilm(ActionEvent event) {
    String selectionFilm = listeFilms.getSelectionModel().getSelectedItem();
    String selectionArtiste = listeArtistes.getSelectionModel().getSelectedItem();

    if (selectionFilm == null || selectionArtiste == null) {
      afficherMessageErreur("Veuillez sélectionner un film et un artiste.");
      return;
    }

    // Extraire le nom et le prénom de l'artiste sélectionné
    String[] nomPrenom = selectionArtiste.split(" ");
    if (nomPrenom.length < 2) {
      afficherMessageErreur("Format invalide pour l'artiste sélectionné.");
      return;
    }

    String nom = nomPrenom[0];
    String prenom = nomPrenom[1];

    Artiste acteur = gestionFilm.getArtiste(nom, prenom);
    Film film = gestionFilm.getFilm(selectionFilm);

    if (acteur == null || film == null) {
      afficherMessageErreur("Film ou artiste introuvable.");
      return;
    }

    if (gestionFilm.ajouterActeurs(film, acteur)) {
      afficherMessageSucces("L'acteur a été ajouté au film avec succès.");
    } else {
      afficherMessageErreur("Impossible d'ajouter l'acteur au film.");
    }
  }

    
  @FXML
  void actionBoutonAjouterGenreFilm(ActionEvent event) {
    String selectionFilm = listeFilms.getSelectionModel().getSelectedItem();
    String genreSelectionne = listeTousGenres.getSelectionModel().getSelectedItem();

    if (selectionFilm == null || genreSelectionne == null) {
      afficherMessageErreur("Veuillez sélectionner un film et un genre.");
      return;
    }

    Film film = gestionFilm.getFilm(selectionFilm);
    if (film == null) {
      afficherMessageErreur("Film introuvable.");
      return;
    }

    try {
      Genre genre = Arrays.stream(Genre.values())
                                .filter(g -> g.name().equalsIgnoreCase(genreSelectionne))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Genre invalide : " 
                                      + genreSelectionne));

      if (gestionFilm.ajouterGenres(film, genre)) {
        afficherMessageSucces("Le genre a été ajouté au film avec succès.");
        mettreaJourlisteGenresfilm(film);
      } else {
        afficherMessageErreur("Impossible d'ajouter le genre au film.");
      }
    } catch (IllegalArgumentException e) {
      afficherMessageErreur("Genre invalide : " + genreSelectionne);
    }
  }


  private void mettreaJourlisteGenresfilm(Film film) {
    listeGenresFilm.getItems().clear();
    for (Genre genre : film.getGenres()) {
      listeGenresFilm.getItems().add(genre.name());
    }
  }

  @FXML
  void actionBoutonChercherArtiste(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();

    if (nom.isEmpty() || prenom.isEmpty()) {
      afficherMessageErreur("Veuillez remplir les champs"
          + " Nom et Prénom pour rechercher un artiste.");
      return;
    }

    Artiste artiste = gestionFilm.getArtiste(nom, prenom);

    if (artiste == null) {
      afficherMessageErreur("Artiste introuvable.");
      return;
    }

    listeArtistes.getItems().clear();
    listeArtistes.getItems().add(artiste.getNom() + " " + artiste.getPrenom());

    entreeNomArtiste.setText(artiste.getNom());
    entreePrenomArtiste.setText(artiste.getPrenom());
    entreeNationaliteArtiste.setText(artiste.getNationalite());

    labelListeArtistes.setText("Résultat de la recherche");
  }

    
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    Set<Artiste> realisateurs = gestionFilm.ensembleRealisateurs(); // Récupération des réalisateurs

    for (Artiste realisateur : realisateurs) {
      listeArtistes.getItems().add(realisateur.getNom() + " " + realisateur.getPrenom());
    }

    labelListeArtistes.setText("Liste des réalisateurs");
  }

    
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    Set<Artiste> acteurs = gestionFilm.ensembleActeurs(); // Récupération des acteurs

    for (Artiste acteur : acteurs) {
      listeArtistes.getItems().add(acteur.getNom() + " " + acteur.getPrenom());
    }

    labelListeArtistes.setText("Liste des acteurs");
  }

  @FXML
  void actionBoutonAfficherFilmsDuRealisateur(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();

    if (nom.isEmpty() || prenom.isEmpty()) {
      afficherMessageErreur("Veuillez remplir les champs Nom et Prénom pour"
          + " rechercher un réalisateur.");
      return;
    }

    Artiste realisateur = gestionFilm.getRealisateur(nom, prenom);

    if (realisateur == null) {
      afficherMessageErreur("Réalisateur introuvable.");
      return;
    }

    Set<Film> films = gestionFilm.ensembleFilmsRealisateur(realisateur);
    listeFilms.getItems().clear();

    if (films != null) {
      for (Film film : films) {
        listeFilms.getItems().add(film.getTitre());
      }
    } else {
      afficherMessageErreur("Aucun film trouvé pour ce réalisateur.");
    }

    labelListeFilms.setText("Films de " + realisateur.getNom() + " " + realisateur.getPrenom());
  }

    

  @FXML
  void actionMenuCharger(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Charger les données");
      fileChooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("Fichier de données", "*.dat")
      );
      
      File fichier = fileChooser.showOpenDialog(null);
      if (fichier != null) {
          try {
              // Utilisation de l'interface InterSauvegarde implémentée par votre gestionnaire
              gestionnaire.chargerDonnees(fichier.getAbsolutePath());
              
              // Mise à jour des listes dans l'interface
              mettreaJourlistefilms();
              mettreAjourListeartistes();
              
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Chargement");
              alert.setHeaderText(null);
              alert.setContentText("Les données ont été chargées avec succès !");
              alert.showAndWait();
              
          } catch (IOException e) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Erreur de chargement");
              alert.setHeaderText(null);
              alert.setContentText("Une erreur est survenue lors du chargement : " + e.getMessage());
              alert.showAndWait();
          }
      }
  }
    
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Sauvegarder les données");
      fileChooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("Fichier de données", "*.dat")
      );
      
      File fichier = fileChooser.showSaveDialog(null);
      if (fichier != null) {
          try {
              // Utilisation de l'interface InterSauvegarde implémentée par votre gestionnaire
              gestionnaire.sauvegarderDonnees(fichier.getAbsolutePath());
              
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Sauvegarde");
              alert.setHeaderText(null);
              alert.setContentText("Les données ont été sauvegardées avec succès !");
              alert.showAndWait();
              
          } catch (IOException e) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Erreur de sauvegarde");
              alert.setHeaderText(null);
              alert.setContentText("Une erreur est survenue lors de la sauvegarde : " + e.getMessage());
              alert.showAndWait();
          }
      }
  }
    
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Quitter l'application");
    alert.setHeaderText("Confirmation");
    alert.setContentText("Êtes-vous sûr de vouloir quitter l'application ?");

    if (alert.showAndWait().get() == ButtonType.OK) {
      Platform.exit();
    }
  }
    
    
    
  private void afficherMessageErreur(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void afficherMessageSucces(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Succès");
    alert.setContentText(message);
    alert.showAndWait();
  }

}
