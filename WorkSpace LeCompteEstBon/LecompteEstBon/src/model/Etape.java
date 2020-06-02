package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import exceptions.OperationException;

/**
 * Etape de jeux
 * @author Bastien 
 */
public class Etape {
	
	/** Tableau des plaques de nombres */
	private int[] tabplaques;
	
	/** Sauvegarde de l'operation */
	private String sauvegardeOperation;
	
	/** Résultat de l'étape */
	private int resultat;

	/**
	 * Constructor
	 */
	public Etape() {
		//Initialisation 6 plaques
		tabplaques = new int[6];
		
		//TODO
		LinkedList<Integer> plaquesInit = new LinkedList<Integer>();
		for (int i = 1; i <= 10; i++) {
			plaquesInit.add(i);
			plaquesInit.add(i);
		}
		plaquesInit.add(25);
		plaquesInit.add(50);
		plaquesInit.add(75);
		plaquesInit.add(100);
		for (int i = 0; i < 6; i++) {
			int numPlaqueSelectionnee = ThreadLocalRandom.current().nextInt(0,plaquesInit.size());
			tabplaques[i] = plaquesInit.get(numPlaqueSelectionnee);
			plaquesInit.remove(numPlaqueSelectionnee);
		}

	}

	/**
	 * Constructor Etape
	 * @param plaquesOld Ancienne plaque
	 * @param numPlaquesel1 Plaque 1 
	 * @param numPlaquesel2 Plaque 2
	 * @param operation Operation
	 * @param operations Ensemble des operation
	 * @throws OperationException 
	 */
	public Etape(int[] plaquesOld, int numPlaquesel1, int numPlaquesel2, String operation, String operations[])
			throws OperationException{

		// gestion des exceptions
		if (numPlaquesel1 < 0 || numPlaquesel1 >= plaquesOld.length || numPlaquesel2 < 0
				|| numPlaquesel2 >= plaquesOld.length || numPlaquesel1 == numPlaquesel2) {
			throw new OperationException("indices hors tableau");
		}

		// on recherche si l'opération existe dans le tableau OPERATIONS
		if (operation.length() != 1 || (Arrays.binarySearch(operations, operation)<0)) {
			throw new OperationException("opération invalide");
		}
		
		// vérifications spécifiques selon les opérateurs 
		if ((operation.contains("-") && (plaquesOld[numPlaquesel1] - plaquesOld[numPlaquesel2] < 0))
				|| (operation.contains("/") && plaquesOld[numPlaquesel1] % plaquesOld[numPlaquesel2] != 0)) {
			throw new OperationException("resultat invalide");
		}

		// calcul du résultat
		if (operation.contains("-"))
			resultat = plaquesOld[numPlaquesel1] - plaquesOld[numPlaquesel2];
		if (operation.contains("+"))
			resultat = plaquesOld[numPlaquesel1] + plaquesOld[numPlaquesel2];
		if (operation.contains("/"))
			resultat = plaquesOld[numPlaquesel1] / plaquesOld[numPlaquesel2];
		if (operation.contains("*"))
			resultat = plaquesOld[numPlaquesel1] * plaquesOld[numPlaquesel2];

		//Concervation du calcule pour RollBack
		sauvegardeOperation = 
				plaquesOld[numPlaquesel1] + " " + operation + " " + plaquesOld[numPlaquesel2] + " = " + resultat;

		// mise à jour des plaques
		tabplaques = new int[plaquesOld.length - 1];
		for (int i = 0, j = 0; i < plaquesOld.length; i++) {
			if (i != numPlaquesel1 && i != numPlaquesel2) {
				tabplaques[j] = plaquesOld[i];
				j++;
			}
		}

		tabplaques[tabplaques.length - 1] = resultat;

	}
	
	public int[] getTabplaques() {
		return tabplaques;
	}

	public void setTabplaques(int[] tabplaques) {
		this.tabplaques = tabplaques;
	}

	public String getSauvegardeOperation() {
		return sauvegardeOperation;
	}

	public void setSauvegardeOperation(String sauvegardeOperation) {
		this.sauvegardeOperation = sauvegardeOperation;
	}

	public int getResultat() {
		return resultat;
	}

	public void setResultat(int resultat) {
		this.resultat = resultat;
	}

	public int[] getPlaques() {
		return tabplaques;
	}

	public String getCalculString() {
		return sauvegardeOperation;
	}

	public String toString() {
		return "liste des plaques : " + Arrays.toString(tabplaques) + " resulat = " + resultat;
	}

}
