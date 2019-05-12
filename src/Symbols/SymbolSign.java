/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

/**
 *
 * @author Jaime
 */
public class SymbolSign extends SymbolBase {
    
    public boolean isNegative;
    public boolean hasSign;
    
        public SymbolSign() {
        super("SymbolSign", 0);
        this.hasSign = false;
    }
    
    public SymbolSign(boolean isNegative) {
        super("SymbolSign", 0);
        this.hasSign = true;
        this.isNegative = isNegative;
    }
    
}
