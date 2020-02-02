package oop.ex6.scope;

import oop.ex6.main.WarningException;
import oop.ex6.variable.Variable;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * an abstract class for creating a scope
 */
public abstract class Scope {

    /*a list that holds all variables that were initialized in the current scope*/
    private LinkedList<Variable> localVariables;

    /*keeps track of current scope father scope*/
    private Scope fatherScope;

    /**
     * a constructor for a general scope
     *
     * @param fatherScope the scope we are currently in when the creation of this scopes occurs
     */
    public Scope(Scope fatherScope) {
        this.fatherScope = fatherScope;
        localVariables = new LinkedList<>();
    }

    /**
     * initializes a new variable in the new scope
     *
     * @param newVar the new variable that needs to be added
     * @throws WarningException prints 1 if a variable already exists
     */
    public void addLocalVariables(Variable newVar) throws WarningException {
        for (Variable variable : localVariables) {
            if (newVar.getVarName().equals(variable.getVarName()))
                throw new WarningException("this variable name already exist.");
        }
        localVariables.add(newVar);
    }

    /**
     * @return a list that holds all of current scope variables
     */
    public LinkedList<Variable> getLocalVariables() {
        return localVariables;
    }

    /**
     * @return current scope's father scope
     */
    public Scope getFatherScope() {
        return fatherScope;
    }

}
