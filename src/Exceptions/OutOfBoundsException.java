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
public class OutOfBoundsException extends CompilerException {
    public OutOfBoundsException (CompilerException.CompilerErrorType compilerErrorType, String method, String msg) {
        super(
                compilerErrorType, 
                "Exception: OutOfBoundsException.\nMethod: " + method + ".\nException message: " + msg + "."
        );
    }
}
