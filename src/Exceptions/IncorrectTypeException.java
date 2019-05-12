/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Jaime
 */
public class IncorrectTypeException extends CompilerException{
    
    public IncorrectTypeException (CompilerErrorType compilerErrorType, String msg) {
        super(
                compilerErrorType, 
                "Exception: IncorrectTypeException.\nException message: " + msg
        );
    }
    
    public IncorrectTypeException (CompilerErrorType compilerErrorType, String method, String msg) {
        super(
                compilerErrorType, 
                "Exception: IncorrectTypeException.\nMethod: " + method + ".\nException message: " + msg
        );
    }
    

    
}
