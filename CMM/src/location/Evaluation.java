package location;

public class Evaluation {
    private int note;
    private String commentaire;
    private Utilisateur utilis;
    private Film film; // Nouvel attribut pour lier l'évaluation à un film

    public Evaluation(int note, String commentaire, Utilisateur utilis, Film film) {
        this.note = note;
        this.commentaire = commentaire;
        this.utilis = utilis;
        this.film = film;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getUtilisateurPseudo() {
        return utilis.getPseudo();  
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void afficherEvaluation() {
        System.out.println("Film : " + film.getTitre() + " | Note : " + note + " | Commentaire : " + commentaire + " | Utilisateur : " + utilis.getPseudo());
    }
}
