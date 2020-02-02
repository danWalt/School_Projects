package oop.ex6.variable;

/**
 * a char type variable class
 */
public class CharVar extends Variable {

    /**
     * a constructor of a char type variable
     *
     * @param varType       indicates it's a char type for further use
     * @param varName       name of variable
     * @param isFinal       a boolean value, is this a final variable or not
     * @param isInitialized was the variable given a value or not
     */
    public CharVar(String varType, String varName, boolean isFinal, boolean isInitialized) {
        super(varType, varName, isFinal, isInitialized);
    }
}
