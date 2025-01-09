package location;

/**
 * Exception levée quand il y a un problème avec le compte d'un utilisateur ou
 * sa connexion.
 *
 * @author Eric Cariou
 */
public class NonConnecteException extends Exception {
  /**
* Constructeur .
* */
  public NonConnecteException(String message) {
    super(message);
  }
  
  /**
  * Constructeur .
    */
  public NonConnecteException() {
    super();
  }

  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = -2876441299971092712L;
  
  // A COMPLETER SI BESOIN
  
}
