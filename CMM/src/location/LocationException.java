package location;

/**
 * Exception levée quand il y a un problème lors de la location d'un film par un
 * utilisateur.
 *
 * @author Eric Cariou
 */
public class LocationException extends Exception {
  /**
* Constructeur .
  */
  public LocationException(String string) {
    super(string);
  }

  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = -3365565475174636290L;
  
  // A COMPLETER SI BESOIN
  
}
