package oop.ex6.variable;

/**
 * a double type variable class
 */
public class DoubleVar extends Variable {

    /**
     * a constructor of a double type variable
     *
     * @param varType       indicates it's a double type for further use
     * @param varName       name of variable
     * @param isFinal       a boolean value, is this a final variable or not
     * @param isInitialized was the variable given a value or not
     */
    public DoubleVar(String varType, String varName, boolean isFinal, boolean isInitialized) {
        super(varType, varName, isFinal, isInitialized);
    }
}
