/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import Utils.FilesManager;

/**
 *
 * @author Jaime
 */
public class AssignationSizeOverflowException extends Exception {
   
    public static final String FILENAME = "Compilation_error.txt";
    
    public AssignationSizeOverflowException (String msg) {
        super(
                "Exception: Compilation error.\n"
                + "Message: " + msg + "."
        );
        
        String msgError = "Exception: Compilation error.\n"
                        + "Message: " + msg + ".";
        
        FilesManager fileManager = new FilesManager();
        fileManager.writeFile(FILENAME, msgError);
    }

}
