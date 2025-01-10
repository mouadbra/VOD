package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import location.GestionUtilisateur;
import location.InformationPersonnelle;
import location.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




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
	this.gestionUtilisateur = GestionUtilisateur.getInstance();
    InformationPersonnelle info = new InformationPersonnelle("John", "Doe", "123 Main St", 25);
    utilisateur = new Utilisateur("johndoe", "password123", info);
  }
  
  
  /**
   * Teste la récupération de l'ensemble des utilisateurs.
   */
  @Test
  public void testGetUtilisateurs() {
    // Vérifier que le système est vide au départ
    assertTrue(gestionUtilisateur.getUtilisateurs().isEmpty(),
              "L'ensemble des utilisateurs doit être vide initialement.");

    // Ajouter plusieurs utilisateurs
    InformationPersonnelle info1 = new InformationPersonnelle("Alice", "Smith", "456 Elm St", 30);
    InformationPersonnelle info2 = new InformationPersonnelle("Bob", "Johnson", "789 Oak St", 40);
    Utilisateur utilisateur1 = new Utilisateur("alice123", "passAlice", info1);
    Utilisateur utilisateur2 = new Utilisateur("bob456", "passBob", info2);

    gestionUtilisateur.ajouteUtilisateur(utilisateur1);
    gestionUtilisateur.ajouteUtilisateur(utilisateur2);

    // Vérifier que les utilisateurs ajoutés sont dans l'ensemble
    Set<Utilisateur> utilisateurs = gestionUtilisateur.getUtilisateurs();
    assertEquals(2, utilisateurs.size(), "L'ensemble des utilisateurs"
        + " doit contenir 2 utilisateurs.");
    assertTrue(utilisateurs.contains(utilisateur1), "L'ensemble doit contenir "
        + "'utilisateur1'.");
    assertTrue(utilisateurs.contains(utilisateur2), "L'ensemble doit contenir 'utilisateur2'.");
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
    assertFalse(gestionUtilisateur.ajouteUtilisateur(null), 
            "L'ajout d'un utilisateur null doit échouer.");
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
    assertFalse(gestionUtilisateur.modifieMotDePasse("inexistant", "ancienMotDePasse",
            "nouveauMotDePasse"),
            "La modification du mot de passe d'un utilisateur inexistant doit échouer.");
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
  

  /**
   * Teste connexion utilisateur.
   */
  
  @Test
  public void testConnecterUtilisateur() {
    InformationPersonnelle info = new InformationPersonnelle("John", "Doe", "123 Main St", 25);

    Utilisateur utilisateur = new Utilisateur("johndoe", "password123", info);
      
    gestionUtilisateur.connecterUtilisateur(utilisateur);

    assertTrue(utilisateur.isEstConnecte(), "L'utilisateur doit être marqué comme connecté.");

    assertEquals(utilisateur, gestionUtilisateur.getUtilisateurConnecte(), 
              "L'utilisateur connecté doit être celui fourni à la méthode.");
  }

  /**
   * Teste deconnexion utilisateur.
   */
  
  @Test
  public void testDeconnecterUtilisateur() {
    InformationPersonnelle info = new InformationPersonnelle("John", "Doe", "123 Main St", 25);

    Utilisateur utilisateur = new Utilisateur("johndoe", "password123", info);

    gestionUtilisateur.connecterUtilisateur(utilisateur);

    gestionUtilisateur.deconnecterUtilisateur();

    assertFalse(utilisateur.isEstConnecte(), "L'utilisateur doit être marqué comme déconnecté.");

    assertNull(gestionUtilisateur.getUtilisateurConnecte(), 
              "Aucun utilisateur ne doit être connecté après la déconnexion.");
  }

 
 
}
