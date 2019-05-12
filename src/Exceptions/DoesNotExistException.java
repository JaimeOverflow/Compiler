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
public class DoesNotExistException extends CompilerException {
    
    public DoesNotExistException (CompilerException.CompilerErrorType compilerErrorType, String msg) {
        super(
                compilerErrorType, 
                "Exception: DoesNotExistException.\nException message: " + msg + "."
        );
    }
    
    public DoesNotExistException (CompilerException.CompilerErrorType compilerErrorType, String method, String msg) {
        super(
                compilerErrorType, 
                "Exception: DoesNotExistException.\nMethod: " + method + ".\nException message: " + msg + "."
        );
    }
}
