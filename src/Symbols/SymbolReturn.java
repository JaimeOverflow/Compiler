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
    
    public BASIC_SUBJACENT_TYPE basicSubjacentType;
    
    public SymbolReturn(BASIC_SUBJACENT_TYPE basicSubjacentType) {
        super("SymbolReturn", 0);
        this.basicSubjacentType = basicSubjacentType;
    }
    
    public SymbolReturn() {
        super("SymbolReturn", 0);
    }
    
}
