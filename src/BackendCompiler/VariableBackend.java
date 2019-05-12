/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import SymbolsTable.TypeDescription.BASIC_SUBJACENT_TYPE;

/**
 *
 * @author Jaime
 */
public class VariableBackend {
    public String name;
    public int idProcedure;
    public int size;
    public int offset;
    public BASIC_SUBJACENT_TYPE basicSubjacentType;
    
    
    public VariableBackend(String name, int idProcedure, int size, int offset, BASIC_SUBJACENT_TYPE basicSubjacentType){
        this.name = name;
        this.idProcedure = idProcedure;
        this.size = size;
        this.offset = offset;
        this.basicSubjacentType = basicSubjacentType;
    }

    @Override
    public String toString() {
        return "VariableBackend{" 
                + "name=" + name 
                + ", idProcedure=" + idProcedure 
                + ", size=" + size 
                + ", offset=" + offset 
                + ", basicSubjacentType=" + basicSubjacentType 
                + '}';
    }
    

   
}
