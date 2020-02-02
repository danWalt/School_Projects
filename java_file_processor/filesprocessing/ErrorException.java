package filesprocessing;

/**
 * This class represent an Error Exception. This class extends the Exception class.
 */
public class ErrorException extends Exception{

	/* The message that should be printed when there is an error exception. */
	private String errorMessage;

	/**
	 * constructor for error exception, creates a new error exception object.
	 * @param errorMessage the message that is printed when an error exception occurs.
	 */
	public ErrorException (String errorMessage){
		this.errorMessage = errorMessage;
	}

	/**
	 *
	 * @return the message that is printed when an error exception occurs.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
