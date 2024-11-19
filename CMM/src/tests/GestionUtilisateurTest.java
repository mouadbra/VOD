package tests;

import location.GestionUtilisateur;
import location.InformationPersonnelle;
import location.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe GestionUtilisateur.
 */
public class GestionUtilisateurTest {
  private GestionUtilisateur gestionUtilisateur;
  private Utilisateur utilisateur;

  /**
     * Méthode d'initialisation avant chaque test.
     */
  @BeforeEach
    public void setUp() {
    gestionUtilisateur = new GestionUtilisateur();
    InformationPersonnelle info = new InformationPersonnelle("John", "Doe","123 Main St",25 );
    utilisateur = new Utilisateur("johndoe", "password123", info);
  }

  /**
     * Teste l'ajout d'un utilisateur au système.
     */
  @Test
    public void testAjouteUtilisateur() {
    assertTrue(gestionUtilisateur.ajouteUtilisateur(utilisateur), 
         "L'utilisateur doit être ajouté avec succès.");
    assertFalse(gestionUtilisateur.ajouteUtilisateur(utilisateur), 
            "L'utilisateur ne doit pas être ajouté une deuxième fois.");
  }

  /**
     * Teste la suppression d'un utilisateur par pseudo.
     */
  @Test
    public void testSupprimeUtilisateur() {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    assertTrue(gestionUtilisateur.supprimeUtilisateur("johndoe"),
            "L'utilisateur doit être supprimé avec succès.");
    assertFalse(gestionUtilisateur.supprimeUtilisateur("johndoe"),
            "La suppression d'un utilisateur inexistant doit échouer.");
  }

  /**
     * Teste la modification du mot de passe d'un utilisateur.
     */
  @Test
    public void testModifieMotDePasse() {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    assertTrue(gestionUtilisateur.modifieMotDePasse("johndoe", "password123", "newpass"),
            "Le mot de passe doit être modifié avec succès.");
    assertFalse(gestionUtilisateur.modifieMotDePasse("johndoe", "wrongpass", "newpass"),
            "La modification avec un ancien mot de passe incorrect doit échouer.");
  }

  /**
     * Teste l'existence d'un pseudo dans le système.
     */
  @Test
    public void testEstPseudoExistant() {
    gestionUtilisateur.ajouteUtilisateur(utilisateur);
    assertTrue(gestionUtilisateur.estPseudoExistant("johndoe"),
            "Le pseudo doit exister.");
    assertFalse(gestionUtilisateur.estPseudoExistant("janedoe"),
            "Le pseudo ne doit pas exister.");
  }
}
