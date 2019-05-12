/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import practicacompiladores.Main;

/**
 *
 * @author Jaime
 */
public class CompilerException extends Exception {
    public enum CompilerErrorType { lexer, syntactic, semantic, symbolsTable };
    
    private CompilerErrorType compilerErrorType;
    private String messageError;
    
    public CompilerException(CompilerErrorType compilerErrorType, String msg) {
        super("Exception in " + compilerErrorType + " analizer with message: " + msg);
        this.compilerErrorType = compilerErrorType;
        this.messageError = msg;
        
        writeExceptionInFileError(this.compilerErrorType, this.messageError);
    }    
        
    private void writeExceptionInFileError(CompilerErrorType compilerErrorType, String messageError){
        
        String filenameError = Main.FOLDER_OUTPUT + "unkown_error.txt";
        
        switch(compilerErrorType) {
            case lexer: filenameError = Main.FOLDER_OUTPUT + "lexical_errors.txt"; break;
            case syntactic: filenameError = Main.FOLDER_OUTPUT + "sintactic_errors.txt"; break;
            case semantic: filenameError = Main.FOLDER_OUTPUT + "semantic_errors.txt"; break;
            case symbolsTable: filenameError = Main.FOLDER_OUTPUT + "symbolsTable_errors.txt"; break;

        }
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(filenameError));
        
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            out.write("========================= INFO =========================\n");
            out.write("DATE: " + formatter.format(date) + "\n");
            out.write("========================================================\n");
            out.write("======================== ERRORS ========================\n\r");
            out.write(messageError + "\n");
            out.write("========================================================");
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(CompilerException.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
    
    

