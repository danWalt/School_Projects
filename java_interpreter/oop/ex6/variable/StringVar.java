package oop.ex6.variable;

/**
 * a string type variable class
 */
public class StringVar extends Variable {
    /**
     * a constructor of a String type variable
     *
     * @param varType       indicates it's a string type for further use
     * @param varName       name of variable
     * @param isFinal       a boolean value, is this a final variable or not
     * @param isInitialized was the variable given a value or not
     */
    public StringVar(String varType, String varName, boolean isFinal, boolean isInitialized) {
        super(varType, varName, isFinal, isInitialized);
    }
}
