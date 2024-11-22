package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.Genre;
import location.InformationPersonnelle;
import location.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




/**
 * Tests unitaires pour la classe Utilisateur.
 */
class UtilisateurTest {

  private Utilisateur utilisateur;
  private InformationPersonnelle infoPersonnelle;
  private Film film1;
  private Film film2;
  Artiste realisateur; 

  /**
     * Initialisation des objets avant chaque test.
     */
  @BeforeEach
  void setUp() {
    Set<Genre> genres = new HashSet<>();
    genres.add(Genre.Action);
    infoPersonnelle = new InformationPersonnelle("Doe",
    "John", "123 Rue Imaginaire", 15); // Utilisateur de 15 ans
    utilisateur = new Utilisateur("johnDoe", "password123", infoPersonnelle);
    realisateur = new Artiste("Spielberg", "Steven", null);
    film1 = new Film("Film 1", realisateur, 2020, 12, genres);
    film2 = new Film("Film 2", realisateur, 2020, 12, genres);
  }

  /**
     * Test du constructeur avec des arguments valides.
     */
  @Test
  void testConstructeurValide() {
    assertEquals("johnDoe", utilisateur.getPseudo());
    assertEquals("password123", utilisateur.getMotDePasse());
    assertEquals(infoPersonnelle, utilisateur.getInfo());
    assertTrue(utilisateur.getFilmsEnLocation().isEmpty());
    assertTrue(utilisateur.getEvaluations().isEmpty());
    assertFalse(utilisateur.isEstConnecte());
  }

  /**
     * Test du constructeur avec un pseudo null.
     */
  @Test
  void testConstructeurPseudoNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Utilisateur(null, "password123", infoPersonnelle);
    });
    assertEquals("Les arguments pseudo, motDePasse et info ne doivent "
        + "pas être null.", exception.getMessage());
  }

  /**
     * Test du constructeur avec un mot de passe null.
     */
  @Test
  void testConstructeurMotDePasseNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Utilisateur("johnDoe", null, infoPersonnelle);
    });
    assertEquals("Les arguments pseudo, motDePasse et info "
        + "ne doivent pas être null.", exception.getMessage());
  }

  /**
     * Test du constructeur avec des informations personnelles nulles.
     */
  @Test
  void testConstructeurInfoNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Utilisateur("johnDoe", "password123", null);
    });
    assertEquals("Les arguments pseudo, motDePasse et info ne "
        + "doivent pas être null.", exception.getMessage());
  }

  /**
     * Test de l'état de connexion de l'utilisateur.
     */
  @Test
  void testConnexion() {
    utilisateur.setEstConnecte(true);
    assertTrue(utilisateur.isEstConnecte());

    utilisateur.setEstConnecte(false);
    assertFalse(utilisateur.isEstConnecte());
  }

  /**
   * Test de l'ajoute de film.
   */

  @Test
  void testAjouterFilmenLocation() {
    utilisateur.ajouterFilmenLocation(film1);

    // Vérification après ajout
    assertTrue(utilisateur.getFilmsEnLocation().contains(film1),
          "Le film doit être ajouté à la liste des films en location.");
    assertTrue(utilisateur.gethistoriqueFilmsEnLocation().contains(film1),
          "Le film doit être ajouté à l'historique des films loués.");
  }


  /**
     * Test du retrait d'un film de la liste de location.
     */
  @Test
  void testRetirerFilmEnLocation() {
    utilisateur.ajouterFilmenLocation(film1);
    utilisateur.ajouterFilmenLocation(film2);

    utilisateur.retirerFilmEnLocation(film1);
    assertFalse(utilisateur.getFilmsEnLocation().contains(film1));
    assertTrue(utilisateur.getFilmsEnLocation().contains(film2));
  }

  /**
     * Test de l'ajout d'une évaluation.
     */
  @Test
  void testAjoutEvaluation() {
    Evaluation eval = new Evaluation(5, "Très bon film", utilisateur, film1);
    utilisateur.ajouterEvaluation(eval);
    assertTrue(utilisateur.getEvaluations().contains(eval));
  }

  /**
     * Test de la modification du mot de passe et du pseudo.
     */
  @Test
  void testModificationMotDePasseEtPseudo() {
    utilisateur.setMotDePasse("newPassword");
    utilisateur.setPseudo("newPseudo");

    assertEquals("newPassword", utilisateur.getMotDePasse());
    assertEquals("newPseudo", utilisateur.getPseudo());
  }
}
