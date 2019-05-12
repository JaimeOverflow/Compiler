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
    
    public static enum OpCode {
        assignVal, assignRef,
        sum, sub, mult, div, mod,
        output,
        skip,
        jump,
        and, or,
        condTrue, condFalse
    }
    
    private OpCode opCode;
    private Operator source1, source2, destination;
    
    public Quadruple(OpCode opCode, Operator source1, Operator source2, Operator destination){
        this.opCode = opCode;
        this.source1 = source1;
        this.source2 = source2;
        this.destination = destination;
    }
    
    @Override
    public String toString() {
        String result = "[" + this.opCode + ", ";
        
        if (this.source1 != null) result += this.source1 + ", ";
        if (this.source2 != null) result += this.source2 + ", ";
        if (this.destination != null) result += this.destination;
        
        result += "]";
        return result;
        
    }
    
}
