/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolsTable;

import SymbolsTable.TypeDescription;

/**
 *
 * @author Jaime
 */
public class Description {
    
    public TypeDescription typeDescription;
    public String id;
    public int scopeLevel;
    // We only use first for parameters, arrays, tuples, etc
    public int fisrt = -1;
    
    public Description(){}
    
    @Override
    public String toString() {

        String result = "Description Item, ID: " + this.id 
                + ", scope level: " + this.scopeLevel
                + ", first: " + this.fisrt
                + ", Type description: " + typeDescription;
        return result;
    }
}
