package location;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
   * Classe GestionUtilisateur.
   * Gère les utilisateurs et leurs connexions au système.
   */
public class GestionUtilisateur implements Serializable {
  private static final long serialVersionUID = 1L;
  private static GestionUtilisateur instance;
  private Utilisateur utilisateurConnecte;
  private final  Set<Utilisateur> utilisateurs;

  /**
     * Constructeur par défaut.
     * Initialise l'ensemble des utilisateurs et marque aucun utilisateur comme connecté.
     */
  private GestionUtilisateur() {
    this.utilisateurs = new HashSet<>();
    this.utilisateurConnecte = null;
  }
  

  
  /**
   * Retourne une instance unique de la classe {@code GestionUtilisateur}.
   * Cette méthode garantit que l'instance est créée une seule fois (singleton).
   * Elle est thread-safe grâce au mot-clé {@code synchronized}.
   *
   * @return L'instance unique de {@code GestionUtilisateur}.
   */
  public static synchronized GestionUtilisateur getInstance() {
    if (instance == null) {
      instance = new GestionUtilisateur();
    }
    return instance;
  }
  /**
     * Ajoute un nouvel utilisateur au système.
     *
     * @param utilisateur L'utilisateur à ajouter.
     * @return true si l'utilisateur est ajouté avec succès, false sinon.
     */
  
  public boolean ajouteUtilisateur(Utilisateur utilisateur) { 
    if (utilisateurs == null) {
      System.out.println("Collection utilisateurs non initialisée !");
      return false;
    }
    if (utilisateur == null) {
      System.out.println("Tentative d'ajout d'un utilisateur null !");
      return false;
    }
    if (estPseudoExistant(utilisateur.getPseudo())) {
      System.out.println("Pseudo déjà existant : " + utilisateur.getPseudo());
      return false;
    }
    boolean ajoutReussi = utilisateurs.add(utilisateur);
    System.out.println("Après ajout - Taille : " + utilisateurs.size());
    System.out.println("Ajout utilisateur réussi : " + ajoutReussi);
    return ajoutReussi;
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
   *
   */
  public void deconnecterUtilisateur() {
    utilisateurConnecte.setEstConnecte(false); 
    utilisateurConnecte = null;
  }
  
  
  
  /**
   * Connecte un utilisateur si les informations fournies sont valides.
   *
   * @param utilisateur Le utilisateur concerne par la connexion.
   * 
   */
  public void connecterUtilisateur(Utilisateur utilisateur)  {
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
    return this.utilisateurs;
  }
  
  /**
   * Remplace l'ensemble des utilisateurs gérés par cette instance.
   * Si l'ensemble fourni est non nul, il est utilisé pour remplacer 
   * les utilisateurs existants. Sinon, la liste est vidée.
   *
   * @param utilisateurs Un ensemble d'objets {@code Utilisateur} à gérer, 
   *                     ou {@code null} pour vider l'ensemble actuel.
   */
  public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
    this.utilisateurs.clear();
    if (utilisateurs != null) {
      this.utilisateurs.addAll(utilisateurs);
    }
  }
}
