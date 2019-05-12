/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import SymbolsTable.TypeDescription;
import SymbolsTable.TypeDescription.BASIC_SUBJACENT_TYPE;

/**
 *
 * @author Jaime
 */
public class ProcedureBackend {
    
    public String name;
    public int depth;
    public String initalLabel;
    public int numParams;
    public int sizeLocalVariables;
    public int sizeTemporalArgs;
    public int size;
    public BASIC_SUBJACENT_TYPE basicSubjacentType;

    public ProcedureBackend(String name, int depth, String initalLabel, int numParams, int sizeLocalVariables, int sizeTemporalArgs, int size, BASIC_SUBJACENT_TYPE basicSubjacentType) {
        this.name = name;
        this.depth = depth;
        this.initalLabel = initalLabel;
        this.numParams = numParams;
        this.sizeLocalVariables = sizeLocalVariables;
        this.sizeTemporalArgs = sizeTemporalArgs;
        this.size = size;
        this.basicSubjacentType = basicSubjacentType;
    }

    @Override
    public String toString() {
        return "ProcedureBackend{" 
                + "name=" + name 
                + ", depth=" + depth 
                + ", initalLabel=" + initalLabel 
                + ", numParams=" + numParams 
                + ", sizeLocalVariables=" + sizeLocalVariables 
                + ", sizeTemporalArgs=" + sizeTemporalArgs 
                + ", size=" + size 
                + ", basicSubjacentType=" + basicSubjacentType 
                + '}';
    }
    
    
   
    
}