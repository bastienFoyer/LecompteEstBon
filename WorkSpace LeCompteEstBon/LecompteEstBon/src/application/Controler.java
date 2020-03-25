package application;

import java.awt.Label;
import java.awt.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controler du jeu
 */
public class Controler {
	
	/** Modele du jeu */
	private Modele modele;
	
	@FXML
	private TextField pseudo;
	
	@FXML
	private Label cible;
	
	@FXML
	private TextField operationEnCours;
	
	@FXML
	private TextArea tableauope;
	
	@FXML
	public void init() {
		modele = new Modele();
		modele.init();
		initVue();
	}
	
	/**
	 * Lancement de la partie
	 * @param e evenement
	 */
	public void jouer(ActionEvent e) {
		modele.setPseudo(pseudo.getText());
		modele.jouer();//TODO
		//TODO
	}
	
	public void annuler(ActionEvent e) {
		operationEnCours.setText("");
	}
	
	public void valider(ActionEvent e) {
		//TODO
	}
	
	public void suppprimerAll(ActionEvent e) {
		operationEnCours.setText("");
		tableauope.setText("");
	}
	
	public void proposer(ActionEvent e) {
		//TODO
	}
	
	/**
	 * Initialisae la vue
	 */
	private void initVue() {
		pseudo.setText(modele.getPseudo());
		//TODO timer 
		cible.setText(""+modele.getCible());
		operationEnCours.setText("");
		//TODO afficher la liste des Etape
	}
	
	
	
}
