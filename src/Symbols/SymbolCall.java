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
public class SymbolCall extends SymbolBase{
    
    public String idFunction;
           
    public SymbolCall(String idFunction) {
        super("SymbolCall", 0);
        this.idFunction = idFunction;
    }
    
}