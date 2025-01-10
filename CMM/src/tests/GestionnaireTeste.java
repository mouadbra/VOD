package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




/**
 * Classe de test pour la classe Gestionnaire.
 * Teste les fonctionnalités principales telles que l'inscription, la connexion,
 * la gestion des films et des évaluations.
 */
public class GestionnaireTeste {

  /**
   * Gestionnaire des utilisateurs pour les tests.
   */
  private GestionUtilisateur gestionUtilisateur;

  /**
   * Gestionnaire des films pour les tests.
   */
  private GestionFilm gestionFilm;

  /**
   * Instance de Gestionnaire utilisée dans les tests.
   */
  private Gestionnaire gestionnaire;

  /**
   * Utilisateur de test.
   */
  private Utilisateur utilisateur;
  private Utilisateur utilisateur2;


  /**
   * Film de test.
   */
  private Film film;

  /**
   * Réalisateur du film de test.
   */
  private Artiste realisateur;

  /**
   * Prépare les objets nécessaires avant chaque test.
   */
  @BeforeEach
  public void setUp() {
    this.gestionUtilisateur = GestionUtilisateur.getInstance();
    gestionFilm = new GestionFilm();
    gestionnaire = new Gestionnaire(gestionUtilisateur, gestionFilm);

    InformationPersonnelle info = new InformationPersonnelle("John", "Doe", "123 Main St", 25);
    utilisateur = new Utilisateur("johndoe", "password", info);
    utilisateur2 = new Utilisateur("johndoee", "password", info);

    realisateur = new Artiste("Nolan", "Christopher", "Inception");
    Set<Genre> genres = new HashSet<>();
    genres.add(Genre.Action);
    film = gestionFilm.creerFilm("Inception", realisateur, 2010, 12);
  }

  /**
   * Teste une inscription valide.
   */
  @Test
  public void testInscriptionValide() {
    int result = gestionnaire.inscription("janedoe", "password123", utilisateur.getInfo());
    assertEquals(0, result, "L'inscription doit réussir avec des données valides.");
  }

  /**
   * Teste une inscription avec un pseudo déjà existant.
   */
  @Test
  public void testInscriptionPseudoExistant() {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    int result = gestionnaire.inscription("johndoe", "password123", utilisateur.getInfo());
    assertEquals(1, result, "L'inscription doit échouer si le pseudo existe déjà.");
  }

  /**
   * Teste une connexion valide.
   */
  @Test
  public void testConnexionValide() {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    assertTrue(gestionnaire.connexion("johndoe", "password"), 
            "La connexion doit réussir avec des identifiants valides.");
  }

  /**
   * Teste une connexion avec un mot de passe incorrect.
   */
  @Test
  public void testConnexionInvalide() {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    assertFalse(gestionnaire.connexion("johndoe", "wrongpassword"), 
            "La connexion doit échouer avec un mot de passe incorrect.");
  }

  /**
   * Teste la déconnexion sans utilisateur connecté.
   */
  @Test
  public void testDeconnexionSansConnexion() throws NonConnecteException {
    if (gestionUtilisateur.getUtilisateurConnecte() != null) {
      gestionnaire.deconnexion();
    }
    assertThrows(NonConnecteException.class, () -> gestionnaire.deconnexion(), 
         "Une exception doit être levée si aucun utilisateur n'est connecté.");
  }

  /**
   * Teste la location d'un film sans connexion.
   *  
   */
  @Test
  public void testLouerFilmSansConnexion() throws NonConnecteException {
    if (gestionUtilisateur.getUtilisateurConnecte() != null) {
      gestionnaire.deconnexion();
    }
    assertThrows(NonConnecteException.class, () -> gestionnaire.louerFilm(film), 
           "Une exception doit être levée si aucun utilisateur n'est connecté.");
  }

  /**
   * Teste la location d'un film lorsque la limite de films est atteinte.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  @Test
  public void testLouerFilmLimiteAtteinte() throws NonConnecteException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");
    utilisateur.ajouterFilmenLocation(film);
    utilisateur.ajouterFilmenLocation(new Film("Film2", realisateur, 2011, 12, new HashSet<>()));
    utilisateur.ajouterFilmenLocation(new Film("Film3", realisateur, 2012, 12, new HashSet<>()));

    Film film4 = new Film("Film4", realisateur, 2013, 12, new HashSet<>());
    assertThrows(LocationException.class, () -> gestionnaire.louerFilm(film4), 
            "Une exception doit être levée si la limite de location est atteinte.");
  }

  /**
   * Teste l'ajout d'une évaluation sans connexion.
   */
  @Test
  public void testAjouterEvaluationNonConnecte() {
    Evaluation eval = new Evaluation(4, "Bon film", utilisateur, film);
    assertThrows(NonConnecteException.class, () -> gestionnaire.ajouterEvaluation(film, eval), 
            "Une exception doit être levée si aucun utilisateur n'est connecté.");
  }

  /**
   * Teste l'ajout d'une évaluation sans historique de location.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  @Test
  public void testAjouterEvaluationSansHistorique() throws NonConnecteException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");

    Evaluation eval = new Evaluation(4, "Bon film", utilisateur, film);
    assertThrows(LocationException.class, () -> gestionnaire.ajouterEvaluation(film, eval), 
            "Une exception doit être levée si le film n'est pas dans l'historique.");
  }

  /**
   * Teste l'ajout d'une évaluation valide.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   * 
   * @throws LocationException si une erreur de location se produit.
   */
  @Test
  public void testAjouterEvaluationValide() throws NonConnecteException, LocationException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");
    film.modifierLocation(true);
    gestionnaire.louerFilm(film); // Ajoute le film dans l'historique
    Evaluation eval = new Evaluation(4, "Excellent!", utilisateur, film);

    gestionnaire.ajouterEvaluation(film, eval);
    assertTrue(film.getEvaluations().contains(eval), 
          "L'évaluation doit être ajoutée au film.");
  }

  /**
   * Teste la récupération de l'ensemble des films.
   */
  @Test
  public void testEnsembleFilms() {
    Set<Film> films = gestionnaire.ensembleFilms();
    assertNotNull(films, "L'ensemble des films ne doit pas être null.");
    assertTrue(films.contains(film), "L'ensemble des films doit contenir le film ajouté.");
  }
  /**
   * Teste la déconnexion avec un utilisateur connecté.
   */
  
  @Test
  public void testDeconnexionValide() throws NonConnecteException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");
    gestionnaire.deconnexion();
    assertNull(gestionUtilisateur.getUtilisateurConnecte(), "L'utilisateur doit être déconnecté.");
  }
  
  /**
   * Teste la location de film avec un utilisateur connecté mais film non disponible.
   */
  
  @Test
  public void testLocationFilmNonDisponible() throws NonConnecteException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");

    film.setLocation(false);  
    // Simulation que le film n'est pas disponible pour la location
    assertThrows(LocationException.class, () -> {
      gestionnaire.louerFilm(film); },
        "La location doit échouer si le film n'est pas disponible.");
  }
  
  /**
   * Teste l'ajout d'une évaluation lorsque l'utilisateur n'a pas loué le film.
   */
    
  @Test
  public void testAjouterEvaluationSansLocation() throws NonConnecteException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");
    Evaluation eval = new Evaluation(5, "Excellent film!", utilisateur, film);
    assertThrows(LocationException.class, () -> {
      gestionnaire.ajouterEvaluation(film, eval);
    }, "L'utilisateur ne peut pas évaluer un film qu'il n'a pas loué.");
  }
  
  /**
   * Teste la modification d'une évaluation existante.
   */
    
  @Test
  public void testModifierEvaluationValide() throws NonConnecteException, LocationException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");

    // Loue le film et ajoute une évaluation
    film.modifierLocation(true);
    gestionnaire.louerFilm(film);
    Evaluation eval = new Evaluation(5, utilisateur, film);
    gestionnaire.ajouterEvaluation(film, eval);  
    // Modifie l'évaluation
    Evaluation nouvelleEval = new Evaluation(4, utilisateur, film);
    gestionnaire.modifierEvaluation(film, nouvelleEval);

    assertTrue(film.getEvaluations().contains(nouvelleEval),
                   "L'évaluation du film doit être modifiée.");
  }

  /**
   * Teste la suppression d'une évaluation existante.
   */
  @Test
  public void testSupprimerEvaluation() throws NonConnecteException, LocationException {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    gestionnaire.connexion("johndoe", "password");
    // Loue le film et ajoute une évaluation
    film.modifierLocation(true);
    gestionnaire.louerFilm(film);
    Evaluation eval = new Evaluation(5, utilisateur, film);
    gestionnaire.ajouterEvaluation(film, eval);
    // Supprime l'évaluation
    Evaluation nouvelleEval = new Evaluation(3, utilisateur, film);
    gestionnaire.modifierEvaluation(film, nouvelleEval); 
    assertTrue(film.getEvaluations().contains(nouvelleEval), 
             "L'évaluation précédente doit être supprimée.");
  }
  /**
   * Teste la récupération de l'ensemble des films par genre.
   */
  
  @Test
  public void testEnsembleFilmsGenre() {
    gestionFilm.ajouterGenres(film, Genre.Action);  
    Set<Film> films = gestionnaire.ensembleFilmsGenre(Genre.Action);
    assertNotNull(films, "L'ensemble des films par genre ne doit pas être nul.");
  }

  /**
   * Teste l'évaluation moyenne d'un film.
   */
  @Test
  public void testEvaluationMoyenne() throws LocationException, NonConnecteException {
    Evaluation eval1 = new Evaluation(4, utilisateur, film);
    Evaluation eval2 = new Evaluation(3, utilisateur2, film); 
    String ps1 = utilisateur.getPseudo();
    String mp1 = utilisateur.getMotDePasse();
   
    
    gestionnaire.connexion(ps1, mp1);
    gestionnaire.ajouterEvaluation(film, eval1);
    gestionnaire.deconnexion();
    gestionnaire.inscription("janedoee", "password", utilisateur2.getInfo());
    gestionnaire.connexion("janedoee", "password");
    gestionnaire.ajouterEvaluation(film, eval2);
    gestionnaire.deconnexion();
    double moyenne = gestionnaire.evaluationMoyenne(film);
    assertEquals(3.5, moyenne, "L'évaluation moyenne doit être correcte.");
  }
}


