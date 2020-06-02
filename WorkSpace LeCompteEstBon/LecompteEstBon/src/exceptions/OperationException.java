package exceptions;

import java.io.Serializable;

/**
 * Class d'exception sur les opération
 * @author Bastien
 */
public class OperationException extends Exception implements Serializable {

	/** SerialVersionUID  */
	private static final long serialVersionUID = 1L;
	
	/** Message par défault */
	private final static String MESSAGE_DEFAULT = "Problème dans l'opération des modes de jeu";
	
	/** Message d'exception */
	private String message;

	/**
	 * Constructor class
	 * @param pMessage message consol
	 */
	public OperationException(String pMessage) {
		super(pMessage);
		message = pMessage;
	}
	
	/**
	 * Default Constructor
	 */
	public OperationException() {
		super(MESSAGE_DEFAULT);
		message = MESSAGE_DEFAULT;
	}
	
}
