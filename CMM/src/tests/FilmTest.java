package tests;

import org.junit.jupiter.api.*;

import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.Utilisateur;

import java.util.*;

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
    Assertions.assertTrue(representation.contains(realisateur.toString()));
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
  void testCalculMoyenneEval() {
      // Test avec liste vide
      Assertions.assertEquals(0, film.calculmoyenneEval());
      
      // Ajout d'évaluations
      film.getEvaluations().add(new Evaluation(4, "Très bon film",this.utilis,this.film));
      film.getEvaluations().add(new Evaluation(5, "Excellent",this.utilis,this.film));
      film.getEvaluations().add(new Evaluation(3, "Moyen",this.utilis,this.film));
      
      // Test avec évaluations (4 + 5 + 3) / 3 = 4
      Assertions.assertEquals(4.0, film.calculmoyenneEval());
  }
}