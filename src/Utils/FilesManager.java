/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaime
 */
public class FilesManager {
    
    public static final String BASE_PATH = "src/practicacompiladores/";
    public static final String FOLDER_INPUT = BASE_PATH + "files/input/";
    public static final String FOLDER_OUTPUT = BASE_PATH + "files/output/";
    
    public FilesManager() {
    }
    
    public void writeFile(String filename, String content){
        String pathFile = FOLDER_OUTPUT + filename;
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile));
            writer.write(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FilesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]) {
        System.out.println("HOLA");
        FilesManager filesManager = new FilesManager();
        
        filesManager.writeFile("holis.txt", "aasdeau");
    }
    
    public void removeFilesFromDirectory(String pathDirectory) {
        // Remove all files from a directory
        File folder = new File(pathDirectory);
        for(File file: folder.listFiles()) {
            if (file.isFile()){ 
                file.delete();
            }
        }
    }
    
    public void moveFile(String filename, String newPath, String oldPath) {
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
    
    public String chooseFilenameFromDirectory(String pathDirectory) {
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
