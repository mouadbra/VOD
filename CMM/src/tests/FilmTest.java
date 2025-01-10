package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.InformationPersonnelle;
import location.Utilisateur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test unitaires de la classe Film.
 *
 *
 * @author Mouad Brahmi
 */


public class FilmTest {

  /**
   * Le film utilisé pour les tests.
   */
  private Film film;
    
  /**
   * Le réalisateur du film pour les tests.
   */
  private Artiste realisateur;
    
  /**
   * Le réalisateur du film pour les tests.
   */
  private Utilisateur utilis;
    
  /**
   * L'ensemble des genres pour les tests.
   */
  private Set<Genre> genres;

  /**
   * Initialisation des attributs avant chaque test.
   */
  @BeforeEach
    void setUp() {
    // Création du réalisateur
    realisateur = new Artiste("Nolan", "Christopher", "Americain");
        
    // Création des genres
    genres = new HashSet<>();
    genres.add(Genre.Action);
    genres.add(Genre.ScienceFiction);
        
    // Creation du film
    film = new Film("Inception", realisateur, 2010, 12, genres);
  }

  /**
   * Teste la création d'un film avec des paramètres valides.
   */
  @Test
    void testConstructeurValide() {
    Assertions.assertEquals("Inception", film.getTitre());
    Assertions.assertEquals(realisateur, film.getRealisateur());
    Assertions.assertEquals(2010, film.getAnnee());
    Assertions.assertEquals(12, film.getAgeLimite());
    Assertions.assertEquals(genres, film.getGenres());
    Assertions.assertTrue(film.getActeurs().isEmpty());
    Assertions.assertFalse(film.isEstOuvertalocation());
    Assertions.assertTrue(film.getEvaluations().isEmpty());
  }

  /**
   * Teste que les genres fournis au constructeur sont bien copiés.
   */
  @Test
  void testConstructeurCopieGenres() {
    // Modifie l'ensemble original des genres
    genres.add(Genre.Comedie);
        
    // Vérifie que les genres du film n'ont pas été modifiés
    Assertions.assertEquals(2, film.getGenres().size());
    Assertions.assertTrue(film.getGenres().contains(Genre.Action));
    Assertions.assertTrue(film.getGenres().contains(Genre.ScienceFiction));
    Assertions.assertFalse(film.getGenres().contains(Genre.Comedie));
  }

  /**
   * Teste la méthode toString.
   */
  @Test
    void testToString() {
    String representation = film.toString();
    Assertions.assertTrue(representation.contains("Inception"));
    Assertions.assertTrue(representation.contains("2010"));
    Assertions.assertTrue(representation.contains("12"));
    Assertions.assertTrue(representation.contains(realisateur.getNom()));
  }

  /**
   * Teste la création d'un film avec un titre null.
   */
  @Test
    void testConstructeurTitreNull() {
    Assertions.assertThrows(NullPointerException.class, 
            () -> new Film(null, realisateur, 2010, 12, genres));
  }

  /**
   * Teste la création d'un film avec un réalisateur null.
   */
  @Test
    void testConstructeurRealisateurNull() {
    Assertions.assertThrows(NullPointerException.class, 
            () -> new Film("Inception", null, 2010, 12, genres));
  }

  /**
   * Teste la création d'un film avec des genres null.
   */
  @Test
    void testConstructeurGenresNull() {
    Assertions.assertThrows(NullPointerException.class, 
            () -> new Film("Inception", realisateur, 2010, 12, null));
  }

  /**
   * Teste la création d'un film avec un âge limite négatif.
   */
  @Test
    void testConstructeurAgeLimiteNegatif() {
    Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new Film("Inception", realisateur, 2010, -1, genres));
  }

  /**
   * Teste la création d'un film avec une année invalide.
   */
  @Test
    void testConstructeurAnneeInvalide() {
    Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new Film("Inception", realisateur, -1, 12, genres));
  }
  
  
  /**
   * Teste le calcul de la moyenne des évaluations.
   */

  
  
  
  
  
  @Test
  void testSetAfficheValidValue() {
    // Création d'un film avec des paramètres valides
    Film film = new Film("Inception", new Artiste("chris", "nolan", "americain"),
         2010, 13, Set.of(Genre.Action));
      
    // Définition d'une affiche valide
    String afficheValide = "inception.jpg";
    film.setAffiche(afficheValide);

    // Vérification que l'affiche est bien définie
    assertEquals(afficheValide, film.getAffiche());
  }

  @Test
  void testSetAfficheNullValue() {
    // Création d'un film avec des paramètres valides
    Film film = new Film("Inception", new Artiste("chris", "nolan", "americain"),
        2010, 13, Set.of(Genre.Action));
      
    // Test avec une valeur null
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      film.setAffiche(null);
    });

    // Vérification du message d'exception
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception.getMessage());
  }

  @Test
  void testSetAfficheEmptyValue() {
    // Création d'un film avec des paramètres valides
    Film film = new Film("Inception", new Artiste("chris", "nolan", "americain"),
        2010, 13, Set.of(Genre.Action));
      
    // Test avec une chaîne vide
    Exception exception = assertThrows(IllegalArgumentException.class,
         () -> {
        film.setAffiche("");
      });

    // Vérification du message d'exception
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception.getMessage());
  }

  @Test
  void testSetAfficheWhitespaceOnlyValue() {
    // Création d'un film avec des paramètres valides
    Film film = new Film("Inception", new Artiste("chris", "nolan", "americain"),
        2010, 13, Set.of(Genre.Action));
      
    // Test avec une chaîne composée uniquement d'espaces
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      film.setAffiche("   ");
    });

    // Vérification du message d'exception
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception.getMessage());
  }

  @Test
  void testGetAfficheDefaultValue() {
    // Création d'un film sans définir l'affiche
    Film film = new Film("Inception", new Artiste("chris", "nolan", "americain"),
        2010, 13, Set.of(Genre.Action));
      
    // Vérification que l'affiche est initialement null
    assertNull(film.getAffiche());
  }
  
  @Test
  void testCalculMoyenneEval() {
    // Test avec liste vide
    Assertions.assertEquals(0, film.calculmoyenneEval());
    InformationPersonnelle info = new InformationPersonnelle("Nom", "Prenom");
    this.utilis = new Utilisateur("nom", "prenom", info);  
    // Ajout d'évaluations
    film.getEvaluations().add(new Evaluation(4, "Très bon film", this.utilis, this.film));
    film.getEvaluations().add(new Evaluation(5, "Excellent", this.utilis, this.film));
    film.getEvaluations().add(new Evaluation(3, "Moyen", this.utilis, this.film));
      
    // Test avec évaluations (4 + 5 + 3) / 3 = 4
    Assertions.assertEquals(4.0, film.calculmoyenneEval());
  }
  
  
}