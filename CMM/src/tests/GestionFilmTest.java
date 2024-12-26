package tests;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionFilm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void testCreerArtiste() {
        Artiste artiste = gestionFilm.creerArtiste("Nolan", "Christopher", "Anglais");
        assertNotNull(artiste);
        assertEquals("Nolan", artiste.getNom());
        assertEquals("Christopher", artiste.getPrenom());
        assertEquals("Anglais", artiste.getNationalite());
    }

    @Test
    void testCreerArtisteInvalide() {
        assertNull(gestionFilm.creerArtiste(null, "Christopher", "Anglais"));
        assertNull(gestionFilm.creerArtiste("", "Christopher", "Anglais"));
        assertNull(gestionFilm.creerArtiste("Nolan", "Christopher", null));
    }

    @Test
    void testCreerArtisteDuplique() {
        gestionFilm.creerArtiste("Nolan", "Christopher", "Anglais");
        assertNull(gestionFilm.creerArtiste("Nolan", "Christopher", "Américain"));
    }

    @Test
    void testSupprimerArtiste() {
        Artiste artiste = gestionFilm.creerArtiste("Tarantino", "Quentin", "Américain");
        boolean supprime = gestionFilm.supprimerArtiste(artiste);
        assertTrue(supprime);
        assertFalse(gestionFilm.supprimerArtiste(artiste)); // Déjà supprimé
    }

    @Test
    void testSupprimerArtisteNull() {
        assertFalse(gestionFilm.supprimerArtiste(null));
    }

    @Test
    void testSupprimerArtisteAvecFilm() {
        assertFalse(gestionFilm.supprimerArtiste(realisateur));
    }

    @Test
    void testCreerFilm() {
        Film nouveauFilm = gestionFilm.creerFilm("Interstellar", realisateur, 2014, 10);
        assertNotNull(nouveauFilm);
        assertEquals("Interstellar", nouveauFilm.getTitre());
        assertEquals(realisateur, nouveauFilm.getRealisateur());
        assertEquals(2014, nouveauFilm.getAnnee());
        assertEquals(10, nouveauFilm.getAgeLimite());
    }

    @Test
    void testCreerFilmInvalide() {
        assertNull(gestionFilm.creerFilm(null, realisateur, 2014, 10));
        assertNull(gestionFilm.creerFilm("", realisateur, 2014, 10));
        assertNull(gestionFilm.creerFilm("Test", null, 2014, 10));
        assertNull(gestionFilm.creerFilm("Test", realisateur, 0, 10));
        assertNull(gestionFilm.creerFilm("Test", realisateur, 2014, -1));
    }

    @Test
    void testCreerFilmDuplique() {
        assertNull(gestionFilm.creerFilm("Jurassic Park", realisateur, 2000, 12));
    }

    @Test
    void testAjouterActeurs() {
        Artiste acteur1 = gestionFilm.creerArtiste("Smith", "Will", "Américain");
        Artiste acteur2 = gestionFilm.creerArtiste("Roberts", "Julia", "Américaine");
        
        assertTrue(gestionFilm.ajouterActeurs(film1, acteur1, acteur2));
        assertTrue(film1.getActeurs().contains(acteur1));
        assertTrue(film1.getActeurs().contains(acteur2));
    }

    @Test
    void testAjouterActeursInvalides() {
        assertFalse(gestionFilm.ajouterActeurs(null, realisateur));
        assertFalse(gestionFilm.ajouterActeurs(film1, (Artiste[]) null));
    }

    @Test
    void testAjouterGenres() {
        assertTrue(gestionFilm.ajouterGenres(film1, Genre.Action, Genre.Aventure));
        assertTrue(film1.getGenres().contains(Genre.Action));
        assertTrue(film1.getGenres().contains(Genre.Aventure));
    }

    @Test
    void testAjouterGenresInvalides() {
        assertFalse(gestionFilm.ajouterGenres(null, Genre.Action));
        assertFalse(gestionFilm.ajouterGenres(film1, (Genre[]) null));
    }

    @Test
    void testOuvrirLocation() {
        assertTrue(gestionFilm.ouvrirLocation(film2));
        assertTrue(film2.isEstOuvertalocation());
    }

    @Test
    void testOuvrirLocationDejaOuverte() {
        assertFalse(gestionFilm.ouvrirLocation(film1));
    }

    @Test
    void testFermerLocation() {
        assertTrue(gestionFilm.fermerLocation(film1));
        assertFalse(film1.isEstOuvertalocation());
    }

    @Test
    void testFermerLocationDejaFermee() {
        assertFalse(gestionFilm.fermerLocation(film2));
    }

    @Test
    void testEnsembleFilms() {
        Set<Film> films = gestionFilm.ensembleFilms();
        assertEquals(2, films.size());
        assertTrue(films.contains(film1));
        assertTrue(films.contains(film2));
    }

    @Test
    void testEnsembleActeurs() {
        Artiste acteur = gestionFilm.creerArtiste("Ford", "Harrison", "Américain");
        gestionFilm.ajouterActeurs(film1, acteur);
        
        Set<Artiste> acteurs = gestionFilm.ensembleActeurs();
        assertEquals(1, acteurs.size());
        assertTrue(acteurs.contains(acteur));
    }

    @Test
    void testEnsembleRealisateurs() {
        Set<Artiste> realisateurs = gestionFilm.ensembleRealisateurs();
        assertEquals(1, realisateurs.size());
        assertTrue(realisateurs.contains(realisateur));
    }

    @Test
    void testEnsembleFilmsRealisateur() {
        Set<Film> filmsRealisateur = gestionFilm.ensembleFilmsRealisateur(realisateur);
        assertEquals(2, filmsRealisateur.size());
        assertTrue(filmsRealisateur.contains(film1));
        assertTrue(filmsRealisateur.contains(film2));
    }

    @Test
    void testEnsembleFilmsRealisateurInvalide() {
        assertNull(gestionFilm.ensembleFilmsRealisateur(null));
        Artiste autreRealisateur = gestionFilm.creerArtiste("Test", "Test", "Test");
        assertNull(gestionFilm.ensembleFilmsRealisateur(autreRealisateur));
    }

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

    @Test
    void testEnsembleFilmsActeurInvalide() {
        assertNull(gestionFilm.ensembleFilmsActeur(null));
        Artiste acteur = gestionFilm.creerArtiste("Test", "Test", "Test");
        assertNull(gestionFilm.ensembleFilmsActeur(acteur));
    }

    @Test
    void testGetArtiste() {
        Artiste artiste = gestionFilm.creerArtiste("Test", "Test", "Test");
        assertEquals(artiste, gestionFilm.getArtiste("Test", "Test"));
        assertNull(gestionFilm.getArtiste("Inconnu", "Inconnu"));
    }

    @Test
    void testGetFilm() {
        assertEquals(film1, gestionFilm.getFilm("Jurassic Park"));
        assertNull(gestionFilm.getFilm("Film Inexistant"));
    }

    @Test
    void testEnsembleFilmsGenre() {
        gestionFilm.ajouterGenres(film1, Genre.Action);
        gestionFilm.ajouterGenres(film2, Genre.Action);
        
        Set<Film> filmsAction = gestionFilm.ensembleFilmsGenre(Genre.Action);
        assertEquals(2, filmsAction.size());
        assertTrue(filmsAction.contains(film1));
        assertTrue(filmsAction.contains(film2));
    }

    @Test
    void testEnsembleFilmsGenreInvalide() {
        assertNull(gestionFilm.ensembleFilmsGenre(null));
        assertNull(gestionFilm.ensembleFilmsGenre(Genre.Action));  // Aucun film de ce genre
    }

    @Test
    void testGetActeur() {
        Artiste acteur = gestionFilm.creerArtiste("Ford", "Harrison", "Américain");
        gestionFilm.ajouterActeurs(film1, acteur);
        
        assertEquals(acteur, gestionFilm.getActeur("Ford", "Harrison"));
        assertNull(gestionFilm.getActeur("Inconnu", "Inconnu"));
    }

    @Test
    void testGetRealisateur() {
        assertEquals(realisateur, gestionFilm.getRealisateur("Spielberg", "Steven"));
        assertNull(gestionFilm.getRealisateur("Inconnu", "Inconnu"));
    }

    @Test
    public void testSetAfficheValide() {
        String affiche = "inception.jpg";
        film1.setAffiche(affiche);
        assertEquals(affiche, film1.getAffiche());
    }

    @Test
    public void testSetAfficheInvalide() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> film1.setAffiche(null));
        assertEquals("L'affiche ne peut pas être vide ou nulle.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> film1.setAffiche(""));
        assertEquals("L'affiche ne peut pas être vide ou nulle.", exception2.getMessage());
    }

    @Test
    public void testAfficheIncluseDansToString() {
        String affiche = "inception.jpg";
        film1.setAffiche(affiche);
        String result = film1.toString();
        assertTrue(result.contains("affiche='inception.jpg'"));
    }
}