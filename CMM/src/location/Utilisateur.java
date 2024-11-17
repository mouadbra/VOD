package location;
import java.util.HashSet;
import java.util.Set;

public class Utilisateur {
    private String pseudo;
    private String motDePasse;
    private InformationPersonnelle info;
    private Set<Film> filmsEnLocation;
    private Set<Evaluation> evaluations;

    // Constructeur
    public Utilisateur(String pseudo, String motDePasse, InformationPersonnelle info) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.info = info;
        this.filmsEnLocation = new HashSet<>();
        this.evaluations = new HashSet<>();
    }

    // Getters
    public String getPseudo() {
        return pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public InformationPersonnelle getInfo() {
        return info;
    }

    public Set<Film> getFilmsEnLocation() {
        return filmsEnLocation;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    // Setters
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

 // Méthodes pour gérer les films en location
    public Boolean ajouterFilmEnLocation(Film film) {
        if (filmsEnLocation.contains(film)) {
          return false;	
        }
        
        if (filmsEnLocation.size() < 3) { // Limite de 3 films en location
            filmsEnLocation.add(film);
            return true;
        } else {
         return false;	
        }
    }


    public void retirerFilmEnLocation(Film film) {
        filmsEnLocation.remove(film);
    }

    // Méthodes pour gérer les évaluations
    public void ajouterEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
    }
}
