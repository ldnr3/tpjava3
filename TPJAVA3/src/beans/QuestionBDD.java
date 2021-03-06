// Auteurs : Frédéric et Rached

package beans;

public class QuestionBDD {

    private Long id;
    private String enonce;
    private String reponse;
    private int niveau;

    public QuestionBDD() {
    }

    public QuestionBDD(Long id,String enonce, String reponse, int niveau) {
        this.id = id;
        this.enonce = enonce;
        this.reponse = reponse;
        this.niveau = niveau;
    }

    public long getId() {
        return id;
    }
    
    
    public String getEnonce() {
        return enonce;
    }

    public String getReponse() {
        return reponse;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setId(long id) {
        this.id = id;
    }
    
   
    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Question { id = " + this.id + ", enonce = " + this.enonce
                + ", reponse = " + this.reponse + ", niveau = " + this.niveau + "}";
    }
}
