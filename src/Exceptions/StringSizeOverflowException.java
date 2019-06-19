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
public class StringSizeOverflowException extends Exception{
        public static final String FILENAME = "Compilation_error.txt";
    
    public StringSizeOverflowException (String msg) {
        super(
                "Exception: Compilation error.\n"
                + "Name: StringSizeOverflowException\n"
                + "Message: " + msg + "."
        );
        
        String msgError = "Exception: Compilation error.\n"
                        + "Name: StringSizeOverflowException\n"
                        + "Message: " + msg + ".";
        
        FilesManager fileManager = new FilesManager();
        fileManager.writeFile(FILENAME, msgError);
    }
}
