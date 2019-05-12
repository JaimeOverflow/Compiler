/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digraph;

import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class NodeGraph {
    
    public enum VARIABLES {
        ROOT,  
        INIT_TYPES,
        INIT_OP_ARITH,
        OP_ARITHMETIC,
        OP_ARITH_VALUE,
        SIGN,
        COND,
        COND_TRUE_MASK,
        COND_FALSE_MASK,
        COND_IF,
        COND_ELIF,
        COND_ELSE,
        SWITCH,
        SWITCH_BEGIN,
        SWITCH_END,
        WHILE,
        FOR,
        CODE_BLOCK_IN,
        CODE_BLOCK_OUT,
        INIT_OP_BOOL,
        OP_BOOLEAN,
        OP_BOOL_VALUE,
        OP_NEG,
        DECLS,
        VALUE,
        VALUE_CONST,
        ASSIGN,
        OUTPUT,
        FUNCTION,
        FUNC_HEAD,
        PARAMS,
        HAS_PARAMS,
        FUNC_BODY,
        RETURN,
        CALL,
        CALL_BODY,
        CONSTANT,
        RELATIONAL_COMP,
        INSTRS,
        INSTR
    }
    
    public ArrayList<NodeGraph> childs;
    public String description;
    public VARIABLES variable;
        
    public NodeGraph(String description, VARIABLES variable){
        this.description = description;
        this.variable = variable;
        this.childs = new ArrayList<NodeGraph>();
    }
    
    public NodeGraph(String description, VARIABLES variable, ArrayList<NodeGraph> childs){
        this.description = description;
        this.variable = variable;
        this.childs = childs;
    }
    
    @Override
    public String toString() {
        return "VARIABLE: " + this.variable + ", DESCRIPTION: " + this.description;
        
    }
    
}
