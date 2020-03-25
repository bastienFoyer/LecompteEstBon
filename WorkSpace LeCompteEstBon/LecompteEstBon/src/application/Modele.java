package application;

import java.awt.TextArea;
import java.awt.TextField;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Mod�le du jeu
 */
public class Modele {

	/** Controler du jeu */
	private Controler controler;
	
	/** Liste des differentes �tape du jeu */
	private LinkedList<Etape> listeEtape = new LinkedList<>();
	
	/** Dur�e de la partie */
	private int Duree;
	
	/** Nombre cible */
	private int cible;
	
	/** Pseudo */
	private String pseudo;
	
	public void init() {
		cible = ((int)Math.random()*900) + 100;
		pseudo = "";
		listeEtape = new LinkedList<>();
		Etape premi�reEtape = new Etape(pseudo);
		listeEtape.add(premi�reEtape);
	}

	public Controler getControler() {
		return controler;
	}

	public void setControler(Controler controler) {
		this.controler = controler;
	}

	public LinkedList<Etape> getListeEtape() {
		return listeEtape;
	}

	public void setListeEtape(LinkedList<Etape> listeEtape) {
		this.listeEtape = listeEtape;
	}

	public int getDuree() {
		return Duree;
	}

	public void setDuree(int duree) {
		Duree = duree;
	}

	public int getCible() {
		return cible;
	}

	public void setCible(int cible) {
		this.cible = cible;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
}
