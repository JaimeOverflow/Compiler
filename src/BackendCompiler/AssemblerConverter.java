/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import Utils.FilesManager;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class AssemblerConverter {
    
    private TablesManager tablesManager;
    private ArrayList<Quadruple> c3dList;
    private String filename;
    private FilesManager filesManager;

    public AssemblerConverter(String filename, ArrayList<Quadruple> c3dList, TablesManager tablesManager) {
        if (!filename.contains(".X68")) {
            filename = filename.concat(".X68");
            System.out.println(filename);
        }
        
        this.filename = filename;
        this.c3dList = c3dList;
        this.tablesManager = tablesManager;
        this.filesManager = new FilesManager();
    }
    
    
    public void generateAssemblerCode() {
        String functionsCode = "";
        String assemblerCode = getAssemblerHeadboard();
        
        
        
        assemblerCode += functionsCode;
        filesManager.writeFile(filename, assemblerCode);
    }
    
    private String getAssemblerHeadboard() {
        String result = "*-----------------------------------------------------------\n" +
                        "* Title      : Practice compilers II.\n" +
                        "* Written by : Andrés Ramos Seguí, Alex Mateo Fiol, Jaime Crespí Valero.\n" +
                        "* Date       : 17/06/2019\n" +
                        "* Description: Assembler code.\n" +
                        "*-----------------------------------------------------------\n"+
                        "\n* ------------------------ INCLUDES ---------------------- *\n"+
                        "INCLUDE \"MACROS.X68\"\n"+
                        "* -------------------------------------------------------- *\n"+
                        "\n* ----------------------- MAIN PROGRAM -------------------- *\n"+
                        "    ORG    $1000\n";
        return result;
    }
    

    
    
    
    
    
}
