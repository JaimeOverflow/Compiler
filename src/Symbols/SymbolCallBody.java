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
public class SymbolCallBody extends SymbolBase{
    
    public String idFunction;
    public int counter;
    public int idBackend;
           
    public SymbolCallBody(String idFunction, int counter, int idBackend) {
        super("SymbolCallBody", 0);
        this.idFunction = idFunction;
        this.counter = counter;
        this.idBackend = idBackend;
    }
    
}