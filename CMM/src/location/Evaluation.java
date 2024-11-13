package location;

// A COMPLETER

public class Evaluation {
	int note;
	String commentaire;
	Utilisateur utilis;
	public Evaluation(int note, String commentaire, Utilisateur utilis) {
        this.note = note;
        this.commentaire = commentaire;
        this.utilis = utilis;
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
        return utilis.pseudo();  
    }

    public void afficherEvaluation() {
        System.out.println("Note : " + note + "Commentaire : "+ commentaire + "Utilisateur : "+ utilis.pseudo);

    }
}
