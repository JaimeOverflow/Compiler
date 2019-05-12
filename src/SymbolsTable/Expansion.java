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
public class Expansion {
    public TypeDescription typeDescription;
    public String id;
    public int scopeLevel;
    public int next;
    public String idc;
    
    public Expansion() {}
    
    @Override
    public String toString() {
        
        String result = "Expansion Item, ID: " + this.id 
                + ", scope level: " + this.scopeLevel
                + ", next: " + this.next
                + ", idc: " + this.idc
                + ", Type description: " + typeDescription;
        
        return result;
    }
}
