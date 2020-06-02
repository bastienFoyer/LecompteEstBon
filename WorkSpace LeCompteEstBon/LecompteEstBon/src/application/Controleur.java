package application;

import java.time.LocalDateTime;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Model;
import model.SingletonFactoryModel;

/**
 * Controler du jeu
 * @author Bastien
 */
public class Controleur {

	private static final String MESSAGE_PSEUSO = "-> ICI <- Veuillez saisir un pseudo ";

	private Model model;

	private Timeline timeline;

	int numeroBoutonCourant;

	@FXML
	private Label tempslabel;

	@FXML
	private Label time;

	@FXML
	private Label nombreObjectif;

	@FXML
	private Button play;

	@FXML
	private Button scores;

	@FXML
	private Button annuler;

	@FXML
	private Button valider;

	@FXML
	private Button proposer;

	@FXML
	private Button supprimer;

	@FXML
	private ButtonBar operations;

	@FXML
	private ButtonBar plaqueChiffre;

	@FXML
	private TextField historique;

	@FXML
	private TextField pseudoPlayer;

	@FXML
	private TextArea logCalcul;

	private int nombreClique1 = 0;
	private int numeroBtClique1 = -1;
	private int nombreClique2 = 0;
	private int numeroBtClique2 = -1;
	private String operateurSelectionne = "";
	private int indexButtonOperateur = -1;
	private int timeSeconde;
	private int timeMinute;
	private int hours;
	private int minutes;
	private int seconds;

	@FXML
	private void actionAnnuler(ActionEvent evt) {
		annuler();
	}

	/**
	 * Supprime l'historique et le contenue de la partie en cours
	 * @param evt evennement
	 */
	@FXML
	private void actionSupprimer(ActionEvent evt) {
		plaqueChiffre.getButtons().clear();
		String lastInput = model.getLastEtape().getCalculString() + "\n";
		logCalcul.deleteText(logCalcul.getLength() - lastInput.length(), logCalcul.getLength());

		//On revient à l'Etape précedante
		model.removeLastEtape();

		//On récupère les plaques de la dernière étape
		int[] tabPlaques = model.getLastEtape().getPlaques();


		//On régenere les plaques
		for (int plaque : tabPlaques) {
			plaqueChiffre.getButtons().add(newTogglBtn(plaque));
		}
	}

	//TODO
	@FXML
	private void actionValider(ActionEvent evt) {
		try {
			model.jouer(numeroBtClique1, numeroBtClique2, operateurSelectionne);
			historique.setText("");
			logCalcul.appendText(model.getLastEtape().getCalculString() + "\n");
			restoreOperation();
			ToggleButton bt1 = (ToggleButton) plaqueChiffre.getButtons().get(numeroBtClique1);
			ToggleButton bt2 = (ToggleButton) plaqueChiffre.getButtons().get(numeroBtClique2);
			plaqueChiffre.getButtons().remove(bt1);
			plaqueChiffre.getButtons().remove(bt2);
			nombreClique1 = nombreClique2 = 0;
			numeroBtClique1 = numeroBtClique2 = -1;
			int[] newPlaques = model.getLastEtape().getPlaques();
			ToggleButton bt = newTogglBtn(newPlaques[newPlaques.length - 1]);
			plaqueChiffre.getButtons().add(bt);
		} catch (Exception e) {
			historique.setText(e.getMessage());
		}
	}

	@FXML
	private void actionProposer(ActionEvent evt) {
		if ((numeroBtClique1 != -1) && (numeroBtClique2 == -1) && (indexButtonOperateur == -1)) {
			ToggleButton bt = (ToggleButton) plaqueChiffre.getButtons().get(numeroBtClique1);
			nombreClique1 = Integer.parseInt(bt.getText());
			int duree = timeMinute * 60 + timeSeconde;
			duree = model.getTempsMax() - duree;
			resetTimer();
			try {
				model.score(nombreClique1, duree);
			} catch (Exception e) {
				e.printStackTrace();
			}
			displayVictoryScreen(Math.abs(Integer.parseInt(nombreObjectif.getText()) - nombreClique1), duree);
			annuler();
			restoreOperation();
			logCalcul.clear();
			plaqueChiffre.getButtons().clear();
			operations.getParent().getParent().setOpacity(0);
			operations.getParent().getParent().setMouseTransparent(true);
			pseudoPlayer.getParent().setOpacity(1);
			pseudoPlayer.getParent().setMouseTransparent(false);
			try {
				model.attendre();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Lancement de la partie
	 * @param evt évènement 
	 */
	@FXML
	private void actionJouer(ActionEvent evt) {
		try {
			if (!pseudoPlayer.getText().isEmpty()) {
				
				//initialise le model
				model.preparer(pseudoPlayer.getText());
				
				// initialisation plaques
				initPlaques();
				
				//Active la modification de l'operation
				operations.getParent().getParent().setMouseTransparent(false);
				//Affiche les operations
				operations.getParent().getParent().setOpacity(1);
				//Desactive la modification du pseudo
				pseudoPlayer.getParent().setMouseTransparent(true);
				//Grise le pseudo et le boutons
				pseudoPlayer.setOpacity(0.5);
				play.setOpacity(0.5);
				
				//démare le timer
				startTimer();
			} else {
				pseudoPlayer.setText(MESSAGE_PSEUSO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Initialise les plaques de la dernière étapes
	 */
	private void initPlaques() {
		//Plaques de la dernière étape
		int [] tabPlaques = model.getLastEtape().getPlaques();
		for (int plaque : tabPlaques) {
			ToggleButton btnToggle = newTogglBtn(plaque);
			plaqueChiffre.getButtons().add(btnToggle);

		}
	}

	@FXML
	private void actionScores(ActionEvent evt) {
		//initialisation de la pop up
		Alert bteDialog = new Alert(AlertType.INFORMATION);
		bteDialog.setTitle("Tableau des scores");
		Stage dStage = (Stage) bteDialog.getDialogPane().getScene().getWindow();
		dStage.getIcons().add(new Image("application/3ilogo.png"));
		
//		bteDialog.setHeaderText(null);
//		bteDialog.setGraphic(null);
		bteDialog.setContentText(model.getGereScore().extractListeScores());
		bteDialog.showAndWait();
		//TODO Affiche les scores internet exploreur
	}

	/**
	 * Affiche l'écran de victoire
	 * @param score score du joueur
	 * @param temps temps passer
	 */
	public void displayVictoryScreen(int score, int temps) {

		//Temps
		int minute = temps / 60;
		int seconde = temps % 60;

		//Pop up information
		Alert bteDialog = new Alert(AlertType.INFORMATION);
		Stage dStage = (Stage) bteDialog.getDialogPane().getScene().getWindow();

		bteDialog.setTitle("Score final");
		dStage.getIcons().add(new Image("application/3ilogo.png"));

		bteDialog.setHeaderText("Bravo vous avez gagné avec :");
		bteDialog.setGraphic(new ImageView("application/trophée.jpg"));

		bteDialog.setContentText(score + " :  (" +  minute + ":" + seconde + ")");
		bteDialog.showAndWait();
	}


	//TODO
	public void restoreOperation() {
		if (indexButtonOperateur != -1) {
			ToggleButton btOperateur = (ToggleButton) operations.getButtons().get(indexButtonOperateur);
			btOperateur.setMouseTransparent(false);
			btOperateur.setSelected(false);
			operateurSelectionne = "";
			indexButtonOperateur = -1;
		}
		historique.setText("");
	}

	/**
	 * Annule la dernière tentative
	 */
	//TODO
	public void annuler() {
		if (numeroBtClique1 != -1) {
			ToggleButton bt1 = (ToggleButton) plaqueChiffre.getButtons().get(numeroBtClique1);
			bt1.setMouseTransparent(false);
			bt1.setSelected(false);
		}
		if (numeroBtClique2 != -1) {
			ToggleButton bt2 = (ToggleButton) plaqueChiffre.getButtons().get(numeroBtClique2);
			bt2.setMouseTransparent(false);
			bt2.setSelected(false);
		}
		restoreOperation();
		nombreClique1 = nombreClique2 = 0;
		numeroBtClique1 = numeroBtClique2 = -1;
	}

	/**
	 * Demare le timer
	 */
	public void startTimer() {
		if (timeline == null) {
			timeline = new Timeline();
			KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (timeSeconde <= 0) {
						if (timeMinute <= 0) {
							timeline.stop();
							resetTimer();
							operations.getParent().getParent().setOpacity(0);
							operations.getParent().getParent().setMouseTransparent(true);
							return;
						}
						timeMinute--;
						timeSeconde = 59;
					} else {
						timeSeconde--;
					}
					if (timeMinute == 0) {
						tempslabel.setTextFill(Color.RED);
					}
					tempslabel.setText(String.format("%02d:%02d", timeMinute, timeSeconde));
				}
			});
			tempslabel.setTextFill(Color.BLACK);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.getKeyFrames().add(keyframe);
		}
		timeline.playFromStart();
	}

	/**
	 * Réinitialise le timer
	 */
	public void resetTimer() {
		//On esquive le nullPointer exception si il n'est pas existant
		if (timeline != null) {
			timeline.stop();
			timeSeconde = model.getTempsMax() % 60;
			timeMinute = model.getTempsMax() / 60;
			tempslabel.setText("("+ timeMinute+ ":"+ timeSeconde+")");
		}
	}

	/**
	 * Initialisation
	 */
	public void initialize() {
		// creation model
		SingletonFactoryModel.addInstance(180);
		model = SingletonFactoryModel.getInstance();
		numeroBoutonCourant = 0;
		// initialisation timer
		timeSeconde = model.getTempsMax() % 60;
		timeMinute = model.getTempsMax() / 60;
		tempslabel.setText("("+ timeMinute+ ":"+ timeSeconde+")");

		// initialisation operateur
		String[] tabOperations = model.getTabOperations();
		for (String op : tabOperations) {
			ToggleButton bt = new ToggleButton();
			bt.setId(op);
			bt.setText(op);
			bt.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					ToggleButton bt = (ToggleButton) event.getSource();
					System.out.println(bt.getId());
					if (operateurSelectionne.isEmpty() && nombreClique1 != 0) {
						operateurSelectionne = bt.getText();
						indexButtonOperateur = operations.getButtons().indexOf(bt);
						historique.setText(historique.getText() + " " + operateurSelectionne + " ");
						bt.setMouseTransparent(true);
					}
				}
			});
			operations.getButtons().add(bt);
		}

		// binding nombre à trouver
		nombreObjectif.textProperty().bind(model.getNombreRecherche().asString());

		// lancement horloge
		startHourTimer();

		// passage au mode attendre
		// on rend les fenêtres du bas transparentes et inactives tant que le
		// mode joueur n'est pas actif
		operations.getParent().getParent().setOpacity(0);
		operations.getParent().getParent().setMouseTransparent(true);
		try {
			model.attendre();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public ToggleButton newTogglBtn(int val) {
		ToggleButton bt = new ToggleButton();
		numeroBoutonCourant++;
		bt.setText("" + val);
		bt.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ToggleButton bt = (ToggleButton) event.getSource();
				System.out.println(bt.getText());
				if (nombreClique1 == 0) {
					nombreClique1 = Integer.parseInt(bt.getText());
					numeroBtClique1 = plaqueChiffre.getButtons().indexOf(bt);
					historique.setText("" + nombreClique1);
					bt.setMouseTransparent(true);
				} else {
					if (nombreClique2 == 0 && !operateurSelectionne.isEmpty()) {
						nombreClique2 = Integer.parseInt(bt.getText());
						numeroBtClique2 = plaqueChiffre.getButtons().indexOf(bt);
						historique.setText(historique.getText() + nombreClique2);
						bt.setMouseTransparent(true);
					} else {
						// gestion des erreurs de saisies
						if (operateurSelectionne.isEmpty()) {
							bt.setSelected(false);
						}
					}
				}
			}
		});
		return bt;
	}

	/**
	 * Demarre le timer
	 */
	public void startHourTimer() {
		Timeline hourTimeline;
		hours = LocalDateTime.now().getHour();
		minutes = LocalDateTime.now().getMinute();
		seconds = LocalDateTime.now().getSecond();
		hourTimeline = new Timeline();
		KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (seconds >= 59) {
					minutes++;
					seconds = 0;
				} else {
					seconds++;
				}
				if (minutes >= 60) {
					minutes = 0;
					hours++;
				}
				if (hours >= 24)
					hours = 0;
				time.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
			}
		});
		tempslabel.setTextFill(Color.BLACK);
		hourTimeline.setCycleCount(Timeline.INDEFINITE);
		hourTimeline.getKeyFrames().add(keyframe);
		hourTimeline.playFromStart();
		//timer.start();
	}
}
