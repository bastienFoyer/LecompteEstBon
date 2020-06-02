package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Score d'une partie
 * @author Bastien
 */
public class Score implements Comparable<Score>, Serializable{

	/** SerailVarsionUID */
	private static final long serialVersionUID = 1L;
	
	/** Pseudo du joueur */
	private String pseudo;
	
	/** Date du score */
	private String date;
	
	/** Valeur du score */
	private int valeur;
	
	/** Temps */
	private int temps;
	
	/**
	 * Constructor
	 * @param pseudo pseudo du joueur 
	 * @param valeur du score
	 * @param temps du score
	 */
	public Score(String pPseudo, int pTemps, int pValeur) {
		pseudo = pPseudo;
		temps = pTemps;
		valeur = pValeur;
		/* Date format : (DD/MM/YYYY) */
		date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public String getDate() {
		return date;
	}

	public int getValeur() {
		return valeur;
	}

	public int getTemps() {
		return temps;
	}
	
	/**
	 * ToString Override
	 */
	@Override
	public String toString(){
		int minute = temps/60; 
		int seconde = temps%60;
		//Temps format : Joueur name = valeur en (min:sec) date 
		return String.format("Joueur %s = %d en (%02d:%02d) %s \n", pseudo, valeur, minute,seconde,date);
	}

	/**
	 * Default HashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		result = prime * result + temps;
		result = prime * result + valeur;
		return result;
	}

	/**
	 * Default equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		if (temps != other.temps)
			return false;
		if (valeur != other.valeur)
			return false;
		return true;
	}

	@Override
	/**
	 * Compare this à o 
	 *  return -1 si o > this
	 *  return 0 si égaux
	 *  return 1 si this > o
	 *  @param o instance à comparer
	 *  @return 1, 0 , -1
	 */
	public int compareTo(Score o) {
		if (valeur < o.valeur ) 
			return -1 ;
	    else {
	    	if ( valeur == o.valeur) {
	    		if (temps < o.temps ) 
	    			return -1 ;
	    		else{
	    				return 1;
	    		}
	    	}
		    return 1 ;
	    }
	}
	
}
