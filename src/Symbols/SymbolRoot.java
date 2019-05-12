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
public class SymbolRoot extends SymbolBase {
    
    public Boolean hasFinished;
    
    public SymbolRoot(boolean hasFinished) {
        super("Root", 0);
        this.hasFinished = hasFinished;
    }
    
    @Override
    public String toString() {
        return "Arrived to root!!";
    }
    
    
    
    
}
