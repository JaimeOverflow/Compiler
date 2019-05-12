/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

/**
 *
 * @author Jaime
 */
public class LabelBackend {
    public String name;
    public int procedure;

    public LabelBackend(String name, int procedure) {
        this.name = name;
        this.procedure = procedure;
    }

    @Override
    public String toString() {
        return "LabelBackend{" 
                + "name=" + name 
                + ", procedure=" + procedure 
                + '}';
    }
    
    
}
