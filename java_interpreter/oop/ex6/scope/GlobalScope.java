package oop.ex6.scope;

import oop.ex6.main.WarningException;
import oop.ex6.variable.Variable;

import java.util.LinkedList;

/**
 * a constructor for the global scopes, all other scopes are under this scope
 */
public class GlobalScope extends Scope {

    /*a list that holds all methods created in a given file*/
    private LinkedList<MethodScope> methodList;

    /**
     * a constructor for the global scope
     *
     * @param fatherScope a global scope father scope is null
     */
    public GlobalScope(Scope fatherScope) {
        super(fatherScope);
        methodList = new LinkedList<MethodScope>();
    }

    /**
     * adds new methods to the method list
     *
     * @param method the current method scope that should be added to the method list
     */
    public void setMethodList(MethodScope method) {
        methodList.add(method);
    }

    /**
     * @return returns a list that holds all methods under global scope
     */
    public LinkedList<MethodScope> getMethodList() {
        return methodList;
    }

    /**
     * define global variables
     *
     * @param newVar the new variable that needs to be added
     * @throws WarningException prints 1 if there is a problem with the way the variable was initialized
     */
    @Override
    public void addLocalVariables(Variable newVar) throws WarningException {
        super.addLocalVariables(newVar);
    }

    /**
     * @return a list containing all global variables
     */
    @Override
    public LinkedList<Variable> getLocalVariables() {
        return super.getLocalVariables();
    }
}
