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
public class Operator {
    
    public enum TypeOperator {
        label,
        variable,
        procedure,
        numParam,
        string_value,
        int_value,
        bool_value   
    }
    
    public String value;
    public TypeOperator typeOperator;
    
    public Operator(String value, TypeOperator typeOperator) {
        this.value = value;
        this.typeOperator = typeOperator; 
    }
    
    public boolean compare(Operator operator) {
        return this.value.equals(operator.value) 
                && typeOperator == operator.typeOperator;
    }
    
    @Override
    public String toString(){
        return "[OPERATOR. "
                + "Type operator: " + this.typeOperator 
                + ", value: " + this.value 
                + "]";
    }
    
}
