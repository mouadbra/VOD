package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionFilm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * La classe gestion film pour le test.
 */
public class GestionFilmTest {
  /**
  * La gestion film pour les tests.
  */
  private GestionFilm gestionFilm;
  /**
   * Le réalisateur du film pour les tests.
   */
  private Artiste realisateur;
  /**
   * Le film 1.
   */
  private Film film1;
  /**
   * Le film 2.
   */
  private Film film2;
  /**
   * Initialisation des attributs avant chaque test.
   */
  
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
   * Test de creation d'artistes.
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
   * Test de creation d'artistes non valide.
   */

  @Test
  void testCreerArtisteInvalide() {
    assertNull(gestionFilm.creerArtiste(null, "Christopher", "Anglais"));
    assertNull(gestionFilm.creerArtiste("", "Christopher", "Anglais"));
    assertNull(gestionFilm.creerArtiste("Nolan", "Christopher", null));
  }
  /**
   * Test de creation d'artiste duplique.
   */
  
  @Test
  void testCreerArtisteDuplique() {
    gestionFilm.creerArtiste("Nolan", "Christopher", "Anglais");
    assertNull(gestionFilm.creerArtiste("Nolan", "Christopher", "Américain"));
  }
  /**
   * Test de suppression d'artistes.
   */

  @Test
  void testSupprimerArtiste() {
    Artiste artiste = gestionFilm.creerArtiste("Tarantino", "Quentin", "Américain");
    boolean supprime = gestionFilm.supprimerArtiste(artiste);
    assertTrue(supprime);
    assertFalse(gestionFilm.supprimerArtiste(artiste)); // Déjà supprimé
  }

  /**
   * Test de suppression d'artistes null.
   */
  
  @Test
  void testSupprimerArtisteNull() {
    assertFalse(gestionFilm.supprimerArtiste(null));
  }
  
  /**
   * Test de suppression d'artiste avec film.
   */

  @Test
  void testSupprimerArtisteAvecFilm() {
    assertFalse(gestionFilm.supprimerArtiste(realisateur));
  }
  
  /**
   * Test de creation d'un film.
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
   * Test de creation d'un film non valide.
   */
  
  @Test
  void testCreerFilmInvalide() {
    assertNull(gestionFilm.creerFilm(null, realisateur, 2014, 10));
    assertNull(gestionFilm.creerFilm("", realisateur, 2014, 10));
    assertNull(gestionFilm.creerFilm("Test", null, 2014, 10));
    assertNull(gestionFilm.creerFilm("Test", realisateur, 0, 10));
    assertNull(gestionFilm.creerFilm("Test", realisateur, 2014, -1));
  }

  /**
   * Test de creation de film duplique.
   */
  @Test
  void testCreerFilmDuplique() {
    assertNull(gestionFilm.creerFilm("Jurassic Park", realisateur, 2000, 12));
  }
  /**
   * Test d'ajout  d'acteurs.
   */

  @Test
  void testAjouterActeurs() {
    Artiste acteur1 = gestionFilm.creerArtiste("Smith", "Will", "Américain");
    Artiste acteur2 = gestionFilm.creerArtiste("Roberts", "Julia", "Américaine");
        
    assertTrue(gestionFilm.ajouterActeurs(film1, acteur1, acteur2));
    assertTrue(film1.getActeurs().contains(acteur1));
    assertTrue(film1.getActeurs().contains(acteur2));
  }

  /**
   * Test d'ajout d'acteur non valide.
   */
  
  @Test
  void testAjouterActeursInvalides() {
    assertFalse(gestionFilm.ajouterActeurs(null, realisateur));
    assertFalse(gestionFilm.ajouterActeurs(film1, (Artiste[]) null));
  }
  /**
   * Test d'ajout de genres.
   */
  
  @Test
  void testAjouterGenres() {
    assertTrue(gestionFilm.ajouterGenres(film1, Genre.Action, Genre.Aventure));
    assertTrue(film1.getGenres().contains(Genre.Action));
    assertTrue(film1.getGenres().contains(Genre.Aventure));
  }

  /**
   * Test d'ajout de genres invalides.
   */
  @Test
  void testAjouterGenresInvalides() {
    assertFalse(gestionFilm.ajouterGenres(null, Genre.Action));
    assertFalse(gestionFilm.ajouterGenres(film1, (Genre[]) null));
  }
  
  /**
   * Test d'ouverture de location d'un film.
   */
  @Test
  void testOuvrirLocation() {
    assertTrue(gestionFilm.ouvrirLocation(film2));
    assertTrue(film2.isEstOuvertalocation());
  }

  /**
   * Test d'ouverture d'un film deja ouvert.
   */
  @Test
  void testOuvrirLocationDejaOuverte() {
    assertFalse(gestionFilm.ouvrirLocation(film1));
  }
  /**
   * Test de fermeture de location.
   */
  
  @Test
  void testFermerLocation() {
    assertTrue(gestionFilm.fermerLocation(film1));
    assertFalse(film1.isEstOuvertalocation());
  }

  /**
   * Test de fermeture de location pour un film deja fermé.
   */
  @Test
  void testFermerLocationDejaFermee() {
    assertFalse(gestionFilm.fermerLocation(film2));
  }

  /**
   * Test ensemble film.
   */
  @Test
  void testEnsembleFilms() {
    Set<Film> films = gestionFilm.ensembleFilms();
    assertEquals(2, films.size());
    assertTrue(films.contains(film1));
    assertTrue(films.contains(film2));
  }
  /**
   * Test ensemble acteurs.
   */
  
  @Test
  void testEnsembleActeurs() {
    Artiste acteur = gestionFilm.creerArtiste("Ford", "Harrison", "Américain");
    gestionFilm.ajouterActeurs(film1, acteur);
        
    Set<Artiste> acteurs = gestionFilm.ensembleActeurs();
    assertEquals(1, acteurs.size());
    assertTrue(acteurs.contains(acteur));
  }

  /**
   * Test ensemble realisateurs.
   */
  @Test
  void testEnsembleRealisateurs() {
    Set<Artiste> realisateurs = gestionFilm.ensembleRealisateurs();
    assertEquals(1, realisateurs.size());
    assertTrue(realisateurs.contains(realisateur));
  }

  /**
   * Test ensemble films realisateurs.
   */
  @Test
  void testEnsembleFilmsRealisateur() {
    Set<Film> filmsRealisateur = gestionFilm.ensembleFilmsRealisateur(realisateur);
    assertEquals(2, filmsRealisateur.size());
    assertTrue(filmsRealisateur.contains(film1));
    assertTrue(filmsRealisateur.contains(film2));
  }

  /**
   * Test ensemble films realisateurs invalide.
   */
  @Test
  void testEnsembleFilmsRealisateurInvalide() {
    assertNull(gestionFilm.ensembleFilmsRealisateur(null));
    Artiste autreRealisateur = gestionFilm.creerArtiste("Test", "Test", "Test");
    assertNull(gestionFilm.ensembleFilmsRealisateur(autreRealisateur));
  }

  /**
   * Test ensemble films acteurs.
   */
  @Test
  void testEnsembleFilmsActeur() {
    Artiste acteur = gestionFilm.creerArtiste("Ford", "Harrison", "Américain");
    gestionFilm.ajouterActeurs(film1, acteur);
    gestionFilm.ajouterActeurs(film2, acteur);
        
    Set<Film> filmsActeur = gestionFilm.ensembleFilmsActeur(acteur);
    assertEquals(2, filmsActeur.size());
    assertTrue(filmsActeur.contains(film1));
    assertTrue(filmsActeur.contains(film2));
  }
  /**
   * Test ensemble films acteurs non valides.
   */
  
  @Test
  void testEnsembleFilmsActeurInvalide() {
    assertNull(gestionFilm.ensembleFilmsActeur(null));
    Artiste acteur = gestionFilm.creerArtiste("Test", "Test", "Test");
    assertNull(gestionFilm.ensembleFilmsActeur(acteur));
  }

  /**
   * Test de recuperation d'artistes.
   */
  @Test
  void testGetArtiste() {
    Artiste artiste = gestionFilm.creerArtiste("Test", "Test", "Test");
    assertEquals(artiste, gestionFilm.getArtiste("Test", "Test"));
    assertNull(gestionFilm.getArtiste("Inconnu", "Inconnu"));
  }
  /**
   * Test de recuperation de film.
   */
  
  @Test
  void testGetFilm() {
    assertEquals(film1, gestionFilm.getFilm("Jurassic Park"));
    assertNull(gestionFilm.getFilm("Film Inexistant"));
  }
  /**
   * Test ensemble films genres.
   */
  
  @Test
  void testEnsembleFilmsGenre() {
    gestionFilm.ajouterGenres(film1, Genre.Action);
    gestionFilm.ajouterGenres(film2, Genre.Action);
        
    Set<Film> filmsAction = gestionFilm.ensembleFilmsGenre(Genre.Action);
    assertEquals(2, filmsAction.size());
    assertTrue(filmsAction.contains(film1));
    assertTrue(filmsAction.contains(film2));
  }
  /**
   * Test ensemble films genres non valide.
   */
  
  @Test
  void testEnsembleFilmsGenreInvalide() {
    assertNull(gestionFilm.ensembleFilmsGenre(null));
    assertNull(gestionFilm.ensembleFilmsGenre(Genre.Action));  // Aucun film de ce genre
  }
  /**
   * Test de get acteurs.
   */
  
  @Test
  void testGetActeur() {
    Artiste acteur = gestionFilm.creerArtiste("Ford", "Harrison", "Américain");
    gestionFilm.ajouterActeurs(film1, acteur);
        
    assertEquals(acteur, gestionFilm.getActeur("Ford", "Harrison"));
    assertNull(gestionFilm.getActeur("Inconnu", "Inconnu"));
  }
  /**
   * Test de get realisateur.
   */
  
  @Test
  void testGetRealisateur() {
    assertEquals(realisateur, gestionFilm.getRealisateur("Spielberg", "Steven"));
    assertNull(gestionFilm.getRealisateur("Inconnu", "Inconnu"));
  }
  /**
   * Test d'affichage valide.
   */
  
  @Test
  public void testSetAfficheValide() {
    String affiche = "inception.jpg";
    film1.setAffiche(affiche);
    assertEquals(affiche, film1.getAffiche());
  }
  /**
   * Test d'affichage invalide.
   */
  
  @Test
  public void testSetAfficheInvalide() {
    Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
          film1.setAffiche(null));
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception1.getMessage());

    Exception exception2 = assertThrows(IllegalArgumentException.class, () -> film1.setAffiche(""));
    assertEquals("L'affiche ne peut pas être vide ou nulle.", exception2.getMessage());
  }
  /**
   * Test d'affichage incluse dans toString.
   */
  
  @Test
  public void testAfficheIncluseDansToString() {
    String affiche = "inception.jpg";
    film1.setAffiche(affiche);
    String result = film1.toString();
    assertTrue(result.contains("affiche='inception.jpg'"));
  }
}