package location;

import java.util.HashSet;
import java.util.Set;

/**
   * Classe GestionUtilisateur.
   * Gère les utilisateurs et leurs connexions au système.
   */
public class GestionUtilisateur {
  private Utilisateur utilisateurConnecte;
  private final Set<Utilisateur> utilisateurs;

  /**
     * Constructeur par défaut.
     * Initialise l'ensemble des utilisateurs et marque aucun utilisateur comme connecté.
     */
  public GestionUtilisateur() {
    this.utilisateurs = new HashSet<>();
    this.utilisateurConnecte = null;
  }

  /**
     * Ajoute un nouvel utilisateur au système.
     *
     * @param utilisateur L'utilisateur à ajouter.
     * @return true si l'utilisateur est ajouté avec succès, false sinon.
     */
  public boolean ajouteUtilisateur(Utilisateur utilisateur) {
    if (utilisateur == null || estPseudoExistant(utilisateur.getPseudo())) {
      return false;
    }
    return utilisateurs.add(utilisateur);
  }
  /**
     * Supprime un utilisateur par son pseudo.
     *
     * @param pseudo Le pseudo de l'utilisateur à supprimer.
     * @return true si l'utilisateur est supprimé avec succès, false sinon.
     */
  
  public boolean supprimeUtilisateur(String pseudo) {
    Utilisateur utilisateur = getUtilisateurParPseudo(pseudo);
    if (utilisateur != null) {
      return utilisateurs.remove(utilisateur);
    }
    return false;
  }

   

  /**
     * Modifie le mot de passe d'un utilisateur.
     *
     * @param pseudo Le pseudo de l'utilisateur.
     * @param ancienMotDePasse L'ancien mot de passe.
     * @param nouveauMotDePasse Le nouveau mot de passe.
     * @return true si la modification est réussie, false sinon.
     */
  public boolean modifieMotDePasse(String pseudo, String ancienMotDePasse,
      String nouveauMotDePasse) {
    Utilisateur utilisateur = getUtilisateurParPseudo(pseudo);
    if (utilisateur != null && utilisateur.getMotDePasse().equals(ancienMotDePasse)) {
      utilisateur.setMotDePasse(nouveauMotDePasse);
      return true;
    }
    return false;
  }

  /**
     * Vérifie si un pseudo existe déjà dans le système.
     *
     * @param pseudo Le pseudo à vérifier.
     * @return true si le pseudo existe, false sinon.
     */
  public boolean estPseudoExistant(String pseudo) {
    for (Utilisateur utilisateur : utilisateurs) {
      if (utilisateur.getPseudo().equals(pseudo)) {
        return true;
      }
    }
    return false;
  }


  /**
     * Retourne l'utilisateur actuellement connecté.
     *
     * @return L'utilisateur connecté, ou null s'il n'y en a pas.
     */
  public Utilisateur getUtilisateurConnecte() {
    return utilisateurConnecte;
  }
  
  
  
  
  /**
   * Déconnecte l'utilisateur actuellement connecté.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  public void deconnecterUtilisateur() {
    utilisateurConnecte.setEstConnecte(false); 
    utilisateurConnecte = null;
  }
  
  
  
  /**
   * Connecte un utilisateur si les informations fournies sont valides.
   *
   * @param pseudo Le pseudo de l'utilisateur.
   * @param mdp Le mot de passe de l'utilisateur.
   */
  public void connecterUtilisateur(String pseudo, String mdp)  {
  

    Utilisateur utilisateur = getUtilisateurParPseudo(pseudo);
    utilisateurConnecte = utilisateur;
    utilisateur.setEstConnecte(true);
  }



  /**
     * Recherche un utilisateur par son pseudo.
     *
     * @param pseudo Le pseudo de l'utilisateur.
     * @return L'utilisateur correspondant, ou null si non trouvé.
     */
  public Utilisateur getUtilisateurParPseudo(String pseudo) {
    for (Utilisateur u : utilisateurs) {
      if (u.getPseudo().equals(pseudo)) {
        return u;
      }
    }
    return null;
  }


  /**
     * Retourne l'ensemble des utilisateurs.
     *
     * @return Un ensemble des utilisateurs enregistrés.
     */
  public Set<Utilisateur> getUtilisateurs() {
    return new HashSet<>(utilisateurs);
  }
}
