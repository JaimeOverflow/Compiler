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
public class Quadruple {
/*
        assignVal, assignRef,
        sum, sub, mult, div, mod,
        output,
        skip,
        jump,
        and, or,
        condTrue, condFalse
*/
    public static enum OpCode {
        // variables
        assign,
        
        // Procedure
        procedureName,
        procedurePreamble,
        procedureReturn,
        procedureEnd,
        procedureCall,
        procedureCallMain,
        
        // parameters
        procedureParam,
        
        // Standard Keyboard actions
        standardInput,
        standardOutput,
        
        // Arithmetic operators
        sum, sub, mult, div, mod,
        
        // Conditionals
        and, or,
        condTrue, condFalse,
        
        // jumps
        skip, jump
    }
    
    public OpCode opCode;
    public Operator source1, source2, destination;
    
    public Quadruple(OpCode opCode, Operator source1, Operator source2, Operator destination){
        this.opCode = opCode;
        this.source1 = source1;
        this.source2 = source2;
        this.destination = destination;
    }
    
    @Override
    public String toString() {
        String result = "[" + this.opCode + ", "
                + this.source1 + ", "
                + this.source2 + ", "
                + this.destination
                + "]";

        return result;
    }
    
}
