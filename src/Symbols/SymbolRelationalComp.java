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
public class SymbolRelationalComp extends SymbolBase{
    
    public boolean isConstant;
    public Object valueType;
    
    public SymbolRelationalComp() {
        super("SymbolRelationalComp", 0);
        this.isConstant = false;
    }
    
    public SymbolRelationalComp(Object valueType) {
        super("SymbolRelationalComp", 0);
        this.isConstant = true;
        this.valueType = valueType;
    }
}
    