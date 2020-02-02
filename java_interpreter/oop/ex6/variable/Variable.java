package oop.ex6.variable;

import oop.ex6.scope.Scope;

/**
 * an abstract class used to create different type variables
 */
public abstract class Variable {

    /*variable's name*/
    private String varName;

    /*was the variable declared final or not*/
    private boolean isFinal;

    /*variable type*/
    private String varType;

    /*was the variable given a value or not*/
    private boolean isInitialized;


    /**
     * a general constructor for a Variable
     *
     * @param varType       the type of the variable
     * @param varName       the name of the variable
     * @param isFinal       was the variable declared final
     * @param isInitialized does the variable holds a value
     */
    public Variable(String varType, String varName, boolean isFinal, boolean isInitialized) {
        this.varType = varType;
        this.varName = varName;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
    }

    /**
     * @return variable's name
     */
    public String getVarName() {
        return varName;
    }

    /**
     * @return variable's type
     */
    public String getVarType() {
        return varType;
    }

    /**
     * @return is the variable final or not
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * @return does the variable hold a value or not
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * updates variable to hold a value
     *
     * @param initialized true if the variable now holds a value in the current scope
     */
    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}

