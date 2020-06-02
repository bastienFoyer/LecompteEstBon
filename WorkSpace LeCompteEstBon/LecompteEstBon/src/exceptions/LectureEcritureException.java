package exceptions;

import java.io.File;

/**
 * Lecture / Ecriture de fichier Erroné
 * @author Bastien
 */
public class LectureEcritureException extends Exception{

	/** SerialVersionUID  */
	private static final long serialVersionUID = 1L;
	
	/** Message par défault */
	private final static String MESSAGE_DEFAULT = "Problème dans la lecture d'un fichier";
	
	/** Message d'exception */
	private String message;
	
	/** Fichier d'erreur */
	private File fichierErreur;

	/**
	 * Constructor class
	 * @param pMessage message consol
	 */
	public LectureEcritureException(String pMessage) {
		super(pMessage);
		message = pMessage;
	}

	/**
	 * Erreur dans la lecture/ecriture d'un fichier
	 * @param message
	 * @param fichierErreur
	 */
	public LectureEcritureException(String pMessage, File pFichierErreur) {
		super(pMessage + pFichierErreur.getAbsolutePath());
		message = pMessage;
		fichierErreur = pFichierErreur;
	}



	/**
	 * Default Constructor
	 */
	public LectureEcritureException() {
		super(MESSAGE_DEFAULT);
		message = MESSAGE_DEFAULT;
	}
}
