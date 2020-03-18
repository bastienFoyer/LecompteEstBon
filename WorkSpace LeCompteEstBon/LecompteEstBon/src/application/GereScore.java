package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;


/**
 * Gestion des scores
 */
public class GereScore {

	/** Liste des 10 meilleurs scores */
	private LinkedList<Score> scoreList ;

	public GereScore(LinkedList<Score> scoreList) {
		scoreList = new LinkedList<Score>();
	}
	
	/**
	 * Ajoute un score à la liste
	 * @param pseudo nouveau pseudo du score
	 * @param valeur nouvelle valeur
	 * @param temps nouveau temps
	 */
	public void ajouteScore(String pseudo, int valeur, int temps) {
		Collections.sort(scoreList);
		Score score = new Score(pseudo, valeur, temps);
	
		if (scoreList.size() < 10) {
			scoreList.add(score);
		}else {
			if (scoreList.get(0).compareTo(score) == -1 ) {
				scoreList.set(0, score);
			}
		}
	}
	
	/**
	 * Affiche le contenue de la liste
	 */
	public void affiche() {
		for (Score score : scoreList) {
			System.out.println(score);
		}
	}

	/**
	 * Recupere dans un fichier binaire les données
	 */
	public void readObject() {
		Path paths = Paths.get(System.getProperty("user.dir"),"SaveFile.bin"); 
		try {
			
			ObjectInputStream obs = new ObjectInputStream(Files.newInputStream(paths));
			scoreList = (LinkedList<Score>) obs.readObject();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Enregistre dans un fichier binaire les données
	 */
	public void saveObject() {
		try {
			
			ObjectOutputStream obs = new ObjectOutputStream(Files.newOutputStream(Paths.get(System.getProperty("user.dir"),"SaveFile.bin" )));
			obs.writeObject(scoreList);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Export au format html
	 */
	public void export() {
		try {
			Path chemin = Paths.get(System.getProperty("user.dir"),"export.html");
			BufferedWriter buffer = new BufferedWriter(new FileWriter(chemin.toString()));
			for (Score score : scoreList) {
				buffer.write(score.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
