/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

import SymbolsTable.TypeDescription.BASIC_SUBJACENT_TYPE;

/**
 *
 * @author Jaime
 */
public class SymbolReturn extends SymbolBase{
    
    public String idVariable;
    public BASIC_SUBJACENT_TYPE basicSubjacentType;
    public String idBackend;
    public SymbolReturn(String idVariable, BASIC_SUBJACENT_TYPE basicSubjacentType, String idBackend) {
        super("SymbolReturn", 0);
        this.idVariable = idVariable;
        this.basicSubjacentType = basicSubjacentType;
        this.idBackend = idBackend;
    }
    
    public SymbolReturn() {
        super("SymbolReturn", 0);
    }
    
}
