package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.InformationPersonnelle;
import location.Utilisateur;

import java.util.Set;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Utilisateur.
 */
public class UtilisateurTest {
    private Utilisateur utilisateur;
    private Film film1;
    private Film film2;
    private Film film3;
    private Artiste realisateur;
    private Set<Genre> genres;

    /**
     * Méthode d'initialisation avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        InformationPersonnelle info = new InformationPersonnelle("Jane", "Smith", "456 Elm St", 30);
        utilisateur = new Utilisateur("janesmith", "mypassword", info);
        
        realisateur = new Artiste("John"," Doe", null ); // Exemple de création d'un réalisateur
        genres = new HashSet<>();
        genres.add(Genre.Action);
        genres.add(Genre.Drame);

        film1 = new Film("Film1", realisateur, 2020, 18, genres);
        film2 = new Film("Film2", realisateur, 2021, 18, genres);
        film3 = new Film("Film3", realisateur, 2022, 18, genres);
    }

    /**
     * Teste l'ajout de films en location.
     */
    @Test
    public void testAjouterFilmEnLocation() {
        assertTrue(utilisateur.ajouterFilmenLocation(film1), 
            "Le film doit être ajouté en location.");
        assertTrue(utilisateur.ajouterFilmenLocation(film2),
            "Le film doit être ajouté en location.");
        assertTrue(utilisateur.ajouterFilmenLocation(film3),
            "Le film doit être ajouté en location.");
        assertFalse(utilisateur.ajouterFilmenLocation(new Film("Film4", realisateur, 2022, 18, genres)),
            "L'ajout d'un quatrième film doit échouer.");
    }

    /**
     * Teste le retrait de films de la liste des films en location.
     */
    @Test
    public void testRetirerFilmEnLocation() {
        utilisateur.ajouterFilmenLocation(film1);
        utilisateur.retirerFilmEnLocation(film1);
        assertFalse(utilisateur.getFilmsEnLocation().contains(film1),
            "Le film ne doit plus être en location.");
    }

    /**
     * Teste l'ajout d'évaluations à un utilisateur.
     */
    @Test
    public void testAjouterEvaluation() {
        Evaluation evaluation = new Evaluation(5,"Excellent film!", null,null);
        utilisateur.ajouterEvaluation(evaluation);
        assertTrue(utilisateur.getEvaluations().contains(evaluation),
            "L'évaluation doit être ajoutée.");
    }

    /**
     * Teste l'historique des films en location.
     */
    @Test
    public void testGethistoriqueFilmsEnLocation() {
        utilisateur.ajouterFilmenLocation(film1);
        utilisateur.ajouterFilmenLocation(film2);
        Set<Film> historique = utilisateur.gethistoriqueFilmsEnLocation();
        assertTrue(historique.contains(film1),
            "L'historique doit contenir le film.");
        assertTrue(historique.contains(film2),
            "L'historique doit contenir le film.");
    }
 }
