/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;


import SymbolsTable.TypeDescription;

/**
 *
 * @author Jaime
 */
public class SymbolDecls extends SymbolBase {
    
    public TypeDescription typeDescription;

    public String idVariable;
    
    public SymbolDecls() {
        super("DECLS", 0);
    }
    
    public SymbolDecls(TypeDescription typeDescription) {
        super("DECLS", 0);
        this.typeDescription = typeDescription;
    }
    
}
