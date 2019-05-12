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
public class SymbolInitOpArith extends SymbolBase{
    
    public boolean isConstant;
    public Object valueType;
    
    public String idVariable;
    
    public SymbolInitOpArith() {
        super("SymbolInitOpArith", 0);
        this.isConstant = false;
    }
    
    public SymbolInitOpArith(Object valueType) {
        super("SymbolInitOpArith", 0);
        this.isConstant = true;
        this.valueType = valueType;
    }
    
}
