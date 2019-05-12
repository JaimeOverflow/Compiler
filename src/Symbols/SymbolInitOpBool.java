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
public class SymbolInitOpBool extends SymbolBase{
    
    public boolean isConstant;
    public Object valueType;
    
    public String idVariable;
    
    public SymbolInitOpBool() {
        super("SymbolInitOpBool", 0);
        this.isConstant = false;
    }
    
    public SymbolInitOpBool(Object valueType) {
        super("SymbolInitOpBool", 0);
        this.isConstant = true;
        this.valueType = valueType;
    }
    
}
