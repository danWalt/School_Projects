package oop.ex6.variable;

/**
 * a boolean type variable class
 */
public class BooleanVar extends Variable {

    /**
     * a constructor of a boolean type variable
     *
     * @param varType       indicates it's a boolean type for further use
     * @param varName       name of variable
     * @param isFinal       a boolean value, is this a final variable or not
     * @param isInitialized was the variable given a value or not
     */
    public BooleanVar(String varType, String varName, boolean isFinal, boolean isInitialized) {
        super(varType, varName, isFinal, isInitialized);
    }
}
