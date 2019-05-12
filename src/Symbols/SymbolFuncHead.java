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
public class SymbolFuncHead extends SymbolBase{
    
    public String nameType;
    
    public SymbolFuncHead(String nameType) {
        super("SymbolFuncHead", 0);
        this.nameType = nameType;
    }
    
}
