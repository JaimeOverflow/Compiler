/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

import SymbolsTable.TypeDescription;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class SymbolParams extends SymbolBase{
    
    public ArrayList<String> listVariableIds = new ArrayList<String>();
    
    public ArrayList<TypeDescription> listVariableTypeDescription = new ArrayList<TypeDescription>();
    
    public SymbolParams(ArrayList<String> listVariableIds, ArrayList<TypeDescription> listVariableTypeDescription) {
        super("SymbolParams", 0);
        this.listVariableIds = listVariableIds;
        this.listVariableTypeDescription = listVariableTypeDescription;
    }
    
    public SymbolParams() {
        super("SymbolParams", 0);
    }
    
}