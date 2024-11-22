package tests;

import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionFilm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe GestionFilm.
 */
public class GestionFilmTest {

  private GestionFilm gestionFilm;

  private Artiste realisateur;
  private Film film1;
  private Film film2;

  @BeforeEach
    void setUp() {
    // Initialisation du gestionnaire de films
    gestionFilm = new GestionFilm();

    // Création d'un réalisateur
    realisateur = new Artiste("Spielberg", "Steven", "Américain");

    // Création de films
    film1 = gestionFilm.creerFilm("Jurassic Park", realisateur, 1993, 12);
    film2 = gestionFilm.creerFilm("E.T.", realisateur, 1982, 8);

    // Ajout d'un film à la collection des films louables
    gestionFilm.ouvrirLocation(film1);
  }

  /**
     * Teste la création d'un artiste.
     */
  @Test
    void testCreerArtiste() {
    Artiste artiste = gestionFilm.creerArtiste("Nolan", "Christopher", "Anglais");
    assertNotNull(artiste);
    assertEquals("Nolan", artiste.getNom());
    assertEquals("Christopher", artiste.getPrenom());
    assertEquals("Anglais", artiste.getNationalite());
  }

  /**
     * Teste la suppression d'un artiste.
     */
  
  @Test
    void testSupprimerArtiste() {
    Artiste artiste = gestionFilm.creerArtiste("Tarantino", "Quentin", "Américain");
    boolean supprime = gestionFilm.supprimerArtiste(artiste);
    assertTrue(supprime);
    assertFalse(gestionFilm.supprimerArtiste(artiste)); // Déjà supprimé
  }

  /**
     * Teste la création d'un film.
     */
  @Test
    void testCreerFilm() {
    Film nouveauFilm = gestionFilm.creerFilm("Interstellar", realisateur, 2014, 10);
    assertNotNull(nouveauFilm);
    assertEquals("Interstellar", nouveauFilm.getTitre());
    assertEquals(realisateur, nouveauFilm.getRealisateur());
    assertEquals(2014, nouveauFilm.getAnnee());
    assertEquals(10, nouveauFilm.getAgeLimite());
  }

  /**
     * Teste l'ouverture de la location d'un film.
     */
  @Test
    void testOuvrirLocation() {
    boolean ouvert = gestionFilm.ouvrirLocation(film2);
    assertTrue(ouvert);
    assertTrue(film2.isEstOuvertalocation());
  }

  /**
     * Teste la fermeture de la location d'un film.
     */
  @Test
    void testFermerLocation() {
    boolean ferme = gestionFilm.fermerLocation(film1);
    assertTrue(ferme);
    assertFalse(film1.isEstOuvertalocation());
  }

  /**
     * Teste le cas où un film est déjà ouvert à la location.
     */
  @Test
    void testOuvrirLocationDejaOuverte() {
    boolean ouvert = gestionFilm.ouvrirLocation(film1); // Déjà louable
    assertFalse(ouvert);
  }

  /**
     * Teste le cas où un film est déjà fermé à la location.
     */
  @Test
  void testFermerLocationDejaFermee() {
    boolean ferme = gestionFilm.fermerLocation(film2); // Pas louable
    assertFalse(ferme);
  }
  
  
  
  @Test
  public void testSetAfficheValide() {
    // Initialisation des données pour le test
    Artiste realisateur = new Artiste("chris", "nolan", "americain");
    Set<Genre> genres = new HashSet<>();
    genres.add(Genre.ScienceFiction);
    Film film = new Film("Inception", realisateur, 2010, 13, genres);

    // Action : définir une affiche
    String affiche = "inception.jpg";
    film.setAffiche(affiche);

    // Vérification
    assertEquals(affiche, film.getAffiche(), "L'affiche du film devrait être correctement définie.");
  }

  @Test
  public void testSetAfficheInvalide() {
    // Initialisation des données pour le test
    Artiste realisateur = new Artiste("chris", "nolan", "americain");
    Set<Genre> genres = new HashSet<>();
    genres.add(Genre.ScienceFiction);
    Film film = new Film("Inception", realisateur, 2010, 13, genres);

    // Action et vérification : ajout d'une affiche invalide (null)
    Exception exception1 = assertThrows(IllegalArgumentException.class, () -> film.setAffiche(null));
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception1.getMessage());

    // Action et vérification : ajout d'une affiche invalide (chaîne vide)
    Exception exception2 = assertThrows(IllegalArgumentException.class, () -> film.setAffiche(""));
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception2.getMessage());
  }

  @Test
  public void testAfficheIncluseDansToString() {
    // Initialisation des données pour le test
    Artiste realisateur = new Artiste("chris", "nolan", "americain");
    Set<Genre> genres = new HashSet<>();
    genres.add(Genre.ScienceFiction);
    Film film = new Film("Inception", realisateur, 2010, 13, genres);

    // Action : définir une affiche
    String affiche = "inception.jpg";
    film.setAffiche(affiche);

    // Vérification : l'affiche doit apparaître dans toString
    String result = film.toString();
    assertTrue(result.contains("affiche='inception.jpg'"), "L'affiche devrait apparaître dans la représentation textuelle.");
  }
}
