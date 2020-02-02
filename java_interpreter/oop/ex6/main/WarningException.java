package oop.ex6.main;

/**
 * a warning clas that is thrown whenever there is a problem with the given file such that the code won't
 * compile
 */
public class WarningException extends Exception {

    /* the message that will be printed when has a warning.*/
    private String warningMessage;

    /**
     * constructor for warning exception, creates a new warning exception.
     * @param warningMessage message to be printed
     */
    public WarningException(String warningMessage) {
        super(warningMessage);
    }

    /**
     * @return the message that should be printed when a warning exception happens
     */
    public String getWarningMessage() {
        return warningMessage;
    }

}
