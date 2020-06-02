package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import exceptions.LectureEcritureException;

/**
 * Gestion des scores d'une partie
 * @author Bastien
 */
public class GereScore {

	/** Chemin du fichier binaire */
	private static final String CHEMIN_BINAIRE = "scores.dat";

	/** Chemin du fichier HTML */
	private static final String CHEMIN_HTML = "scores.html";

	/** Listes des scores géré */
	private List<Score> listScore = new ArrayList<>();;

	/** Fichier de asuvegarde */
	private File fichierSauvegarde;

	/** fichier de sauvegarde HTML */
	private File fichierHTML;
	
	/**
	 * Constructor
	 */
	public GereScore() {
		try {
			File repertoire = new File(System.getProperty("user.dir"), "data\\");
			fichierSauvegarde = new File(repertoire, CHEMIN_BINAIRE);
			fichierHTML = new File(repertoire, CHEMIN_HTML);
			/* Lectures Fichier */
			readFile();
			
		} catch (Exception exceptionfichier) {
			System.out.println(exceptionfichier.getMessage());
		}
	}

	/**
	 * Ajoute un Score à la gestion des score
	 * @param pseudo pseudo du joueur
	 * @param valeur valeur du score
	 * @param duree durée du score 
	 */
	public void ajoutScore(String pseudo, int valeur, int duree) {
		try {
			Score score = new Score(pseudo, valeur, duree);
			listScore.add(score);
			
			/* Ecrit le score dans les fichier */
			writeFileHTML();
			writeFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Extract la liste des score sous le forma d'une chaine de caractere
	 * @return les liste des scores
	 */
	public String extractListeScores() {
		StringBuilder allScores = new StringBuilder();

		for (Score score : listScore) {
			allScores.append(score.toString() + "\n");
		}
		return allScores.toString();
	}

	/**
	 * Lecture Fichiers
	 * @throws Toute les Erreurs Possibles
	 */
	public void readFile() throws LectureEcritureException, IOException, ClassNotFoundException {
		try {
			if (!fichierSauvegarde.exists()) {

				throw new LectureEcritureException("Erreur Fichier non existant",fichierSauvegarde);

			} else {
				System.out.println("Chemin = " + fichierSauvegarde.getParent());
				System.out.println("Fichier = " + fichierSauvegarde.getName());

				//Lecture du fichier binaire
				ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(fichierSauvegarde.toPath()));
				listScore = (ArrayList<Score>) stream.readObject();
			}

		} catch (IOException | LectureEcritureException | ClassNotFoundException e) { 
			System.out.println(e.getMessage());
		}


	}

	/**
	 * Ecriture Fichiers
	 * @throws Toute les Erreurs Possibles
	 */
	public void writeFileHTML() throws LectureEcritureException {
		try {
			
			/* Si le fichier n'est pas trouvé : le crée */
			BufferedWriter bufferW = new BufferedWriter(new FileWriter (fichierHTML));
			bufferW.write("<HTML>\n");
			bufferW.write("<head>\n");
			bufferW.write("<title>Compte est bon : Scores :</title>\n");
			bufferW.write("</head>\n");
			bufferW.write("<body>\n");
			bufferW.write("<h2>Scores</h2>\n");
			
			for (Score score : listScore) {
				bufferW.write("<p><b>" + score.getPseudo() + " = " + score.getValeur() + "</b> en "
						+ " " + score.getTemps() / 60  + " : " +  score.getTemps() % 60 + " " + "(" + score.getDate()
						+ ")</p>\n");
			}
			bufferW.write("</body>\n");
			bufferW.write("</HTML>\n");
			bufferW.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			/* Information sur le fichier/Path */
			System.out.println("Chemin = " + fichierHTML.getParent());
			System.out.println("Fichier =" + fichierHTML.getName());
		}

	}

	/**
	 * Ecriture d'un fichierS
	 * @throws LectureEcritureException
	 */
	public void writeFile() throws LectureEcritureException {
		try {
			ObjectOutputStream br = new ObjectOutputStream(Files.newOutputStream(fichierSauvegarde.toPath()));
			br.writeObject(listScore);

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) { //
			System.out.println(e.getMessage());
		}


	}

	/**
	 * Affiche les scores
	 */
	public void displayScores() {
		System.out.println(listScore.toString());
	}

	public List<Score> getListScore() {
		return listScore;
	}

	public void setListScore(List<Score> listScore) {
		this.listScore = listScore;
	}
	
	
}
