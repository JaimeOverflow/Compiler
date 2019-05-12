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
public class SymbolOpArithValue extends SymbolBase{
    
    public boolean isConstant;
    public Object valueType;
    
    public String idVariable;
    
    public SymbolOpArithValue() {
        super("SymbolOpArithValue", 0);
        this.isConstant = false;
    }
    
    public SymbolOpArithValue(Object valueType) {
        super("SymbolOpArithValue", 0);
        this.isConstant = true;
        this.valueType = valueType;
    }
    
}
