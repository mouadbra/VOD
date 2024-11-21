package tests;
import org.junit.jupiter.api.*;

import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.GestionUtilisateur;
import location.InformationPersonnelle;
import location.Utilisateur;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
/**
 * Tests JUnit de la classe {@link location.Evaluation
 * Evaluation}.
 *
 * @author Bouberraga Cherif
 * @see location.Evaluation
 */ 
class EvaluationTest {
	private Utilisateur utilisateur;
    private Film film;
    private Evaluation evaluation;
    /**
     * Instancie une evaluation pour les tests.
     *
     * @throws Exception ne peut pas être levée ici
     */
    
    @BeforeEach
    public void setUp() {
        // Création des données nécessaires pour les tests
        utilisateur = new Utilisateur("user1", "password123", new InformationPersonnelle("Doe", "John"));
        Artiste realisateur = new Artiste("Spielberg", "Steven", "Américaine");
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.Action); 
        film = new Film("Jurassic Park", realisateur, 1993, 13, genres);
    }
    /**
     * Test du constructeur d'évaluation sans commentaire.
     */
    @Test
    public void testConstructeurSansCommentaire() {
    	// Connexion de l'utilisateur
        utilisateur.setEstConnecte(true);  // L'utilisateur est connecté
        
    	evaluation = new Evaluation(4, utilisateur, film);

        assertNotNull(evaluation, "L'objet Evaluation ne doit pas être null.");
        assertEquals(utilisateur, evaluation.getUtilisateurPseudo(), "L'utilisateur pseudo doit être correct.");        assertEquals(film, evaluation.getFilm(), "Le film associé doit être correct.");
        assertEquals(film, evaluation.getFilm(), "Le film associé doit être correct.");
        assertEquals(4, evaluation.getNote(), "La note initiale doit être 4.");
        assertNull(evaluation.getCommentaire(), "Le commentaire doit être null lorsqu'il n'est pas fourni.");
    }

    /**
     * Test de l'ajout d'un commentaire par un utilisateur non connecté.
     */
    @Test
    public void testAjoutCommentaireUtilisateurNonConnecte() {
    	// Cas où l'utilisateur n'est pas connecté
        utilisateur.setEstConnecte(false);  // L'utilisateur n'est pas connecté

        // Tentative de création de l'évaluation
        Exception exception = assertThrows(IllegalStateException.class,
            () -> new Evaluation(4, "Super film !", utilisateur, film),
            "Un utilisateur non connecté ne devrait pas pouvoir ajouter un commentaire."
        );

        // Vérification du message d'exception
        assertEquals("L'utilisateur doit être connecté pour ajouter un commentaire.", exception.getMessage());
    }

    /**
     * Test de l'ajout d'un commentaire pour un film non loué.
     */
    @Test
    public void testAjoutCommentaireFilmNonLoue() {
    	// Connexion de l'utilisateur
        utilisateur.setEstConnecte(true);  // L'utilisateur est connecté

        // Tentative de création de l'évaluation pour un film non loué
        Exception exception = assertThrows(IllegalStateException.class,
            () -> new Evaluation(4, "Super film !", utilisateur, film),
            "Le film doit être dans l'historique des films loués pour ajouter un commentaire."
        );

        // Vérification du message d'exception
        assertEquals("Le film doit être dans l'historique des films loués pour ajouter un commentaire.", exception.getMessage());
    }

    /**
     * Test de l'ajout d'un commentaire dans des conditions valides.
     */
    @Test
    public void testAjoutCommentaireConditionsValides() {
        // Connexion de l'utilisateur
        utilisateur.setEstConnecte(true);  // L'utilisateur est connecté

        // L'utilisateur loue un film
        utilisateur.ajouterFilmenLocation(film);  // Ajouter le film à la location

        // Création de l'évaluation pour le film
        evaluation = new Evaluation(4, utilisateur, film);

        // Ajouter un commentaire pour le film
        evaluation.setCommentaire("Un classique intemporel !");

        // Vérification
        assertEquals("Un classique intemporel !", evaluation.getCommentaire(),
            "Le commentaire doit être ajouté correctement lorsque toutes les conditions sont remplies.");
    }

    /**
     * Test de la validation de la note, elle doit être entre 0 et 5.
     */
    @Test
    public void testValidationNote() {
        // Connexion de l'utilisateur
        utilisateur.setEstConnecte(true);

        // Vérification que la note est entre 0 et 5
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> new Evaluation(6, utilisateur, film),  // Note invalide, supérieure à 5
            "La note doit être entre 0 et 5."
        );

        // Vérification du message d'exception
        assertEquals("La note doit être entre 0 et 5.", exception.getMessage());
    }

    /**
     * Test de la validation de la note minimale (0).
     */
    @Test
    public void testValidationNoteMinimale() {
        // Connexion de l'utilisateur
        utilisateur.setEstConnecte(true);

        // Vérification que la note ne peut pas être inférieure à 0
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> new Evaluation(-1, utilisateur, film),  // Note invalide, inférieure à 0
            "La note doit être entre 0 et 5."
        );

        // Vérification du message d'exception
        assertEquals("La note doit être entre 0 et 5.", exception.getMessage());
    }
}