/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import BackendCompiler.Quadruple.OpCode;
import Exceptions.AssignationSizeOverflowException;
import Utils.FilesManager;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class BackendManager {
    private static final String FILENAME_ASSEMBLER_CODE_NOT_OPTIMIZED = "Assembler_code_not_optimized";
    private static final String FILENAME_ASSEMBLER_CODE_OPTIMIZED = "Assembler_code_optimized";
    private static final String FILENAME_TABLES_MANAGER = "Tables_backend.txt";
    
    public TablesManager tablesManager;
    private AssemblerConverter assemblerConverter;
    private CodeOptimizer codeOptimizer;
    private ArrayList<Quadruple> c3dList;
    private FilesManager filesManager;
    
    public BackendManager() {
        this.tablesManager = new TablesManager();
        this.c3dList = new ArrayList<Quadruple>();
        this.codeOptimizer = new CodeOptimizer();
        this.filesManager = new FilesManager();
    }
    
    public void generateC3DInst(OpCode opCode, Operator source1, Operator source2, Operator destination) {
        Quadruple quadruple = new Quadruple(opCode, source1, source2, destination);
        c3dList.add(quadruple);        
    }
    
    public void generateC3DInst(int index, OpCode opCode, Operator source1, Operator source2, Operator destination) {
        Quadruple quadruple = new Quadruple(opCode, source1, source2, destination);
        c3dList.add(index, quadruple);     
    }
    
    public int getSizeOfC3DList() {
        return this.c3dList.size();
    }
    
    private void generateAssemblerCode(String filename) throws AssignationSizeOverflowException {
        assemblerConverter = new AssemblerConverter(filename, c3dList, tablesManager);
        assemblerConverter.generateAssemblerCode();
    }
    
    public void generateAssemblerCodeWithoutOptimization() throws AssignationSizeOverflowException {
        this.generateAssemblerCode(FILENAME_ASSEMBLER_CODE_NOT_OPTIMIZED);
    }
    
    public void generateAssemblerCodeOptimized() throws AssignationSizeOverflowException {
        this.codeOptimizer.setC3DList(this.c3dList);
        this.c3dList = this.codeOptimizer.getC3DOptimized();
        this.generateAssemblerCode(FILENAME_ASSEMBLER_CODE_OPTIMIZED);
    }
    
    public void storeTablesInALogFile() {
        this.tablesManager.storeTablesInLogFile(FILENAME_TABLES_MANAGER);
    }
    
    public void storeC3DInstInALogFile(String filename) {
        String result = 
                "==========================================\n"+
                " Three directions code instruction list\n"+
                "==========================================\n";
        
        for (int i = 0; i < this.c3dList.size(); i++) {
            result += this.c3dList.get(i)+"\n\n";
        }
        
        this.filesManager.writeFile(filename, result);
        
    }
    
    
}
