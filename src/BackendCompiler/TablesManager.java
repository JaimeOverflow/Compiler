/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import SymbolsTable.TypeDescription.BASIC_SUBJACENT_TYPE;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class TablesManager {
    
    private ArrayList<VariableBackend> variablesTable;
    private ArrayList<ProcedureBackend> proceduresTable;
    private ArrayList<LabelBackend> labelsTable;
    
    public TablesManager() {
        this.variablesTable = new ArrayList<VariableBackend>();
        this.proceduresTable = new ArrayList<ProcedureBackend>();
        this.labelsTable = new ArrayList<LabelBackend>();
    }
    
    public int addVariable(String name, int idProcedure, int size, int isArgument, BASIC_SUBJACENT_TYPE basicSubjacentType) {
        name = "#" + name;
        variablesTable.add(
                new VariableBackend(name, idProcedure, size, isArgument, basicSubjacentType)
        );
        int idVariable = variablesTable.size() - 1;

        return idVariable;
    }
    
    public int addTemporalVariable(int idProcedure, int size, int isArgument, BASIC_SUBJACENT_TYPE basicSubjacentType) {
        String name = "#temporalVariable";
        variablesTable.add(
                new VariableBackend(name, idProcedure, size, isArgument, basicSubjacentType)
        );
        int idVariable = variablesTable.size() - 1;

        return idVariable;
    }
    
    public int addProcedure(String name, int depth, int numParams, int size, BASIC_SUBJACENT_TYPE basicSubjacentType){
        String label = "PROCEDURE_" + name;
        
        proceduresTable.add(
                new ProcedureBackend("#" + name, depth, label, numParams, 0, size, basicSubjacentType)
        );
        
        return this.proceduresTable.size() - 1;
    }
    
    public int addLabel() {
        String label = "LABEL_" + this.labelsTable.size();
        this.labelsTable.add(
                new LabelBackend(label)
        );
        return this.labelsTable.size() - 1;
    }
    
    public void updateOffsetOfTables() {
        for (int i = 0; i < this.variablesTable.size(); i++) {
            int idProcedure = this.variablesTable.get(i).idProcedure;
            int sizeVariable = this.variablesTable.get(i).size;
            
            if (this.variablesTable.get(i).offset != -1) {
                this.proceduresTable.get(idProcedure).sizeLocalVariables -= sizeVariable;
                this.variablesTable.get(i).offset = this.proceduresTable.get(idProcedure).sizeLocalVariables;
            } else {
                //Argument
                this.proceduresTable.get(idProcedure).sizeParameters += sizeVariable;
                this.variablesTable.get(i).offset = this.proceduresTable.get(idProcedure).sizeParameters;
            }
        }
        // TODO: DELETE THIS
        //for (int i = 0; i < this.proceduresTable.size(); i++) {
            //this.proceduresTable.get(i).sizeLocalVariables = (this.proceduresTable.get(i).sizeParameters) - (this.proceduresTable.get(i).sizeLocalVariables);
        //}      
    }
    
    public void updateNumParametersInProcedure(int idProcedure, int numParameters) {
        this.proceduresTable.get(idProcedure).numParams = numParameters;
    }
    
    public int getActualProcedure() {
        return proceduresTable.size() - 1;
    }
    
    public VariableBackend getVariable(int idVariable) {
        return this.variablesTable.get(idVariable);
    }
    
    public ProcedureBackend getProcedure(int idProcedure) {
        return this.proceduresTable.get(idProcedure);
    }
    
    public LabelBackend getLabel(int idLabel) {
        return this.labelsTable.get(idLabel);
    }

    public void printTables(){
        System.out.println("=====================");
        System.out.println("TABLES MANAGER");
        System.out.println("=====================");
        System.out.println("Variables:");
        for (int i = 0; i < variablesTable.size(); i++) {
            System.out.println("ID: " + i + " => " + variablesTable.get(i));
        }

        System.out.println("\nProcedures:");
        for (int i = 0; i < proceduresTable.size(); i++) {
            System.out.println("ID: " + i + " => " + proceduresTable.get(i));
        }

        System.out.println("\nLabels:");
        for (int i = 0; i < labelsTable.size(); i++) {
            System.out.println("ID: " + i + " => " + labelsTable.get(i));
        }
    }
    
}
