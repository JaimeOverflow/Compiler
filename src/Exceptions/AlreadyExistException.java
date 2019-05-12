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
public class AlreadyExistException extends CompilerException {
    
    public AlreadyExistException (CompilerException.CompilerErrorType compilerErrorType, String method, String msg) {
        super(
                compilerErrorType, 
                "Exception: AlreadyExistException.\nMethod: " + method + ".\nException message: " + msg + "."
        );
    }
}
