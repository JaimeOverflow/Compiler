/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntermediateCode;

/**
 *
 * @author Jaime
 */
public class Quadruple {
    
    public static enum OP_CODE {
        assignVal, assignRef,
        sum, sub, mult, div, mod,
        output,
        skip,
        jump,
        and, or,
        condTrue, condFalse
    }
    
    private OP_CODE opCode;
    private String operator1, operator2, operator3;
    
    public Quadruple(OP_CODE opCode, String operator1, String operator2, String operator3){
        this.opCode = opCode;
        this.operator1 = operator1;
        this.operator2 = operator2;
        this.operator3 = operator3;
    }
    
    public String toString() {
        return "["
                + this.opCode + " | "
                + this.operator1 + " | "
                + this.operator2 + " | "
                + this.operator3
                + "]";

    }
    
    
    public static void main(String args[]) {
        
        boolean [] a = new boolean[4];
        int b = -1;
        if(a[b] || true) {
            System.out.println("ALEX GAY");
        }
        
    }
    
}
