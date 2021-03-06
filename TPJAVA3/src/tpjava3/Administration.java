// AUTEUR : Rached, groupe 3
package tpjava3;

import DAO.DAO;
import DAO.DAOFactory;
import beans.QuestionBDD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Administration extends Tab {

    private final TextField chReponse;
    private final TextField chQuestion;
    private final RadioButton chNiveau1;
    private final RadioButton chNiveau2;
    private final ComboBox chNum;
    private final Button btnAjouter;
    private final Button btnModifier;
    private final Button btnEffacer;
    private int valNiv;

    public Administration() {
        DAO<QuestionBDD> questiondao = DAOFactory.getQuestionDAO();

        this.setText("Administration");

        //Initialiser liste de selection n° question
        ObservableList<String> numQuestion
                = FXCollections.observableArrayList();
        numQuestion.add("Question n°");

        QuestionBDD quest1 = new QuestionBDD();
        for (int i = 1; i <= questiondao.compter(); i++) {
            numQuestion.add(Integer.toString(i));
        }

        this.chNum = new ComboBox(numQuestion);
        this.chNum.setMinHeight(50);
        chNum.getSelectionModel().selectFirst();
        Label nlabel = new Label("Si modification");
        nlabel.setFont(new Font("Arial", 20));
        nlabel.setTranslateX(23);
        nlabel.setTranslateY(0);

        HBox numHb = new HBox();
        numHb.getChildren().addAll(this.chNum, nlabel);
        this.chNum.setTranslateX(20);
        numHb.setAlignment(Pos.CENTER);

        // RadioButton des niveaux
        final ToggleGroup group = new ToggleGroup();
        this.chNiveau1 = new RadioButton("Niveau 1");
        this.chNiveau2 = new RadioButton("Niveau 2");
        this.chNiveau1.setFont(new Font("Arial", 20));
        this.chNiveau2.setFont(new Font("Arial", 20));
        chNiveau1.setToggleGroup(group);
        chNiveau2.setToggleGroup(group);
        chNiveau1.setSelected(true);
        valNiv = 1;
        VBox nhb = new VBox();
        nhb.getChildren().addAll(this.chNiveau1, this.chNiveau2);
        nhb.setSpacing(5);
        nhb.setAlignment(Pos.CENTER);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton niv = (RadioButton) t1.getToggleGroup().getSelectedToggle();
                if (niv.getText().equals("Niveau 1")) {
                    valNiv = 1;
                } else {
                    valNiv = 2;
                }
            }
        });

        // champs de saisie question/reponse
        Label qLabel = new Label("Question : ");
        qLabel.setFont(new Font("Arial", 30));
        this.chQuestion = new TextField("");
        this.chQuestion.setFont(new Font("Arial", 15));
        chQuestion.setPrefWidth(400);
        HBox qhb = new HBox();
        qhb.getChildren().addAll(qLabel, this.chQuestion);
        qhb.setSpacing(5);
        qhb.setAlignment(Pos.CENTER);
        Label rLabel = new Label("Réponse : ");
        rLabel.setFont(new Font("Arial", 30));
        this.chReponse = new TextField("");
        this.chReponse.setFont(new Font("Arial", 15));
        chReponse.setPrefWidth(400);
        HBox rhb = new HBox();
        rhb.getChildren().addAll(rLabel, this.chReponse);
        rhb.setSpacing(5);
        rhb.setAlignment(Pos.CENTER);

        // boutons
        this.btnAjouter = new Button("Ajouter");
        this.btnAjouter.setFont(new Font("Arial", 17));
        this.btnModifier = new Button("Modifier");
        this.btnModifier.setFont(new Font("Arial", 17));
        this.btnEffacer = new Button("Effacer");
        this.btnEffacer.setFont(new Font("Arial", 17));
        HBox bhb = new HBox();
        bhb.getChildren().addAll(this.btnAjouter, this.btnModifier, btnEffacer);
        bhb.setSpacing(20);
        bhb.setAlignment(Pos.CENTER);

        VBox vb = new VBox();
        vb.getChildren().addAll(numHb, nhb, qhb, rhb, bhb);
        vb.setSpacing(25);
        vb.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(vb);
        this.setContent(bp);

        // TRAITEMENT BOUTON Ajouter
        btnAjouter.setOnAction(event -> {
            QuestionBDD quest2 = questiondao.create(
                    new QuestionBDD(null, this.chQuestion.getText(), this.chReponse.getText(), valNiv));
            Popup pop = new Popup();
            if ((!chQuestion.getText().equals("")) && (!chReponse.getText().equals(""))) {
                pop.alertSauv();
                chNiveau1.setSelected(true);
                this.chQuestion.clear();
                this.chReponse.clear();
            } else {
                pop.alertInfo();
                chNiveau1.setSelected(true);
            }
        });

        // TRAITEMENT BOUTON MODIFIER
        btnModifier.setOnAction(event -> {
            QuestionBDD quest3 = new QuestionBDD();
            int numero = chNum.getSelectionModel().getSelectedIndex();
            quest3 = questiondao.getObj(numero);
            quest3 = questiondao.find(quest3.getId());
            quest3.setEnonce(this.chQuestion.getText());
            quest3.setReponse(this.chReponse.getText());
            quest3.setNiveau(valNiv);
            quest3 = questiondao.update(quest3);
            Popup pop = new Popup();
            if ((!chQuestion.getText().equals("")) && (!chReponse.getText().equals(""))) {
                pop.alerModif();
                chNum.getSelectionModel().selectFirst();
                chNiveau1.setSelected(true);
                this.chQuestion.clear();
                this.chReponse.clear();
            } else {
                pop.alertInfo();
                chNum.getSelectionModel().selectFirst();
                chNiveau1.setSelected(true);
            }
        });

        // TRAITEMENT BOUTON EFFACER
        btnEffacer.setOnAction(event -> {

            chNum.getSelectionModel().selectFirst();
            chNiveau1.setSelected(true);
            this.chQuestion.clear();
            this.chReponse.clear();
        });

        // TRAITEMENT SELECTION N° QUESTION
        chNum.setOnAction(event -> {
            int numero = chNum.getSelectionModel().getSelectedIndex();
            if (numero != 0) {
                QuestionBDD quest4 = new QuestionBDD();
                quest4 = questiondao.getObj(numero);
                this.chQuestion.setText(quest4.getEnonce());
                this.chReponse.setText(quest4.getReponse());
                int niv = quest4.getNiveau();
                if (niv == 1) {
                    chNiveau1.setSelected(true);
                } else if (niv == 2) {
                    chNiveau2.setSelected(true);
                }
            }
        });
    }
}
