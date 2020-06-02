package model;

import javax.jws.WebParam.Mode;

public enum ModeJEU {

	INITIALISATION(0),
	WAIT(1),
	PREPARE(2),
	PLAY(3),
	SCORE(4);
	
	private int etape;
	
	ModeJEU(int pEtape) {
		etape = pEtape;
	}
	
	/**
	 * Retourne la prochaine étape de jeu , de celle entrée en paramètre
	 * @param modeDeJeuActuel mode de jeu actuel
	 * @return le nouveau mode de jeu
	 */
	public static ModeJEU nextStep(ModeJEU modeDeJeuActuel) {
		if(modeDeJeuActuel.equals(INITIALISATION)) return WAIT;
		if(modeDeJeuActuel.equals(WAIT)) return PREPARE;
		if(modeDeJeuActuel.equals(PREPARE)) return PLAY;
		if(modeDeJeuActuel.equals(PLAY)) return SCORE;
		if(modeDeJeuActuel.equals(SCORE)) return WAIT;
		return INITIALISATION;
	}
	
	/**
	 * Retourne l'ancienne étape de jeu , de celle entrée en paramètre
	 * @param modeDeJeuActuel mode de jeu actuel
	 * @return le nouveau mode de jeu
	 */
	public static ModeJEU  beforeStep(ModeJEU modeDeJeuActuel) {
		if(modeDeJeuActuel.equals(WAIT)) return INITIALISATION;
		if(modeDeJeuActuel.equals(PREPARE)) return WAIT;
		if(modeDeJeuActuel.equals(PLAY)) return PREPARE;
		if(modeDeJeuActuel.equals(SCORE)) return PLAY;
		if(modeDeJeuActuel.equals(INITIALISATION)) return INITIALISATION;
		return SCORE;
	}

	public int getEtape() {
		return etape;
	}

	public void setEtape(int etape) {
		this.etape = etape;
	}
	
}
