package SymbolsTable;

import Exceptions.AlreadyExistException;
import Exceptions.CompilerException;
import Exceptions.DoesNotExistException;
import java.util.ArrayList;
import java.util.Hashtable;
import Exceptions.IncorrectTypeException;
import Exceptions.OutOfBoundsException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import practicacompiladores.Main;
import practicacompiladores.Parser;

/**
 *
 * @author Jaime
 */
public class SymbolsTable {
    
    // If we want to write each change of symbols table
    public static final boolean WRITE_CHANGES_IN_FILE = true;
    private BufferedWriter out;
    public static final String SYMBOLS_TABLE_OUTPUT_FILENAME = Main.FOLDER_OUTPUT + "Symbols_table.txt";

    public int currentScope;
    public ArrayList<Integer> scopeTable;
    public Hashtable<String, Description> descriptionTable;
    public ArrayList<Expansion> expansionTable;
    
    public SymbolsTable() {
        
        if (WRITE_CHANGES_IN_FILE) {
            try {
                out = new BufferedWriter(new FileWriter(SYMBOLS_TABLE_OUTPUT_FILENAME));
            } catch (IOException ex) {
                Logger.getLogger(SymbolsTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.currentScope = 0;
        this.scopeTable = new ArrayList();
        this.descriptionTable = new Hashtable<>();
        this.expansionTable = new ArrayList();
        
        this.scopeTable.add(this.currentScope, 0);
    }
    
    // Get type description from a variable using 'id' as a identifier.
    public TypeDescription query(String id) {
        try {
            TypeDescription td;
            Description desc = this.descriptionTable.get(id);
            
            if (desc != null) {
                td = desc.typeDescription;
                return td;
            } else {
                // Check if it's an arguments in expansion table
                for (int i = this.expansionTable.size() - 1; i >= 0; i--) {
                    if (id.equals(this.expansionTable.get(i).idc)) {
                        Expansion exp = this.expansionTable.get(i);
                        td = exp.typeDescription;
                        return td;
                    }
                }

                // If doesn't exist the Id in expansion table
                throw new DoesNotExistException(
                        CompilerException.CompilerErrorType.symbolsTable, 
                        "query",
                        "Error to get id: " + id
                );
            }
            
        } catch (DoesNotExistException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    // Reset all symbols table
    public void reset() {
        // Reset scope
        this.currentScope = 0;
        this.scopeTable.set(currentScope, 0);
        
        // Reset description table
        this.descriptionTable = new Hashtable<>();
        
        if (WRITE_CHANGES_IN_FILE) { 
            updateSymbolsTableFile("reset", "Reset symbols table");
        }
        
    }
    
    public void getinBlock() {
        // Increase our scope
        this.currentScope += 1;

        this.scopeTable.add(
                this.currentScope, 
                scopeTable.get(this.currentScope - 1)
        );
        
        if (WRITE_CHANGES_IN_FILE) { 
            updateSymbolsTableFile("getinBlock", "Get in a new block and therefore increase the scope");
        }
    }
    
    public void getoutBlock() {
        try{
            if (this.currentScope == 0) { 
                throw new OutOfBoundsException(
                    CompilerException.CompilerErrorType.symbolsTable,
                    "getoutBlock", 
                    "You can't get out from scope level '0'"
                );
            }

            int first = this.scopeTable.get(this.currentScope) - 1;
            this.currentScope -= 1;
            int last = this.scopeTable.get(this.currentScope);
            // translate variables from expansion table to description table
            while (first >= last) {

                // Complex type is array, parameteres, tuples, etc
                boolean isContentOfComplexType = this.expansionTable.get(first).scopeLevel == -1;
                if (!isContentOfComplexType) {
                    String id = this.expansionTable.get(first).id;
                    Description desc = new Description();
                    desc.id = id;
                    desc.scopeLevel = this.expansionTable.get(first).scopeLevel;
                    desc.typeDescription = this.expansionTable.get(first).typeDescription;
                    desc.fisrt = -1;

                    this.descriptionTable.put(id, desc);

                    // Remove the element in expansion table
                    this.expansionTable.remove(first);
                }
                
                first -= 1;
            }

            // Remove keys from description table that has a higher scope
            Iterator<String> iterate = this.descriptionTable.keySet().iterator();

            while  (iterate.hasNext()) {
                Description description = this.descriptionTable.get(iterate.next());

                if(description.scopeLevel > this.currentScope) {
                    iterate.remove();
                }
            }
            
            this.scopeTable.remove(this.scopeTable.size()-1);

            if (WRITE_CHANGES_IN_FILE) { 
                updateSymbolsTableFile("getoutBlock", "Get out of last block and update variables and scope");
            }
            
        } catch (OutOfBoundsException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void add(String id, TypeDescription td) {
        try {
            Description oldDesc = this.descriptionTable.get(id);

            if (oldDesc != null) {

                if(oldDesc.scopeLevel == this.currentScope) {
                    throw new OutOfBoundsException(CompilerException.CompilerErrorType.symbolsTable,
                            "add", 
                            "We can not add a new variable '" + id + "' because it already exists in actual scope"
                    );
                }

                int newVariableScope = this.scopeTable.get(this.currentScope);
                this.scopeTable.set(this.currentScope, newVariableScope + 1);

                Expansion exp = new Expansion();
                exp.id = oldDesc.id;
                exp.scopeLevel = oldDesc.scopeLevel;
                exp.typeDescription = oldDesc.typeDescription;

                this.expansionTable.add(newVariableScope, exp); 
            }

            Description desc = new Description();
            desc.id = id;
            desc.scopeLevel = this.currentScope;
            desc.typeDescription = td;

            this.descriptionTable.put(id, desc);
            
            if (WRITE_CHANGES_IN_FILE) { 
                updateSymbolsTableFile("add", "Add a new variable '" + id + "'");
            }
            
        } catch (OutOfBoundsException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addParam(String idVar, String idParam, TypeDescription td) {
        try {
            Description desc = this.descriptionTable.get(idVar);

            if (desc.typeDescription.contentDescription != TypeDescription.CONTENT_DESCRIPTION.dfunc){
                throw new IncorrectTypeException(
                        CompilerException.CompilerErrorType.symbolsTable,
                        "addParam",
                        "You have to use the correct type!!");
            }

            int idx_p = -1;
            int idx_e = desc.fisrt;

            while (idx_e != -1 && !this.expansionTable.get(idx_e).idc.equals(idParam)){
                idx_p = idx_e;
                idx_e = this.expansionTable.get(idx_e).next;
            }

            if (idx_e  != -1) {
                throw new AlreadyExistException(CompilerException.CompilerErrorType.symbolsTable,
                        "addParam",
                        "You id '" + idParam + "' is duplicated!!"
                );
            }
            
            int newVariableScope = this.scopeTable.get(this.currentScope);
            this.scopeTable.set(this.currentScope, newVariableScope + 1);

            Expansion exp = new Expansion();
            exp.id = idVar;
            exp.idc = idParam;
            exp.scopeLevel = -1;
            exp.typeDescription = td;
            exp.next = -1;
            
            this.expansionTable.add(newVariableScope, exp); 

            //this.descriptionTable.put(id, desc);

            if (idx_p == -1) {
                desc.fisrt = newVariableScope;
                this.descriptionTable.put(idVar, desc);
            } else {
                Expansion expPrevious = this.expansionTable.get(idx_p);
                expPrevious.next = newVariableScope;
                this.expansionTable.set(idx_p, expPrevious);
            }
            
            if (WRITE_CHANGES_IN_FILE) { 
                updateSymbolsTableFile("addParam", "Add a new parameter '" + idParam + "' from function  '" + idVar + "'");
            }

        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    public TypeDescription queryParam(String idVar, String idParam)  {
        try {
            Description desc = this.descriptionTable.get(idVar);

            if (desc.typeDescription.contentDescription != TypeDescription.CONTENT_DESCRIPTION.dfunc){
                throw new IncorrectTypeException(
                        CompilerException.CompilerErrorType.symbolsTable,
                        "queryParam",
                        "You variable isn't a function!!"
                );
            }

            int idx_e = desc.fisrt;

            while (idx_e != -1 && !this.expansionTable.get(idx_e).idc.equals(idParam)){
                idx_e = this.expansionTable.get(idx_e).next;
            }

            if (idx_e  == -1) {
                throw new DoesNotExistException(
                        CompilerException.CompilerErrorType.symbolsTable,
                        "queryParam",
                        "The parameter '" + idParam + "' doesn't exists!!"
                );
            }
            
            return this.expansionTable.get(idx_e).typeDescription;
            
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
        public TypeDescription queryParam(String idVar, int index)  {
        try {
            Description desc = this.descriptionTable.get(idVar);

            if (desc.typeDescription.contentDescription != TypeDescription.CONTENT_DESCRIPTION.dfunc){
                throw new IncorrectTypeException(
                        CompilerException.CompilerErrorType.symbolsTable,
                        "queryParam",
                        "You variable isn't a function!!"
                );
            }

            int idx_e = desc.fisrt;

            while (idx_e != -1 && index > 0){
                idx_e = this.expansionTable.get(idx_e).next;
                index--;
            }

            if (idx_e  == -1) {
                                throw new DoesNotExistException(
                        CompilerException.CompilerErrorType.symbolsTable,
                        "queryParam",
                        "The parameter with index '" + index + "' doesn't exists!!"
                );
            }
            
            return this.expansionTable.get(idx_e).typeDescription;
            
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getNumParameters(String idVar)  {
        try {
            int counter = 0;
            Description desc = this.descriptionTable.get(idVar);

            if (desc.typeDescription.contentDescription != TypeDescription.CONTENT_DESCRIPTION.dfunc){
                throw new IncorrectTypeException(
                        CompilerException.CompilerErrorType.symbolsTable,
                        "getNumParameters",
                        "You variable isn't a function!!"
                );
            }

            int idx_e = desc.fisrt;

            while (idx_e != -1 ){
                idx_e = this.expansionTable.get(idx_e).next;
                counter++;
            }
            
            return counter;
            
        } catch (IncorrectTypeException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    private void updateSymbolsTableFile(String method, String msg) {
        
        try {
            out.write("********************************************************\n");
            out.write("Method: " + method + ".\n");
            out.write("Info: " + msg + ".\n\r");
            writeSymbolsTableInFile();
            out.write("********************************************************\n\r");
        } catch (IOException ex) {
            Logger.getLogger(SymbolsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void writeSymbolsTableInFile() {
        
        try {
            out.write("===========================================\n");
            out.write("                <SymbolsTable>             \n");
            out.write("===========================================\n");
            out.write("DESCRIPTION TABLE\n\r");
        
        for(String key: this.descriptionTable.keySet()) {
            
            Description desc = this.descriptionTable.get(key);
            out.write(desc + "\n\r");
        }
        
        out.write("===========================================\n");
        out.write("ACTUAL SCOPE LEVEL: " + this.currentScope + "\n");
        out.write("Scope table\n");
        for (int i = 0; i < this.scopeTable.size(); i++) {
            out.write("scope: " + i + ", pointer: " + scopeTable.get(i) + "\n");
        }
        out.write("===========================================\n");
        out.write("EXPANSION TABLE\n\r");
        for (int i = 0; i < this.expansionTable.size(); i++) {
            out.write(this.expansionTable.get(i) + "\n\r");
        }
        out.write("===========================================\n");
        out.write("               </SymbolsTable>             \n");
        out.write("===========================================\n");

        } catch (IOException ex) {
            Logger.getLogger(SymbolsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeFile() {
        if (WRITE_CHANGES_IN_FILE) {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SymbolsTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    public void printTables() {
        System.out.println("===========================================");
        System.out.println("                <SymbolsTable>             ");
        System.out.println("===========================================");
        System.out.println("DESCRIPTION TABLE");
        
        for(String key: this.descriptionTable.keySet()) {
            
            Description desc = this.descriptionTable.get(key);
            System.out.println(desc);
        }
        
        System.out.println("===========================================");
        System.out.println("ACTUAL SCOPE LEVEL: " + this.currentScope);
        System.out.println("Scope table");
        for (int i = 0; i < this.scopeTable.size(); i++) {
            System.out.println("scope: " + i + ", pointer: " + scopeTable.get(i));
        }
        System.out.println("===========================================");
        System.out.println("EXPANSION TABLE");
        for (int i = 0; i < this.expansionTable.size(); i++) {
            System.out.println(this.expansionTable.get(i));
        }
        System.out.println("===========================================");
        System.out.println("               </SymbolsTable>             ");
        System.out.println("===========================================");
    }    
}
