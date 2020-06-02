package exceptions;

import java.io.Serializable;

/**
 * Class d'exception sur les op�ration
 * @author Bastien
 */
public class OperationException extends Exception implements Serializable {

	/** SerialVersionUID  */
	private static final long serialVersionUID = 1L;
	
	/** Message par d�fault */
	private final static String MESSAGE_DEFAULT = "Probl�me dans l'op�ration des modes de jeu";
	
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
