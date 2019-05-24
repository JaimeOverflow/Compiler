/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendCompiler;

import BackendCompiler.Quadruple.OpCode;
import SymbolsTable.TypeDescription;
import Utils.FilesManager;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jaime
 */
public class AssemblerConverter {
    
    private final String MACROS_FILENAME = "MACROS.X68";
    
    private TablesManager tablesManager;
    private ArrayList<Quadruple> c3dList;
    private ArrayList<String> variablesString;
    private String filename;
    private FilesManager filesManager;

    public AssemblerConverter(String filename, ArrayList<Quadruple> c3dList, TablesManager tablesManager) {
        if (!filename.contains(".X68")) {
            filename = filename.concat(".X68");
            System.out.println(filename);
        }
        
        this.filename = filename;
        this.c3dList = c3dList;
        this.tablesManager = tablesManager;
        this.filesManager = new FilesManager();
        this.variablesString = new ArrayList<String>();
    }
    
    private int getOffsetFromVariable (VariableBackend variableBackend) {
        // Parameters
        if (variableBackend.offset > 0) {
            switch(variableBackend.basicSubjacentType) {
                case ts_integer: return variableBackend.offset;
                default: return variableBackend.offset + 2; // String or booleans
            }
        } else {
            // Variable
            return variableBackend.offset;
        }   
    }

    public void generateAssemblerCode() {
        
        generateMacros();
        
        String assemblerCode = getAssemblerHeadboard();
        
        for(int i = 0; i < this.c3dList.size(); i++) {
            Quadruple c3dInstruction = this.c3dList.get(i);
            OpCode opCodeInst = c3dInstruction.opCode;
            
            switch (opCodeInst) {
                case procedureCallMain:
                    assemblerCode += getProcedureCallMain(c3dInstruction);
                    break;
                case procedureCall:
                    assemblerCode += getProcedureCall(c3dInstruction);
                    break;
                case procedureName:
                    assemblerCode += getProcedureInitialLabel(c3dInstruction);
                    break;
                case procedurePreamble:
                    assemblerCode += getProcedurePreamble(c3dInstruction);
                    break;
                case procedureReturn:
                    assemblerCode += getProcedureReturn(c3dInstruction);
                    break;
                case procedureEnd:
                    assemblerCode += getProcedureEnd(c3dInstruction);
                    break;
                case assign:
                    assemblerCode += getAssignation(c3dInstruction);
                    break;
                case standardInput:
                    assemblerCode += getStandardInput(c3dInstruction);
                    break;
                case standardOutput:
                    assemblerCode += getStandardOutput(c3dInstruction);
                    break;
                case sum:
                case sub:
                case mult:
                case div:
                    assemblerCode += getArithmeticOperation(c3dInstruction);
                    break;
                case skip:
                    assemblerCode += getSkip(c3dInstruction);
                    break;
                case jump:
                    assemblerCode += getJump(c3dInstruction);
                    break;
                case condTrue:
                    assemblerCode += getCondTrue(c3dInstruction);
                    break;
                case condFalse:
                    assemblerCode += getCondFalse(c3dInstruction);
                    break;
            }
        }
        
        assemblerCode += "\n*Put variables and constants here\n";
        assemblerCode += "buffer ds.b 1024\n";
        for (int i = 0; i < this.variablesString.size(); i++) {
            assemblerCode += "string_id_" + i + " dc.b " + this.variablesString.get(i).replace("\"", "'") + ", 0\n";  
        }
        assemblerCode += ";variables para mensajes \n";
        assemblerCode += "informacion dc.b 'Introduzca un texto que no supere los 1024 caracteres:',0  \n" +
                       "errorstring dc.b 'ERROR, La longitud del texto supera los 1024 carcateres',0 \n";
        assemblerCode +=  "\n    END    START\n";
        
        filesManager.writeFile(filename, assemblerCode);
    }
    
    private String getAssemblerHeadboard() {
        String result = "*-----------------------------------------------------------\n" +
                        "* Title      : Practice compilers II.\n" +
                        "* Written by : Andrés Ramos Seguí, Alex Mateo Fiol, Jaime Crespí Valero.\n" +
                        "* Date       : 17/06/2019\n" +
                        "* Description: Assembler code for compilers II practice.\n" +
                        "*-----------------------------------------------------------\n"+
                        "\n* ------------------------ INCLUDES ---------------------- *\n"+
                        "    INCLUDE \"MACROS.X68\"\n"+
                        "* -------------------------------------------------------- *\n"+
                        "\n* ----------------------- MAIN PROGRAM -------------------- *\n"+
                        "    ORG    $1000\n"+
                        "START:\n";
        return result;
    }
    
    private String getProcedureCallMain(Quadruple c3dInstruction) {
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        ProcedureBackend procedure = this.tablesManager.getProcedure(idProcedure);
        
        String result = "";
        result += "\n* CALL MAIN PROCEDURE *\n"+
        "* Intermediate code => " + c3dInstruction.toString() + "\n"+
        "    JSR " + procedure.initialLabel + "\n"+
        "    SIMHALT\n";
        
        return result;
    }
    
    private String getProcedureCall(Quadruple c3dInstruction) {
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        ProcedureBackend procedure = this.tablesManager.getProcedure(idProcedure);
        
        String result = "";
        result += "\n* CALL PROCEDURE *\n"+
            "* Intermediate code => " + c3dInstruction.toString() + "\n"+
            "    MOVE.L A7, A5\n"+
            "    MOVE.L A6, A7\n"+
            "    MOVE.L A5, -(A7)  ;previous block pointer\n";
        if (procedure.basicSubjacentType != TypeDescription.BASIC_SUBJACENT_TYPE.ts_none) {
            result += "    SUB.L #" + procedure.size +", A7 ; Memory space for return\n";
        }
        result += "    SUB.L #4, A7 ; Memory space for PC\n";
        
        if (procedure.numParams > 0) {
            System.out.println("OKI");
        }
        //Llamamos al metodo
        int offsetProcedure = Math.abs(procedure.sizeTemporalArgs)+4;
        result += "    ADD.L #"+offsetProcedure+", A7  ;Regresamos para poner el pc en el salto\n";
        result += "    JSR " + procedure.initialLabel + "\n";
        // Come back from procedure
        result += "    ADD.L #" + procedure.size + ", A7 ;Jump return\n";
        result += "    MOVE.L (A7)+, A5 ;Get block pointer\n";
        result += "    MOVE.L A7, A6  ;A6 => Stack pointer\n";
        result += "    MOVE.L A5, A7 ;Update out block pointer\n";
        
        return result;
    }
    
    private String getProcedureInitialLabel(Quadruple c3dInstruction){
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        String result = "";
        result += "\n* INITIAL LABEL (PROCEDURE) *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n"+
                this.tablesManager.getProcedure(idProcedure).initialLabel + ":\n";
        
        return result;
    }

    private String getProcedurePreamble(Quadruple c3dInstruction){
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        ProcedureBackend procedure = this.tablesManager.getProcedure(idProcedure);
        String result = "";
        result += "\n* PREAMBLE (PROCEDURE) *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n"+
                "    SUB.L #"+Math.abs(procedure.sizeTemporalArgs)+", A7\n"+
                "    SUB.L #4, A7 ; Block pointer\n"+
                "    MOVE.L #0, (A7)\n"+
                "    MOVE.L A7, A6\n"+
                "    SUB.L #"+Math.abs(procedure.sizeLocalVariables)+", A6 ; Update stack pointer for a new activation block\n";
        
        return result;
    }
    
    private String getProcedureReturn(Quadruple c3dInstruction){
        int idProcedure = Integer.parseInt(c3dInstruction.source1.value);
        int idVariable = Integer.parseInt(c3dInstruction.destination.value);
        
        ProcedureBackend procedure = this.tablesManager.getProcedure(idProcedure);
        VariableBackend variable = this.tablesManager.getVariable(idVariable);
        
        int pcSize = 4; // bytes;
        int blockPointerSize = 4; // bytes
        int offsetReturn = Math.abs(procedure.sizeTemporalArgs) + blockPointerSize + pcSize;
        
        String result = "";
        result += "\n* RETURN (PROCEDURE) *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n";
                
        switch(variable.basicSubjacentType) {
            case ts_integer:
                result += "    MOVE.L " + this.getOffsetFromVariable(variable) + "(A7), D0\n";
                result += "    MOVE.L D0, " + offsetReturn + "(A7)\n";
                break;
            case ts_boolean:
                result += "    MOVE.W " + this.getOffsetFromVariable(variable) + "(A7), D0\n";
                result += "    MOVE.W D0, " + offsetReturn + "(A7)\n";
                break;
            case ts_string:
                int putStringInReturn = IdentificarStringArg(idVariable);
                if(putStringInReturn==0){
                    putStringInReturn = variable.offset;
                }
                result += "    RETURN_STRING #" + offsetReturn + ", #" + putStringInReturn + ", #"+variable.size+"\n";
                break;
        }
        
        return result;
    }
    
    private String getProcedureEnd(Quadruple c3dInstruction) {
        int idProcedure = Integer.parseInt(c3dInstruction.destination.value);
        ProcedureBackend procedure = this.tablesManager.getProcedure(idProcedure);
        int sizeBlockPointer = 4;
        int offsetToGetOutOfProcedure = Math.abs(procedure.sizeTemporalArgs) + sizeBlockPointer; 
        String result = "";
        result += "\n* PREAMBLE END (PROCEDURE) *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n"+
                "    ADD.L #" + offsetToGetOutOfProcedure + ", A7\n"+
                "    RTS\n";

        return result;
        
    }

    private String getAssignation(Quadruple c3dInstruction){
        String valueC3DInstruction = c3dInstruction.source1.value;
        
        int variableSource1Value;
        int idVariableDestination = Integer.parseInt(c3dInstruction.destination.value);
        
        VariableBackend variableBackendDestination = this.tablesManager.getVariable(idVariableDestination);
        VariableBackend variableBackendSource1;
        String result = "";
        result += "\n* ASSIGNATION VARIABLE *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n";
        
        switch (c3dInstruction.source1.typeOperator) {

            case variable:
                variableSource1Value = Integer.parseInt(valueC3DInstruction);
                variableBackendSource1 = this.tablesManager.getVariable(variableSource1Value);
                switch(variableBackendSource1.basicSubjacentType) {
                    case ts_integer:
                        result += "    ASSIGNATION_VARIABLE_INTEGER " +
                                this.getOffsetFromVariable(variableBackendSource1) +
                                ", " +
                                this.getOffsetFromVariable(variableBackendDestination) + 
                                "\n";
                        break;
                        case ts_boolean:
                            result += "    ASSIGNATION_VARIABLE_BOOLEAN " +
                                this.getOffsetFromVariable(variableBackendSource1) +
                                ", " +
                                this.getOffsetFromVariable(variableBackendDestination) + 
                                "\n";
                        break;   
                    case ts_string:
                        
                        int differenceStringSize = Math.abs(variableBackendSource1.size - variableBackendDestination.size);
                        int offsetSource1 = IdentificarStringArg(variableSource1Value);
                        if(offsetSource1==0){
                            offsetSource1 = variableBackendSource1.offset;
                        }
                        int offsetDestination = IdentificarStringArg(idVariableDestination);
                        if(offsetDestination==0){
                            offsetDestination = variableBackendDestination.offset;
                        }
                        result += "    ASSIGNATION_VARIABLE_STRING #"+offsetDestination+", #"+differenceStringSize+", #"+offsetSource1+", #"+variableBackendSource1.size+" \n";
                        break; 
                }
                break;
            case procedure:
                variableSource1Value = Integer.parseInt(valueC3DInstruction);
                variableBackendSource1 = this.tablesManager.getVariable(variableSource1Value);
                switch(variableBackendSource1.basicSubjacentType) {
                    case ts_integer:
                        result += "    RETURN_GET_INTEGER " + this.getOffsetFromVariable(variableBackendDestination) + "\n";
                        break;
                    case ts_boolean:
                        result += "    RETURN_GET_BOOLEAN " + this.getOffsetFromVariable(variableBackendDestination) + "\n";
                        break;
                    case ts_string:
                        int offsetDestinationReturn = IdentificarStringArg(idVariableDestination);
                        if(offsetDestinationReturn==0){
                            offsetDestinationReturn = variableBackendDestination.offset;
                        }
                        int sizeReturn = variableBackendSource1.size;
                        result += "    RETURN_GET_STRING #"+sizeReturn+", #"+offsetDestinationReturn+", #"+variableBackendDestination.size+"  \n";
                        break;
                }
                
                break;
            case int_value:
                variableSource1Value = Integer.parseInt(valueC3DInstruction);
                result += "    ASSIGNATION_INTEGER " +
                        "#" + variableSource1Value+
                        ", " + this.getOffsetFromVariable(variableBackendDestination)+
                        "\n";
                break;

            case bool_value:
                variableSource1Value = valueC3DInstruction.equals("true") ? 1 : 0;
                result += "    ASSIGNATION_BOOLEAN " +
                        "#" + variableSource1Value+
                        ", " + this.getOffsetFromVariable(variableBackendDestination)+
                        "\n";
                break;

            case string_value:
                String textString = valueC3DInstruction;

                String labelString = "string_id_" + this.variablesString.size();
                this.variablesString.add(textString);
                int offsetParameters = IdentificarStringArg(idVariableDestination);
                if(offsetParameters == 0){
                    offsetParameters = variableBackendDestination.offset;
                 }
                result += "    ASSIGNATION_STRING " +
                          "#" + offsetParameters+
                        ", #" + labelString+
                        ", #" + variableBackendDestination.size+
                        "\n";
                break;
        }
        
        
        return result;
    }
    
    private int IdentificarStringArg(int param1){
        VariableBackend actual = this.tablesManager.getVariable(param1);
        VariableBackend anterior= null;
        if(param1 != 0){
            anterior = this.tablesManager.getVariable(param1-1);
        }
        //ES un argumento por que si es String no son 0, son 2048
        if(actual.offset > 0){
            //Mirar si es el primero
            if(anterior == null || actual.idProcedure != anterior.idProcedure){ //Es el primer arg
                return 4;
            }else{
                if(anterior.basicSubjacentType == TypeDescription.BASIC_SUBJACENT_TYPE.ts_integer){
                    return anterior.offset+anterior.size;
                }else{
                    return anterior.offset+4; //tanto para string como boolean es +2 para el bp y los dos ocupan 2 bytes = 4
                }
            }
        }
        return 0; //NO es arg
    }
    
    private String getStandardOutput(Quadruple c3dInstruction){
        int idVariableDestination = Integer.parseInt(c3dInstruction.destination.value);
        VariableBackend variableBackendDestination = this.tablesManager.getVariable(idVariableDestination);
        String result = "";
        result += "\n* STANDARD OUTPUT *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n";
        
        switch(variableBackendDestination.basicSubjacentType) {
            case ts_integer:
                result += "    OUTPUT_INTEGER " + variableBackendDestination.offset+"\n";
                break;
            case ts_boolean:
                result += "    OUTPUT_BOOLEAN " + variableBackendDestination.offset+"\n";
                break;
            case ts_string:
                int variableSize = variableBackendDestination.size / 2;
                int offsetVariable= IdentificarStringArg(idVariableDestination);
                if(offsetVariable == 0){
                    offsetVariable= variableBackendDestination.offset;
                }
                result += "    CLR.L buffer  \n";
                result += "    PRINT_STRING #buffer, #"+offsetVariable+", #"+variableSize+"\n";
                break;
            default:
        }
        result += "\n* PRINT BUFFER *\n"+
        "    PRINT_BUFFER #buffer, #0 \n";
        return result;
    }
 
    private String getStandardInput(Quadruple c3dInstruction){
        int idVariableDestination = Integer.parseInt(c3dInstruction.destination.value);
        VariableBackend variableBackendDestination = this.tablesManager.getVariable(idVariableDestination);
        String result = "";
        result += "\n* STANDARD INPUT *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n"+
                "    STANDARD_INPUT " + this.getOffsetFromVariable(variableBackendDestination) + "\n";
        return result;
    }
            
    private String getArithmeticOperation(Quadruple c3dInstruction){
        int idVariableSource1 = Integer.parseInt(c3dInstruction.source1.value);
        VariableBackend variableBackendSource1 = this.tablesManager.getVariable(idVariableSource1);
        
        int idVariableSource2 = Integer.parseInt(c3dInstruction.source2.value);
        VariableBackend variableBackendSource2 = this.tablesManager.getVariable(idVariableSource2);
        
        int idVariableDestination = Integer.parseInt(c3dInstruction.destination.value);
        VariableBackend variableBackendDestination = this.tablesManager.getVariable(idVariableDestination);

        String result = "";
        result += "\n* ARITHMETIC OPERATION *\n"+
                "* Intermediate code => " + c3dInstruction.toString() + "\n";
        
        switch(c3dInstruction.opCode) {
            case sum:
                result += "    OP_SUMA_VAR_VAR ";
                break;
            case sub:
                result += "    OP_SUMA_VAR_VAR ";
                break;
            case mult:
                result += "    OP_MULTI_VAR_VAR ";
                break;
            case div:
                result += "    OP_DIV_VAR_VAR ";
                break;
        }
        result += this.getOffsetFromVariable(variableBackendDestination)+", "+this.getOffsetFromVariable(variableBackendSource1)+", "+this.getOffsetFromVariable(variableBackendSource2)+"\n";   
                
        
        return result;
    }

    private String getSkip(Quadruple c3dInstruction) {
        int idLabel = Integer.parseInt(c3dInstruction.destination.value);
        LabelBackend label = this.tablesManager.getLabel(idLabel);
        
        String result = "";
        result += "\n* SKIP (LABEL) *\n"+
        "* Intermediate code => " + c3dInstruction.toString() + "\n"+
        label.label + ":\n";
        
        return result;
    }
    
    private String getJump(Quadruple c3dInstruction) {
        int idLabel = Integer.parseInt(c3dInstruction.destination.value);
        LabelBackend label = this.tablesManager.getLabel(idLabel);
        
        String result = "";
        result += "\n* JUMP (LABEL) *\n"+
        "* Intermediate code => " + c3dInstruction.toString() + "\n"+
        "    JMP " + label.label + "\n";
        
        return result;
    }
    
    private String getCondTrue(Quadruple c3dInstruction) {
        int idVariable = Integer.parseInt(c3dInstruction.source1.value);
        VariableBackend variable = this.tablesManager.getVariable(idVariable);
        
        
        int idLabel = Integer.parseInt(c3dInstruction.destination.value);
        LabelBackend label = this.tablesManager.getLabel(idLabel);
        
        String result = "";
        result += "\n* True condition *\n"+
        "* Intermediate code => " + c3dInstruction.toString() + "\n"+
        "    MOVE.W " + this.getOffsetFromVariable(variable) + "(A7), D0\n"+
        "    CMP.W #1, D0\n"+
        "    BEQ " + label.label + "\n";
        
        return result;
    }
    
    private String getCondFalse(Quadruple c3dInstruction) {
        int idVariable = Integer.parseInt(c3dInstruction.source1.value);
        VariableBackend variable = this.tablesManager.getVariable(idVariable);
        
        
        int idLabel = Integer.parseInt(c3dInstruction.destination.value);
        LabelBackend label = this.tablesManager.getLabel(idLabel);
        
        String result = "";
        result += "\n* False condition *\n"+
        "* Intermediate code => " + c3dInstruction.toString() + "\n"+
        "    MOVE.W " + this.getOffsetFromVariable(variable) + "(A7), D0\n"+
        "    CMP.W #0, D0\n"+
        "    BEQ " + label.label + "\n";
        
        return result;
    }
        
    private void generateMacros() {
        String macros = getMacrosHeadboard();
        macros += getMacroAssignationInteger();
        macros += getMacroAssignationBoolean();
        macros += getMacroAssignationString();
        macros += getMacroAssignationVariableInteger();
        macros += getMacroAssignationVariableBoolean();
        macros += getMacroAssignationVariableString();
        macros += getMacroRecoverIntegerFromReturn();
        macros += getMacroRecoverBooleanFromReturn();
        macros += getMacroRecoverStringFromReturn();
        macros += getMacroStandardInput();
        macros += getMacroOutputInteger();
        macros += getMacroOutputBoolean();
        macros += getMacroOutputString();
        macros += getMacroPrintNewLine();
        macros += getMacroReturnString();
        macros += getMacroOperationArithmeticSum();
        macros += getMacroOperationArithmeticSub();
        macros += getMacroOperationArithmeticMult();
        macros += getMacroOperationArithmeticDiv();
        
        
        filesManager.writeFile(MACROS_FILENAME, macros);
    }
    
    private String getMacrosHeadboard() {
        String result = "*-----------------------------------------------------------\n" +
                        "* Title      : Practice compilers II.\n" +
                        "* Written by : Andrés Ramos Seguí, Alex Mateo Fiol, Jaime Crespí Valero.\n" +
                        "* Date       : 17/06/2019\n" +
                        "* Description: Assembler code for compilers II practice.\n" +
                        "*-----------------------------------------------------------\n"+
                        "\n* ------------------------- MACROS ----------------------- *\n";
        return result;
    }
    
    private String getMacroAssignationVariableInteger() {
        String result = "";
        result += "*-----------------------------------------------------------\n" +
            "ASSIGNATION_VARIABLE_INTEGER 	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1   ;source1\n" +
            "*             \\2: Param2   ;destination\n" +
            "* Modifies  : Nothing\n" +
            "*-----------------------------------------------------------\n" +
            "   MOVE.L \\1(A7), \\2(A7)\n" +
            "   \n   ENDM\n";
        return result;
    }
    
    private String getMacroAssignationVariableBoolean() {
        String result = "";
        result += "*-----------------------------------------------------------\n" +
            "ASSIGNATION_VARIABLE_BOOLEAN 	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1   ;source1\n" +
            "*             \\2: Param2   ;destination\n" +
            "* Modifies  : Nothing\n" +
            "*-----------------------------------------------------------\n" +
            "   MOVE.W \\1(A7), \\2(A7)\n" +
            "   \n   ENDM\n";
        return result;
    }
    
    private String getMacroAssignationVariableString() {
        String result = "";
        result += "*-----------------------------------------------------------\n" +
            "ASSIGNATION_VARIABLE_STRING	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1   ;offset destination variable\n" +
            "*             \\2: Param1   ;remaining size of destination var\n" +
            "*             \\3: Param1   ;offset source variable\n" +
            "*             \\4: Param1   ;size source variable\n" +
            "* Modifies  : D0\n" +
            "*-----------------------------------------------------------    \n" +
            "    MOVE.L A7, A1\n" +
            "    ADD.L \\1, A1\n" +
            "    MOVE.L \\2, D1\n" +
            "    MOVE.L A7, A2\n" +
            "    ADD.L \\3, A2\n" +
            "    MOVE.L \\4, D2\n" +
            "    CMP.L #0, D2\n" +
            "    BEQ FILL_REMAINING_STRING\\@\n" +
            "    CLR.L D3\n" +
            "FILL_NEXT_CHAR\\@\n" +
            "    MOVE.W (A2)+, D3\n" +
            "    MOVE.W D3, (A1)+\n" +
            "    SUB.L #2, D2\n" +
            "    CMP.L #0, D2\n" +
            "    BNE FILL_NEXT_CHAR\\@\n" +
            "FILL_REMAINING_STRING\\@ ; If var1 = var2 and var1 > var2\n" +
            "    CMP.L #0, D1\n" +
            "    BEQ STRING_COPIED\\@\n" +
            "    CLR.L D2\n" +
            "    MOVE.W #8224, D2 ;Blank space\n" +
            "FILL_BLANK_CHAR\\@\n" +
            "    MOVE.W D2, (A1)+\n" +
            "    SUB.L #2, D1\n" +
            "    CMP.L #0, D1\n" +
            "    BNE FILL_BLANK_CHAR\\@   \n" +
            "STRING_COPIED\\@    \n" +
            "    ENDM\n";
        return result;
    }
    
    private String getMacroAssignationInteger() {
        String result = "";
        result += "*-----------------------------------------------------------\n" +
            "* Primary types are: Int, booleans, etc\n"+
            "ASSIGNATION_INTEGER 	MACRO\n" +
            "* Macro to add.\n" +
            "* Parameters: \\1: Param1   ;Value of int or boolean\n" +
            "*             \\2: Param2   ;Offset of destination variable\n" +
            "* Modifies  : Nothing\n" +
            "*-----------------------------------------------------------\n" +
            "   MOVE.L \\1, \\2(A7)\n"
            + "   ENDM\n";
        return result;
    }
    
    private String getMacroAssignationBoolean() {
        String result = "";
        result += "*-----------------------------------------------------------\n" +
            "* Primary types are: Int, booleans, etc\n"+
            "ASSIGNATION_BOOLEAN 	MACRO\n" +
            "* Macro to add.\n" +
            "* Parameters: \\1: Param1   ;Value of int or boolean\n" +
            "*             \\2: Param2   ;Offset of destination variable\n" +
            "* Modifies  : Nothing\n" +
            "*-----------------------------------------------------------\n" +
            "   MOVE.W \\1, \\2(A7)\n"
            + "   ENDM\n";
        return result;
    }
    
    private String getMacroAssignationString() {
        String result = "";
        result += "*-----------------------------------------------------------\n" +
            "ASSIGNATION_STRING 	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1   ;offset variable\n" +
            "*             \\2: Param1   ;label variable\n" +
            "*             \\3: Param1   ;size string\n" +
            "* Modifies  : D0, D1\n" +
            "*-----------------------------------------------------------\n" +
            "    MOVE.L A7, A1\n" +
            "    ADD.L \\1, A1\n" +
            "    MOVE.L \\2, A2\n" +
            "    MOVE.L \\3, D0\n" +
            "    CMP.L #0, D0\n" +
            "    BEQ STRING_COPIED\\@\n" +
            "    CLR.L D1\n" +
            "FILL_NEXT_CHAR\\@\n" +
            "    MOVE.B (A2)+, D1\n" +
            "    MOVE.W D1, (A1)+\n" +
            "    SUB.L #2, D0\n" +
            "    CMP.L #0, D0\n" +
            "    BNE FILL_NEXT_CHAR\\@\n" +
            "STRING_COPIED\\@\n"+
            "    ENDM\n";
        return result;
    }
    
    private String getMacroOutputInteger() {
        String result = "";
        result += "; -----------------------------------------------------------------------------\n" +
            "OUTPUT_INTEGER      MACRO\n" +
            "; Input    - \\1  ; Offset's variable with integer value\n" +
            "; Modifies - \n" +
            "; -----------------------------------------------------------------------------\n" +
            "   CLR.L D0\n" +
            "   CLR.L D1\n" +
            "   MOVE.L \\1(A7), D1\n" +
            "   MOVE.W #20, D0\n" +
            "   trap #15\n" +
            "   ENDM\n";
        return result;
    }
    
    private String getMacroOutputBoolean() {
        String result = "";
        result += "; -----------------------------------------------------------------------------\n" +
            "OUTPUT_BOOLEAN      MACRO\n" +
            "; Input    - \\1  ; Offset's variable with boolean value\n" +
            "; Modifies - \n" +
            "; -----------------------------------------------------------------------------\n" +
            "   CLR.L D0\n" +
            "   CLR.L D1\n" +
            "   MOVE.W \\1(A7), D1\n" +
            "   MOVE.W #20, D0\n" +
            "   trap #15\n" +
            "   ENDM\n";
        return result;
    }
    
    private String getMacroOutputString() {
        String result = "";
        result += "; -----------------------------------------------------------------------------\n" +
            "PRINT_STRING      MACRO\n" +
            "* Macro to add.                          \n" +
            "; Input    - \\1  ; buffer direction\n" +
            ";          - \\2  ; offset variable\n" +
            ";          - \\3  ; size string / 2\n" +
            "; Modifies - \n" +
            "; -----------------------------------------------------------------------------\n" +
            "    MOVE.L \\1,A1\n" +
            "    MOVE.L A7, A2\n" +
            "    ADD.L \\2, A2\n" +
            "    MOVE.L \\3, D1\n" +
            "    CMP.L #0, D1\n" +
            "    BEQ END_PRINT\\@\n" +
            "NEXT_CHAR_TO_PRINT\\@ \n" +
            "    MOVE.W  (A2)+, D0\n" +
            "    CMP.W #8224, D0\n" +
            "    BEQ END_PRINT\\@\n" +
            "    MOVE.B D0 ,(A1)+\n" +
            "    SUB.L #1, D1\n" +
            "    CMP.L #0, D1\n" +
            "    BNE NEXT_CHAR_TO_PRINT\\@\n" +
            "END_PRINT\\@\n" +
            "    MOVE.W #1, D0\n" +
            "    MOVE.L \\3, D1\n" +
            "    MOVE.L \\1,A1\n" +
            "    TRAP #15\n" +
            "\n    ENDM\n";
        return result;
    }

    private String getMacroPrintNewLine() {
        String result = "";
        result += "; -----------------------------------------------------------------------------\n" +
            "PRINT_BUFFER      MACRO\n" +
            "; Input    - \\1  : size string\n" +
            "; -----------------------------------------------------------------------------\n" +
            "    MOVE.W  #0, D0\n" +
            "    MOVE.L \\1, A1\n" +
            "    MOVE.L \\2, D1\n" +
            "    TRAP      #15\n" +
            "    ENDM\n";
        return result;
    }

    private String getMacroReturnString() {
        String result = "";
        result += "; -----------------------------------------------------------------------------\n" +
            "RETURN_STRING      MACRO\n" +
            "; Input    - \\1  ; offset return\n" +
            ";          - \\2  ; offset variable\n" +
            ";          - \\3  ; size string\n" +
            "; Modifies - \n" +
            "; -----------------------------------------------------------------------------\n" +
            "    MOVE.L A7, A2\n" +
            "    ADD.L \\1, A2\n" +
            "    MOVE.L A7, A1\n" +
            "    ADD.L \\2, A1\n" +
            "    MOVE.L \\3, D1\n" +
            "    CMP.L #0, D1\n" +
            "    BEQ FINAL_RETURN_STRING\\@\n" +
            "NEXT_CHAR\\@ \n" +
            "    MOVE.W (A1)+, D2\n" +
            "    MOVE.W D2, (A2)+\n" +
            "    SUB.L #2, D1\n" +
            "    CMP.L #0, D1\n" +
            "    BNE NEXT_CHAR\\@ \n" +
            "FINAL_RETURN_STRING\\@\n" +
            "    ENDM \n";
        return result;
        
    }

    private String getMacroRecoverIntegerFromReturn() {
        String result = "*-----------------------------------------------------------\n" +
            "RETURN_GET_INTEGER 	MACRO\n" +
            "* Macro to add. \n" +
            "* Parameters: \\1: Param1   ; offset variable\n" +
            "* Modifies  : A5, A6, A7\n" +
            "*-----------------------------------------------------------\n" +
            "    CLR.L D0\n" +
            "    MOVE.L A7, A5\n" +
            "    MOVE.L A6, A7\n" +
            "    SUB.L #4, A7\n" +
            "    SUB.L #4, A7\n" +
            "    MOVE.L (A7), D0\n" +
            "    MOVE.L A5, A7\n" +
            "    MOVE.L D0, \\1(A7)\n" +
            "    ENDM\n";
        return result; 
    }
    
    private String getMacroRecoverBooleanFromReturn() {
        String result = "*-----------------------------------------------------------\n" +
                    "RETURN_GET_BOOLEAN 	MACRO\n" +
                    "* Macro to add.\n" +
                    "* Parameters: \\1: Param1   ; offset variable\n" +
                    "* Modifies  : D0\n" +
                    "*-----------------------------------------------------------\n" +
                    "    CLR.L D0\n" +
                    "    MOVE.L A7, A5\n" +
                    "    MOVE.L A6, A7\n" +
                    "    SUB.L #4, A7\n" +
                    "    SUB.L #2, A7A\n" +
                    "    MOVE.W (A7), D0\n" +
                    "    MOVE.L A5, A7\n" +
                    "    MOVE.W D0, \\1(A7)\n" +
                    "    ENDM\n";
        return result; 
    }
    
    private String getMacroRecoverStringFromReturn() {
        String result = "*-----------------------------------------------------------\n" +
                    "RETURN_GET_STRING	MACRO\n" +
                    "* Macro to add.                          \n" +
                    "* Parameters: \\1: Param1   ;size return string\n" +
                    "*             \\2: Param1   ;offset variable\n" +
                    "*             \\3: Param1   ;sizevariable\n" +
                    "* Modifies  : D0\n" +
                    "*-----------------------------------------------------------   \n" +
                    "    CLR.L D0\n" +
                    "    CLR.L D1\n" +
                    "    MOVE.L A6, A1\n" +
                    "    SUB.L #4, A1\n" +
                    "    SUB.L \\1, A1\n" +
                    "    MOVE.L A7, A2\n" +
                    "    ADD.L \\2, A2\n" +
                    "    MOVE.L \\3, D0\n" +
                    "    MOVE.L \\1, D1\n" +
                    "    CMP.L #0, D1\n" +
                    "    BEQ FILL_STRING\\@\n" +
                    "NEXT_CHAR\\@\n" +
                    "    MOVE.W (A1)+, D3\n" +
                    "    MOVE.W D3, (A2)+\n" +
                    "    SUB.L #2, D1 ;RESTAMOS 2 POR 2 BYTES\n" +
                    "    CMP.L #0, D1\n" +
                    "    BNE NEXT_CHAR\\@\n" +
                    "FILL_STRING\\@\n" +
                    "    CMP.L #0, D0\n" +
                    "    BEQ END_STRING\\@\n" +
                    "    CLR.L D1\n" +
                    "    MOVE.W #8224, D1 ;blank spaces\n" +
                    "EMPTY_STRING\\@\n" +
                    "    MOVE.W D1, (A2)+\n" +
                    "    SUB.L #2, D0\n" +
                    "    CMP.L #0, D0\n" +
                    "    BNE EMPTY_STRING\\@\n" +
                    "END_STRING\\@    \n" +
                    "    ENDM\n";
        return result; 
    }
    
    private String getMacroOperationArithmeticSum() {
        String result = "*-----------------------------------------------------------\n" +
            "OP_SUMA_VAR_VAR 	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1 desti                        \n" +
            "*             \\2: Param2 op1\n" +
            "*             \\3: Param3 op2\n" +
            "* Modifies  : D0\n" +
            "*-----------------------------------------------------------\n" +
            "   CLR.L D0\n" +
            "   CLR.L D1\n" +
            "\n" +
            "   MOVE.L \\2(A7), D0\n" +
            "   MOVE.L \\3(A7), D1\n" +
            "   \n" +
            "   ADD.L D1,D0\n" +
            "   \n" +
            "   MOVE.L D0, \\1(A7)\n" +
            "   \n" +
            "   ENDM\n";
        return result; 
    }
    
    private String getMacroOperationArithmeticSub() {
        String result = "*-----------------------------------------------------------\n" +
            "OP_RESTA_VAR_VAR  	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1 desti                        \n" +
            "*             \\2: Param2 op1\n" +
            "*             \\3: Param3 op2\n" +
            "* Modifies  : D0\n" +
            "*-----------------------------------------------------------\n" +
            "   CLR.L D0\n" +
            "   CLR.L D1\n" +
            "\n" +
            "   MOVE.L \\2(A7), D0\n" +
            "   MOVE.L \\3(A7), D1\n" +
            "   \n" +
            "   SUB.L D1,D0\n" +
            "   \n" +
            "   MOVE.L D0, \\1(A7)\n" +
            "   \n" +
            "   ENDM\n";
        return result; 
    }
    
    private String getMacroOperationArithmeticMult() {
        String result = "*-----------------------------------------------------------\n" +
            "OP_MULTI_VAR_VAR  	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1 desti                        \n" +
            "*             \\2: Param2 op1\n" +
            "*             \\3: Param3 op2\n" +
            "* Modifies  : D0\n" +
            "*-----------------------------------------------------------\n" +
            "   CLR.L D0\n" +
            "   CLR.L D1\n" +
            "\n" +
            "   MOVE.L \\2(A7), D0\n" +
            "   MOVE.L \\3(A7), D1\n" +
            "   \n" +
            "   MULS D1,D0\n" +
            "   \n" +
            "   MOVE.L D0, \\1(A7)\n" +
            "   \n" +
            "   ENDM\n";
        return result; 
    }
    
    private String getMacroOperationArithmeticDiv() {
        String result = "*-----------------------------------------------------------\n" +
            "OP_DIV_VAR_VAR  	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1: Param1 desti                        \n" +
            "*             \\2: Param2 op1\n" +
            "*             \\3: Param3 op2\n" +
            "* Modifies  : D0\n" +
            "*-----------------------------------------------------------\n" +
            "   CLR.L D0\n" +
            "   CLR.L D1\n" +
            "\n" +
            "   MOVE.L \\2(A7), D0\n" +
            "   MOVE.L \\3(A7), D1\n" +
            "   \n" +
            "   DIVU D1,D0\n" +
            "   \n" +
            "   MOVE.L D0, \\1(A7)\n" +
            "   \n" +
            "   ENDM\n";
        return result; 
    }
    
    private String getMacroStandardInput() {
        String result = "*-----------------------------------------------------------\n" +
            "STANDARD_INPUT  	MACRO\n" +
            "* Macro to add.                          \n" +
            "* Parameters: \\1 ; offset variable\n" +
            "* Modifies  : D0, D1\n" +
            "*-----------------------------------------------------------\n" +
            "    CLR.L D0\n"+
            "    CLR.L D1\n"+
            "    MOVE.L #4, D0\n"+
            "    TRAP #15\n"+
            "    MOVE.L D1, \\1(A7)\n" +
            "    ENDM\n";
        return result; 
        
    }
    
}
