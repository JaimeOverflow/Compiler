/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicacompiladores;

import Symbols.*;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;

/**
 *
 * @author Jaime
 */
public class Main {
    
    public static final String BASE_PATH = "src/practicacompiladores/";
    public static final String FOLDER_INPUT = BASE_PATH + "files/input/";
    public static final String FOLDER_OUTPUT = BASE_PATH + "files/output/";
    
    public static final String JFLEX_FLEX_FILENAME = "Lexer.flex";
    public static final String JFLEX_JAVA_FILENAME = "ScannerLex.java";
    public static final String JFLEX_PATH_TO_GENERATE_FILES = BASE_PATH + "files/flex/";
    public static final String JFLEX_NEW_PATH_FOR_FILES = BASE_PATH;
    
    public static final String CUP_FILENAME = "Syntactic.cup";
    public static final String CUP_JAVA_FILENAME = "Parser.java";
    public static final String CUP_SYM_FILENAME = "ParserSym.java";
    public static final String CUP_PATH_TO_GENERATE_FILES = BASE_PATH + "files/cup/";
    public static final String CUP_NEW_PATH_FOR_FILES = BASE_PATH;
    
    public static final int OPTION_GENERATE = 1;
    public static final int OPTION_EXECUTE = 2;
    public static final int OPTION_EXIT = 3;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int menuOption = -1;
        Scanner scanner = new java.util.Scanner(System.in);
        do {
            showMenuOptions();
            menuOption = scanner.nextInt();
            
            switch (menuOption) {
                case OPTION_GENERATE:
                    generateJFlexFile();
                    generateCupFiles();
                    break;
                case OPTION_EXECUTE:
                    executeAnalizer();
                    break;
                case OPTION_EXIT:
                    System.out.println("Nice to see you, come back soon ;)");
                    break;
                default:
                    System.out.println("Incorrect option. Try again!!");
            }
        } while (menuOption != OPTION_EXIT && menuOption != OPTION_GENERATE);
    }

    public static void showMenuOptions() {
        System.out.println("Choose an option:");
        System.out.println(OPTION_GENERATE + ") Generate jflex and cup files.");
        System.out.println(OPTION_EXECUTE + ") Execute.");
        System.out.println(OPTION_EXIT + ") Exit.");
        System.out.print("Option: ");
    }

    public static void generateJFlexFile() {
        String pathGenerateFlexFile = JFLEX_PATH_TO_GENERATE_FILES + JFLEX_FLEX_FILENAME;

        File fileGenerateFlexFile = new File(pathGenerateFlexFile);
        // Generate JFlex File
        jflex.Main.generate(fileGenerateFlexFile);
        
        // Once generated JFlex file, we have to move it to a new location
        moveFile(JFLEX_JAVA_FILENAME,
                JFLEX_NEW_PATH_FOR_FILES,
                JFLEX_PATH_TO_GENERATE_FILES
        );
    }
    
        public static void generateCupFiles() {
        String pathGenerateCupFile = CUP_PATH_TO_GENERATE_FILES + CUP_FILENAME;
      
        String[] fileSyntactic = {"-parser", "Syntactic", pathGenerateCupFile};

        try {
            java_cup.Main.main(fileSyntactic);
        
            moveFile(CUP_SYM_FILENAME, CUP_NEW_PATH_FOR_FILES, "");
            moveFile(CUP_JAVA_FILENAME, CUP_NEW_PATH_FOR_FILES, "");  
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void moveFile(String filename, String newPath, String oldPath) {
        // Remove old file
        File file = new File(newPath + filename);
        if (file.exists()) file.delete();
        
        File newFile = new File(oldPath + filename);
        if(newFile.exists()){
            newFile.renameTo(new File(newPath + filename));
            newFile.delete();
            System.out.println("Generated " + filename + " file\n");
        }else{
            System.out.println("Fail to generate " + filename + " file!!");
        }
        
    }
    
    public static void executeAnalizer() {
        // Remove all output files from other executions
        removeFilesFromDirectory(FOLDER_OUTPUT);
        
        String filenameSourceCode = chooseFilenameFromDirectory(FOLDER_INPUT);

        try {
            SymbolFactory sf = new ComplexSymbolFactory();
            ScannerLex scanner = new ScannerLex(new FileReader(filenameSourceCode));
            boolean hasToGenerateDigraph = false;
            Parser parser = new Parser(scanner, sf, hasToGenerateDigraph);

            SymbolRoot root = (SymbolRoot)parser.parse().value;

            if (root != null && root.hasFinished) {
                System.out.println("======================================================");
                System.out.println("CONGRATULATIONS!! Your source code is correct ;)");
                System.out.println("======================================================");
            }
            
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
        
    }
    public static void removeFilesFromDirectory(String pathDirectory) {
        // Remove all files from a directory
        File folder = new File(pathDirectory);
        for(File file: folder.listFiles()) {
            if (file.isFile()){ 
                file.delete();
            }
        }
    }

    public static String chooseFilenameFromDirectory(String pathDirectory) {
        File folder = new File(pathDirectory);
        File[] listOfFiles = folder.listFiles();
        Scanner reader = new Scanner(System.in);

        int indexOption = -1;
        
        do {
            System.out.println("\nList of source codes:");
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println(i + ": " + listOfFiles[i].getName()); 
                }
            }
            System.out.println("Choose one to execute:");
            try {
                indexOption = reader.nextInt();
            } catch (Exception ex) {
                System.out.println("\nIncorrect option. Try again!!");
                indexOption = -1;
            }
            
        } while (indexOption == -1 && indexOption < listOfFiles.length);
        return (pathDirectory + listOfFiles[indexOption].getName());
    }
}
