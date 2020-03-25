package application;

import java.util.LinkedList;

/**
 * Etape de jeu 
 */
public class Etape {
	
	/** Plaques */
	private  int[] tabPlaques;
	
	/** Gestion des scores*/
	private GereScore gestionDesScores;

	/** Pseudo entre 3 et 8 caractere*/
	private String pseudo;

	public Etape(String pseudo) {
		this.tabPlaques = new int[6];
		gestionDesScores = new GereScore(new LinkedList<>());
		gestionDesScores.readObject();
		this.pseudo = pseudo;
		//TODO création desz plaques
	}

	/**
	 * Calcule l'operation entre les plaques 1 et 2 en fonction des plaques choisit
	 * @param plaque1 Première plaque choisit 
	 * @param plaque2 Seconde plaque choisit
	 * @param operation : x + - /
	 * @return True si l'operation est possible et c'est bien déroulé
	 */
	public boolean calculePlaque(int plaque1 , int plaque2, String operation) {
		int result = 0;// Resultat de l'operation
		switch (operation) {
			case "+":
				result = plaque1 + plaque2;
				break;
				
			case "-":
				result = plaque1 - plaque2;
				break;
				
			case "/":
				result = plaque1 / plaque2;
				break;
				
			case "x":
				result = plaque1 * plaque2;
				break;
	
			default: break;
		}
		
		if (result < 0) {
			return false;
		}else {
			//Modification des plaques
			changePlaque(plaque1, plaque2, result);
			return true;
		}
		
	}
	
	/**
	 * Change les plaques utiliser	
	 * @param plaque1 Première plaque utiliser 
	 * @param plaque2 Seconde plaque utiliser
	 * @param result resultat à mettre dans une des deux plaque
	 */
	private void changePlaque(int plaque1, int plaque2, int result) {
		for (int i = 0; i < tabPlaques.length; i++) {
			if (tabPlaques[i] == plaque1) {
				tabPlaques[i] = result;
			}
			if (tabPlaques[i] == plaque1) {
				tabPlaques[i] = -1;
			}
		}
	}
	public int[] getTabPlaques() {
		return tabPlaques;
	}

	public void setTabPlaques(int[] tabPlaques) {
		this.tabPlaques = tabPlaques;
	}

	public GereScore getGestionDesScores() {
		return gestionDesScores;
	}

	public void setGestionDesScores(GereScore gestionDesScores) {
		this.gestionDesScores = gestionDesScores;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	
	
}
