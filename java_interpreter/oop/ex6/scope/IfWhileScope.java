package oop.ex6.scope;

import oop.ex6.main.WarningException;


/**
 * a class for and if statement or a while loop statement
 */
public class IfWhileScope extends Scope {


    /**
     * A constructor for an if statement or a while loop, keeps current scope as a father scope and the
     * given condition
     *
     * @param fatherScope scope from which this if statement or while loop were called.
     * @throws WarningException will print 1 if there is a problem with father scope or the condition given
     */
    public IfWhileScope(Scope fatherScope) throws WarningException {
        super(fatherScope);
    }

}
