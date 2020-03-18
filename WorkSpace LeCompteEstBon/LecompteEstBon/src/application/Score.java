package application;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Score d'une personne
 */
public class Score implements Comparable<Score>{
	
	/** Pseudo d'une personne */
	private String pseudo;
	
	/** Valeur obtenue par une personne */
	private int valeur;
	
	/** Temps notée en seconde */
	private int temps;
	
	/** Date d'obtention du score */
	private String date;

	public Score(String pseudo, int valeur, int temps) {
		Date dateAJD = new Date();
		SimpleDateFormat formater =new SimpleDateFormat("dd/MM/YYYY");
		date = "( le " + formater.format(dateAJD) + ')';
		this.pseudo = pseudo;
		this.valeur = valeur;
		this.temps = temps;
	}
 
	/**
	 * Crée un affichage pour le temps
	 * @return une chaine de caractère de temps
	 */
	private String traitementTemps() {
		String seconde = String.valueOf(temps%60);
		String minute = String.valueOf(temps/60);
		return minute+":"+seconde;
	}
	
	@Override
	public int compareTo(Score o) {
		if (this.valeur > o.valeur) {
			return 1;
		}else if(this.valeur < o.valeur){
			return  -1;
		}else {
			if (this.temps > o.temps) {
				
			}else {
				return -1;
			}
		}
			return 0;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		
		return "" + pseudo + " = " + valeur + " en " + traitementTemps() + date ;
	}

	
	
}
