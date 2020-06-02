package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import exceptions.OperationException;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Model du jeu
 * @author Bastien
 */
public class Model {
	/** Echelle maximum */
	protected static final int ECHELLE_MAX = 999;

	/** Echelle minimum */
	protected static final int ECHELLE_MIN = 100;

	/** Ensemble des operations possibles */
	protected static final String[] ALL_OPERATIONS = { "*", "+", "-", "/" };

	/** Pseudo choisit selectionné */
	private String pseudoChoisit;

	/** Gestion des scores */
	private GereScore gereScore;

	/** historique des Etape pour RollBack */
	private LinkedList<Etape> historique;

	/** Integer de recherche */
	SimpleIntegerProperty nombreRecherche;

	/** Temps Maximum */
	private int tempsMax;

	/** Durée de la partie */
	private int dureePartie;

	/** Mode de jeu de la partie */
	private ModeJEU modeJeu;

	/**
	 * Constructor 
	 * @param pTempsMax temps maximum de la partie
	 */
	public Model(int pTempsMax) {
		Etape init = new Etape();
		tempsMax = pTempsMax;
		historique = new LinkedList<>();
		gereScore = new GereScore();
		pseudoChoisit = "";
		//Ajout étape 0;
		historique.add(init);
		nombreRecherche = new SimpleIntegerProperty(ThreadLocalRandom.current().nextInt(ECHELLE_MIN, ECHELLE_MAX + 1));

		modeJeu = ModeJEU.INITIALISATION;
	}

	/**
	 * ETAPE 1 du jeu
	 * Passage Etape WAIT
	 * @throws OperationException
	 */
	public void attendre() throws OperationException{
		// controle de l'ordre des modesJEU 
		if ((modeJeu.equals(ModeJEU.INITIALISATION)) || (modeJeu.equals(ModeJEU.SCORE))) {
			modeJeu = ModeJEU.nextStep(modeJeu);
		} else {
			throw new OperationException();
		}

	}

	/**
	 * ETAPE 2 du jeu
	 * Prépare le lancement de la partie
	 * @param pseudo pseudo entrée par l'utilisateur
	 * @throws OperationException
	 */
	public void preparer(String pseudo) throws OperationException{
		if (modeJeu.equals(ModeJEU.WAIT)) {
			modeJeu = ModeJEU.nextStep(modeJeu);

			pseudoChoisit = pseudo;
			dureePartie = 0;

			//Nouveau nombre aléatoire entre Echellemin et EchelleMax +1 
			nombreRecherche.set(ThreadLocalRandom.current().nextInt(ECHELLE_MIN, ECHELLE_MAX + 1));

			if (historique.size() > 1) {
				historique.clear();
				Etape init = new Etape(); // Prochaine etape
				historique.add(init);
			}

		} else {
			throw new OperationException("TODO erreur non respect ordre opérations");
		}

	}

	/**
	 * ETAPE 3 du jeu
	 * @param plaque1
	 * @param plaque2
	 * @param operation
	 * @throws OperationException
	 */
	public void jouer(int plaque1, int plaque2, String operation) throws OperationException, Exception{
		// controle de l'ordre des modes
		if (modeJeu.equals(ModeJEU.PREPARE) || modeJeu.equals(ModeJEU.PLAY)) {
			modeJeu = ModeJEU.nextStep(modeJeu);


			//TODO supprimer if ?
			if (Arrays.binarySearch(ALL_OPERATIONS, operation) >= 0) {
				// action proposer
				Etape etapetCourante = new Etape(historique.getLast().getPlaques(), plaque1, plaque2, operation,
						ALL_OPERATIONS);
				historique.add(etapetCourante);
			}
			else{
				throw new Exception("operation inconnue");
			}

		} else {
			throw new OperationException("erreur non respect ordre opérations");
		}

	}

	/**
	 * ETAPE 4 du jeu
	 * @param valeurProposee
	 * @param duree
	 * @throws Exception
	 */
	public void score(int valeurProposee, int duree) throws OperationException{
		// controle de l'ordre des modes
		if (modeJeu.equals(ModeJEU.PLAY)) {
			modeJeu = ModeJEU.nextStep(modeJeu);
			gereScore.ajoutScore(pseudoChoisit, Math.abs(nombreRecherche.get()-valeurProposee),duree);

		} else {
			throw new OperationException();
		}

	}

	public Etape getLastEtape() {
		return historique.getLast();
	}

	public void removeLastEtape(){
		historique.removeLast();
	}


	public String getPseudoChoisit() {
		return pseudoChoisit;
	}


	public void setPseudoChoisit(String pseudoChoisit) {
		this.pseudoChoisit = pseudoChoisit;
	}


	public GereScore getGereScore() {
		return gereScore;
	}


	public void setGereScore(GereScore gereScore) {
		this.gereScore = gereScore;
	}


	public LinkedList<Etape> getHistorique() {
		return historique;
	}


	public void setHistorique(LinkedList<Etape> historique) {
		this.historique = historique;
	}


	public int getTempsMax() {
		return tempsMax;
	}


	public void setTempsMax(int tempsMax) {
		this.tempsMax = tempsMax;
	}

	public ModeJEU getModeJeu() {
		return modeJeu;
	}

	public void setModeJeu(ModeJEU modeJeu) {
		this.modeJeu = modeJeu;
	}

	public SimpleIntegerProperty getNombreRecherche() {
		return nombreRecherche;
	}


	public void setNombreRecherche(SimpleIntegerProperty nombreRecherche) {
		this.nombreRecherche = nombreRecherche;
	}


	public String[] getTabOperations() {
		return ALL_OPERATIONS;
	}

	@Override
	public String toString() {
		return "Model [pseudoChoisit=" + pseudoChoisit + ", gereScore=" + gereScore + ", historique=" + historique
				+ ", nombreRecherche=" + nombreRecherche + ", tempsMax=" + tempsMax + ", dureePartie=" + dureePartie
				+ ", modeJeu=" + modeJeu + "]";
	}

}