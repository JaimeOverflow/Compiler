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
    
    public String nameFunc;
    public String nameType;
    
    public int numProcedure;
    
    public SymbolFuncHead(String nameFunc, String nameType, int numProcedure) {
        super("SymbolFuncHead", 0);
        this.nameFunc = nameFunc;
        this.nameType = nameType;
        this.numProcedure = numProcedure;
    }
    
}
