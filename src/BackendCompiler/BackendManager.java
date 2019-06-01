/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import BackendCompiler.Quadruple.OpCode;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class BackendManager {
    private static final String FILENAME_ASSEMBLER_CODE = "assemblerCode";
    public TablesManager tablesManager;
    private AssemblerConverter assemblerConverter;
    private ArrayList<Quadruple> c3dList;
    
    public BackendManager() {
        tablesManager = new TablesManager();
        c3dList = new ArrayList<Quadruple>();
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
    
    public void generateAssemblerCode() {
        for (int i = 0; i < this.c3dList.size(); i++) {
            System.out.println(this.c3dList.get(i));
        }
        
        tablesManager.printTables();
        assemblerConverter = new AssemblerConverter(FILENAME_ASSEMBLER_CODE, c3dList, tablesManager);
        assemblerConverter.generateAssemblerCode();
    }
    
    
}
