package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Film;
import location.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * Classe de test pour la classe {@link location.Artiste Artiste}.
 *
 * @author Bouberraga Cherif
 */

public class ArtisteTest {
  private Artiste artiste;
  private Film film1;
  private Film film2;

  /**
   * Configure un artiste et des films avant chaque test.
   */
  @BeforeEach
    void setUp() {
    artiste = new Artiste("Reeves", "Keanu", "Américaine");

    // Création de films pour les tests
    Set<Genre> genres1 = new HashSet<>();
    genres1.add(Genre.Action);
    film1 = new Film("john wick 1", artiste, 2014, 12, genres1);

    Set<Genre> genres2 = new HashSet<>();
    genres2.add(Genre.Aventure);
    film2 = new Film("john wick 2", artiste, 2017, 12, genres2);
  }

  /**
   * Test du constructeur.
   */
  @Test
  void testConstructeur() {
    // Création de l'objet Artiste avec les valeurs du constructeur
    Artiste artiste = new Artiste("Reeves", "Keanu", "Américaine");

    // Vérifications des attributs de l'objet
    assertEquals("Reeves", artiste.getNom());
    assertEquals("Keanu", artiste.getPrenom());
    assertEquals("Américaine", artiste.getNationalite());
    assertTrue(artiste.getFilmographie() != null); // Vérifie que la liste n'est pas null
    assertTrue(artiste.getFilmographie().isEmpty()); // Vérifie que la liste est vide
  }

  /**
   * Test des accesseurs et mutateurs pour le nom.
   */
  @Test
  void testNom() {
    artiste.setNom("dam");
    assertEquals("dam", artiste.getNom(), "Le nom devrait être mis à jour à 'dam'.");
    artiste.setNom(""); // Nom vide, ne doit pas changer
    assertEquals("dam", artiste.getNom(),
        "Le nom ne devrait pas être modifié avec une chaîne vide.");
  }

  /**
   * Test des accesseurs et mutateurs pour le prénom.
   */
  @Test
  void testPrenom() {
    artiste.setPrenom("cherif");
    assertEquals("cherif", artiste.getPrenom(), "Le prénom devrait être mis à jour à 'cherif'.");
  }

  /**
   * Test des accesseurs et mutateurs pour la nationalité.
   */
  @Test
  void testNationalite() {
    artiste.setNationalite("Française");
    assertEquals("Française", artiste.getNationalite(),
        "La nationalité devrait être mise à jour à 'Française'.");
  }


  /**
   * Test de la méthode equals.
   */
  @Test
  void testIdentiqueArtiste() {
    Artiste autreArtiste = new Artiste("Reeves", "Keanu", "Américaine");
    assertEquals(artiste, autreArtiste, "Deux artistes identiques devraient être égaux.");

    autreArtiste.setPrenom("cherif");
    assertNotEquals(artiste, autreArtiste, 
         "Deux artistes avec des prénoms différents ne devraient pas être égaux.");
  }

  /**
   * Test de la méthode hashCode.
   */
  @Test
  void testhashCode() {
    Artiste autreArtiste = new Artiste("Reeves", "Keanu", "Américaine");
    assertEquals(artiste.hashCode(), autreArtiste.hashCode(), 
             "Deux artistes identiques devraient avoir le même hashCode.");
  }

  /**
   * Test de la méthode toString.
   */
  @Test
  void testToString() {
    artiste.ajouterFilm(film1);
    artiste.ajouterFilm(film2);
    String attendu = "Artiste{nom='Reeves', prenom='Keanu',"
        + " nationalite='Américaine', filmographie=[" 
                         + film1.toString() + ", " + film2.toString() + "]}";
    assertEquals(attendu, artiste.toString(),
        "La méthode toString devrait produire la chaîne attendue.");
  }
}

