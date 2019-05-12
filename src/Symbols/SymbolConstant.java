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
public class SymbolConstant extends SymbolBase{
    
    public boolean isConstant;
    
    public SymbolConstant(boolean isConstant) {
        super("SymbolConstant", 0);
        this.isConstant = isConstant;
    }
    
}