package filesprocessing.section;

/**
 * This class represent a Warning Exception. This class extends Exception class.
 */
public class WarningException extends Exception {
    /* the message that will be printed when has a warning.*/
    private String warningMessage;

    /**
     * constructor for warning exception, creates a new warning exception.
     *
     * @param line the line that the error was on.
     */
    public WarningException(int line) {
        this.warningMessage = "Warning in line " + line;
    }

    /**
     * @return the message that should be printed when a warning exception happens
     */
    public String getWarningMessage() {
        return warningMessage;
    }
}
