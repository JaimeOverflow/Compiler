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
public class SymbolOpBoolean extends SymbolBase{
    
    public boolean isConstant;
    public Object valueType;
    
    public String idVariable;
    public String idLabel;
    public int indexQuadruples;
    public boolean isSingleBoolean;
    
    public SymbolOpBoolean() {
        super("SymbolOpBoolean", 0);
        this.isConstant = false;
    }
    
    public SymbolOpBoolean(Object valueType) {
        super("SymbolOpBoolean", 0);
        this.isConstant = true;
        this.valueType = valueType;
    }
}