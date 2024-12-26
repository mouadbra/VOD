package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionFilm;

/**
 * Controleur JavaFX de la fenêtre d'administration.
 *
 * @author Eric Cariou
 *
 */
public class AdministrationControleur {
    public GestionFilm gestionFilm;
    private Film filmSelectionne;
    private Artiste artisteSelectionne;
    
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
  
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
	  
    
  }
  
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAfficherFilmsActeurSelectionne(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAfficherFilmsDuRealisateur(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAfficherFilmsRealisateurSelectionne(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAfficherTousActeursFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAjouterActeurFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonAjouterGenreFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonChercherArtiste(ActionEvent event) {
	  String nom = entreeNomArtiste.getText().trim();
      String prenom = entreePrenomArtiste.getText().trim();
      
      Artiste artiste = gestionFilm.getArtiste(nom, prenom);
      if (artiste != null) {
          ObservableList<String> items = FXCollections.observableArrayList(
              artiste.getNom() + " " + artiste.getPrenom()
          );
          listeArtistes.setItems(items);
          labelListeArtistes.setText("Résultat de la recherche");
          selectionnerArtiste(artiste);
      } else {
          afficherMessage("Recherche", "Artiste non trouvé", AlertType.INFORMATION);
      }
  }
  private void afficherMessage(String titre, String message, AlertType type) {
      Alert alert = new Alert(type);
      alert.setTitle(titre);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
  }
  private void selectionnerArtiste(Artiste artiste) {
      if (artiste != null) {
          artisteSelectionne = artiste;
          entreeNomArtiste.setText(artiste.getNom());
          entreePrenomArtiste.setText(artiste.getPrenom());
          entreeNationaliteArtiste.setText(artiste.getNationalite());
      }
  }

@FXML
  void actionBoutonChercherFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonChoisirArtisteSelectionneRealisateur(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonEnregistrerArtiste(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonEnregistrerFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonNouveauArtiste(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonNouveauFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonParcourirAffiche(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonSupprimerArtiste(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonSupprimerFilm(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuApropos(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuCharger(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    
  }
  
  @FXML
  void actionListeSelectionArtiste(MouseEvent event) {
    
  }
  
  @FXML
  void actionListeSelectionFilm(MouseEvent event) {
    
  }
  
  @FXML
  void initialize() {
	  gestionFilm = new GestionFilm();
      
      // Initialisation des choix d'âge limite
      listeChoixAgeLimite.setItems(FXCollections.observableArrayList(
          "0", "12", "16", "18"
      ));
      
      // Initialisation de la liste des genres
      //    listeTousGenres.setItems(FXCollections.observableArrayList(Genre.values()));
      
      // Labels initiaux
      labelListeArtistes.setText("Liste des artistes");
      labelListeFilms.setText("Liste des films");
      
      // Affichage initial
      rafraichirListeFilms();
      rafraichirListeArtistes();
  }
  // Méthodes utilitaires
  private void rafraichirListeFilms() {
      ObservableList<String> films = FXCollections.observableArrayList();
      for (Film film : gestionFilm.ensembleFilms()) {
          films.add(film.getTitre());
      }
      listeFilms.setItems(films);
      labelListeFilms.setText("Liste des films");
  }

  private void rafraichirListeArtistes() {
      ObservableList<String> artistes = FXCollections.observableArrayList();
      for (Artiste artiste : gestionFilm.ensembleActeurs()) {
          artistes.add(artiste.getNom() + " " + artiste.getPrenom());
      }
      listeArtistes.setItems(artistes);
      labelListeArtistes.setText("Liste des artistes");
  }
}
