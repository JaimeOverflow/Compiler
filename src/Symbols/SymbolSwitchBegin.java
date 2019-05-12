/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

import SymbolsTable.TypeDescription;
import SymbolsTable.TypeDescription.BASIC_SUBJACENT_TYPE;

/**
 *
 * @author Jaime
 */
public class SymbolSwitchBegin extends SymbolBase {

        public BASIC_SUBJACENT_TYPE basicSubjacentType;
    
        public SymbolSwitchBegin(BASIC_SUBJACENT_TYPE basicSubjacentType) {
        super("SymbolSwitchBegin", 0);
        this.basicSubjacentType = basicSubjacentType;
    }
}

