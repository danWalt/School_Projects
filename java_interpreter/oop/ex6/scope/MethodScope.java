package oop.ex6.scope;

import oop.ex6.main.WarningException;
import oop.ex6.variable.Variable;
import java.util.LinkedList;

/**
 * a class that represents a Method in the file, each object holds all information needed in a class.
 */

public class MethodScope extends Scope {

    /*a list holding the used parameters when the function is called.*/
    private LinkedList<Variable> argumentsList;

    /*method's name*/
    private String methodName;

    /**
     * a constructor for the methodScope object.
     *
     * @param fatherScope   method father scope, used to search initializing of parameters outside of this scope
     * @param argumentsList parameters needed when calling this function
     * @param methodName    method's name
     * @throws WarningException if a parameter is missing 1 will be printed
     */
    public MethodScope(Scope fatherScope, LinkedList<Variable> argumentsList, String methodName) throws
            WarningException {
        super(fatherScope);
        this.argumentsList = argumentsList;
        this.methodName = methodName;
    }

    /**
     * @return method's name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return method's needed arguments
     */
    public LinkedList<Variable> getArgumentsList() {
        return argumentsList;
    }


}
