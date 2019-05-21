/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import BackendCompiler.Quadruple.OpCode;
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
        String assemblerCode = getAssemblerHeadboard();
        
        for(int i = 0; i < this.c3dList.size(); i++) {
            Quadruple c3dInstruction = this.c3dList.get(i);
            OpCode opCodeInst = c3dInstruction.opCode;
            
            switch (opCodeInst) {
                case procedureName:
                    assemblerCode += getProcedureInitialLabel(c3dInstruction);
                    break;
                case procedurePreamble:
                    assemblerCode += getProcedurePreamble(c3dInstruction);
                    break;

            }
        }
        
        assemblerCode += "    SIMHALT\n";
        filesManager.writeFile(filename, assemblerCode);
    }
    
    private String getAssemblerHeadboard() {
        String result = "*-----------------------------------------------------------\n" +
                        "* Title      : Practice compilers II.\n" +
                        "* Written by : Andrés Ramos Seguí, Alex Mateo Fiol, Jaime Crespí Valero.\n" +
                        "* Date       : 17/06/2019\n" +
                        "* Description: Assembler code for compilers II practice.\n" +
                        "*-----------------------------------------------------------\n"+
                        "\n* ------------------------ INCLUDES ---------------------- *\n"+
                        "INCLUDE \"MACROS.X68\"\n"+
                        "* -------------------------------------------------------- *\n"+
                        "\n* ----------------------- MAIN PROGRAM -------------------- *\n"+
                        "    ORG    $1000\n";
        return result;
    }
    
    private String getProcedureInitialLabel(Quadruple c3dInstruction){
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        String result = "";
        result += "*\n INITIAL LABEL (PROCEDURE) *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n"+
                this.tablesManager.getProcedure(idProcedure).initialLabel+":\n";
        
        return result;
    }

    private String getProcedurePreamble(Quadruple c3dInstruction){
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        ProcedureBackend procedure = this.tablesManager.getProcedure(idProcedure);
        String result = "";
        result += "*\n PREAMBLE (PROCEDURE) *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n"+
                "    SUB.L #"+Math.abs(procedure.sizeTemporalArgs)+", A7\n"+
                "    SUB.L #4, A7  ;BP\n"+
                "    MOVE.L #0, (A7) ;Todo 0\n"+
                "    MOVE.L A7, A6 ;SP==A7\n"+
                "    SUB.L #"+Math.abs(procedure.sizeLocalVariables)+", A6\n";
        
        
        return result;
    }

}
