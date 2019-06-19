/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import BackendCompiler.Operator.TypeOperator;
import BackendCompiler.Quadruple.OpCode;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class CodeOptimizer {
    
    private ArrayList<Quadruple> c3dList;
    
    public CodeOptimizer() {
        
    }
    
    public void setC3DList(ArrayList<Quadruple> c3dList) {
        this.c3dList = c3dList;
    }
    
    public ArrayList<Quadruple> getC3DOptimized() {
        
        for (int indexInst = 0; indexInst < this.c3dList.size(); indexInst++) {
            Quadruple c3dInstruction = this.c3dList.get(indexInst);
            
            switch(c3dInstruction.opCode) {
                case assign:
                    indexInst = optimizeAssignments(c3dInstruction, indexInst);
                    break;
                case sum:
                case sub:
                case mult:
                case div:
                    indexInst = optimizeArithmeticOperations(c3dInstruction, indexInst);
                    break;
                case and:
                case or:
                    indexInst = optimizeLogicalOperations(c3dInstruction, indexInst);
                    break;
                case jump:
                    optimizeConditionals(c3dInstruction, indexInst);
                    break;
            }
        }
        
        return this.c3dList;
    }
    
    private int optimizeAssignments(Quadruple c3dInst, int indexC3DInst) {
        boolean canRemoveActualC3DInst = false;

        int counterNextInst = 1;
        
        if (indexC3DInst + counterNextInst == this.c3dList.size()) return indexC3DInst;
        
        Quadruple nextC3DInst = this.c3dList.get(indexC3DInst + counterNextInst);
        
        while (nextC3DInst.opCode == OpCode.assign){

            if ((c3dInst.source1.typeOperator == TypeOperator.int_value
                || c3dInst.source1.typeOperator == TypeOperator.bool_value
                || c3dInst.source1.typeOperator == TypeOperator.string_value)
                && c3dInst.destination.value.equals(nextC3DInst.source1.value)) {
            
                nextC3DInst.source1.typeOperator = c3dInst.source1.typeOperator;
                nextC3DInst.source1.value = c3dInst.source1.value;
                canRemoveActualC3DInst = true;

            }
            
            counterNextInst += 1;
            if (indexC3DInst + counterNextInst == this.c3dList.size()) return indexC3DInst;
            nextC3DInst = this.c3dList.get(indexC3DInst + counterNextInst);
        }

        
        if (canRemoveActualC3DInst) {
            this.c3dList.remove(indexC3DInst);
            return indexC3DInst - 1;
        }
        return indexC3DInst;
    }
    
    private int optimizeArithmeticOperations(Quadruple c3dInst, int indexC3DInst) {
        
        Quadruple firstOperand = this.c3dList.get(indexC3DInst - 2);
        Quadruple secondOperand = this.c3dList.get(indexC3DInst - 1);

        int result = 0;
        
        if (firstOperand.opCode != OpCode.assign || firstOperand.source1.typeOperator != TypeOperator.int_value) return indexC3DInst;
        if (secondOperand.opCode != OpCode.assign || secondOperand.source1.typeOperator != TypeOperator.int_value) return indexC3DInst;
        
        int firstOperandValue = Integer.parseInt(firstOperand.source1.value);
        int secondOperandValue = Integer.parseInt(secondOperand.source1.value);
        
        switch(c3dInst.opCode) {
            case sum:
                result = firstOperandValue + secondOperandValue;
                break;
            case sub:
                result = firstOperandValue - secondOperandValue;
                break;
            case mult:
                result = firstOperandValue * secondOperandValue;
                break;
            case div:
                result = firstOperandValue / secondOperandValue;
                break;
        }
        
        c3dInst.opCode = OpCode.assign;
        c3dInst.source1.typeOperator = TypeOperator.int_value;
        c3dInst.source1.value = result + "";
        c3dInst.source2 = null;
        
        this.c3dList.remove(indexC3DInst - 2);
        this.c3dList.remove(indexC3DInst - 2);
        return indexC3DInst - 2;
    }
    
    private int optimizeLogicalOperations(Quadruple c3dInst, int indexC3DInst) {
        
        Quadruple firstOperand = this.c3dList.get(indexC3DInst - 3);
        Quadruple secondOperand = this.c3dList.get(indexC3DInst - 2);
        Quadruple conditional = this.c3dList.get(indexC3DInst - 1);
        
        boolean result = false;
        
        if (firstOperand.opCode != OpCode.assign || firstOperand.source1.typeOperator != TypeOperator.bool_value) return indexC3DInst;
        if (secondOperand.opCode != OpCode.assign || secondOperand.source1.typeOperator != TypeOperator.bool_value) return indexC3DInst;
        
        boolean firstOperandValue = Boolean.parseBoolean(firstOperand.source1.value);
        boolean secondOperandValue = Boolean.parseBoolean(secondOperand.source1.value);
        
        switch(c3dInst.opCode) {
            case and:
                if (!Boolean.parseBoolean(firstOperand.source1.value)) return indexC3DInst;
                result = firstOperandValue && secondOperandValue;
                break;
            case or:
                if (Boolean.parseBoolean(firstOperand.source1.value)) return indexC3DInst;
                result = firstOperandValue || secondOperandValue;
                break;
        }
        
        c3dInst.opCode = OpCode.assign;
        c3dInst.source1.typeOperator = TypeOperator.bool_value;
        c3dInst.source1.value = result + "";
        c3dInst.source2 = null;
        
        this.c3dList.remove(indexC3DInst - 3);
        this.c3dList.remove(indexC3DInst - 3);
        this.c3dList.remove(indexC3DInst - 3);
        return indexC3DInst - 3;
    }
    
    private void optimizeConditionals(Quadruple c3dInst, int indexC3DInst) {

        String idLabel = c3dInst.destination.value;

        for (int i = indexC3DInst + 1; i < this.c3dList.size(); i++) {
            
            Quadruple c3dInstSkip = this.c3dList.get(i);
            if (c3dInstSkip.opCode == OpCode.skip && c3dInstSkip.destination.value.equals(idLabel)) {
                
                if ((i + 1) == this.c3dList.size()) return;
                Quadruple c3dInstJump = this.c3dList.get(i + 1);
                
                if (c3dInstJump.opCode == OpCode.jump) {
                    idLabel = c3dInstJump.destination.value;
                }

            }
        }
        
        c3dInst.destination.value = idLabel;
    }
    
}
