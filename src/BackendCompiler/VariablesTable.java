/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import SymbolsTable.Description;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Jaime
 */
public class VariablesTable {
    
    private HashMap<String, String> variables;
    
    public VariablesTable(){
        variables = new HashMap<String, String>();
    }
    
    public String generateVariable() {
        String uuid = UUID.randomUUID().toString();
        variables.put(uuid, "");
        
        return uuid;
    }
    
    public void addVariable(String id) {
        variables.put(id, "");
    }
    
        
    public void showInfo() {
        for(String key: this.variables.keySet()) {
            System.out.println("[" + key + "]");
        }
    }
    
}