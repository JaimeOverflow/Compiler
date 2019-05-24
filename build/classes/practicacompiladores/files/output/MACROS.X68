*-----------------------------------------------------------
* Title      : Practice compilers II.
* Written by : Andrés Ramos Seguí, Alex Mateo Fiol, Jaime Crespí Valero.
* Date       : 17/06/2019
* Description: Assembler code for compilers II practice.
*-----------------------------------------------------------

* ------------------------- MACROS ----------------------- *
*-----------------------------------------------------------
* Primary types are: Int, booleans, etc
ASSIGNATION_INTEGER 	MACRO
* Macro to add.
* Parameters: \1: Param1   ;Value of int or boolean
*             \2: Param2   ;Offset of destination variable
* Modifies  : Nothing
*-----------------------------------------------------------
   MOVE.L \1, \2(A7)
   ENDM
*-----------------------------------------------------------
* Primary types are: Int, booleans, etc
ASSIGNATION_BOOLEAN 	MACRO
* Macro to add.
* Parameters: \1: Param1   ;Value of int or boolean
*             \2: Param2   ;Offset of destination variable
* Modifies  : Nothing
*-----------------------------------------------------------
   MOVE.W \1, \2(A7)
   ENDM
*-----------------------------------------------------------
ASSIGNATION_STRING 	MACRO
* Macro to add.                          
* Parameters: \1: Param1   ;offset variable
*             \2: Param1   ;label variable
*             \3: Param1   ;size string
* Modifies  : D0, D1
*-----------------------------------------------------------
    MOVE.L A7, A1
    ADD.L \1, A1
    MOVE.L \2, A2
    MOVE.L \3, D0
    CMP.L #0, D0
    BEQ STRING_COPIED\@
    CLR.L D1
FILL_NEXT_CHAR\@
    MOVE.B (A2)+, D1
    MOVE.W D1, (A1)+
    SUB.L #2, D0
    CMP.L #0, D0
    BNE FILL_NEXT_CHAR\@
STRING_COPIED\@
    ENDM
*-----------------------------------------------------------
ASSIGNATION_VARIABLE_INTEGER 	MACRO
* Macro to add.                          
* Parameters: \1: Param1   ;source1
*             \2: Param2   ;destination
* Modifies  : Nothing
*-----------------------------------------------------------
   MOVE.L \1(A7), \2(A7)
   
   ENDM
*-----------------------------------------------------------
ASSIGNATION_VARIABLE_BOOLEAN 	MACRO
* Macro to add.                          
* Parameters: \1: Param1   ;source1
*             \2: Param2   ;destination
* Modifies  : Nothing
*-----------------------------------------------------------
   MOVE.W \1(A7), \2(A7)
   
   ENDM
*-----------------------------------------------------------
ASSIGNATION_VARIABLE_STRING	MACRO
* Macro to add.                          
* Parameters: \1: Param1   ;offset destination variable
*             \2: Param1   ;remaining size of destination var
*             \3: Param1   ;offset source variable
*             \4: Param1   ;size source variable
* Modifies  : D0
*-----------------------------------------------------------    
    MOVE.L A7, A1
    ADD.L \1, A1
    MOVE.L \2, D1
    MOVE.L A7, A2
    ADD.L \3, A2
    MOVE.L \4, D2
    CMP.L #0, D2
    BEQ FILL_REMAINING_STRING\@
    CLR.L D3
FILL_NEXT_CHAR\@
    MOVE.W (A2)+, D3
    MOVE.W D3, (A1)+
    SUB.L #2, D2
    CMP.L #0, D2
    BNE FILL_NEXT_CHAR\@
FILL_REMAINING_STRING\@ ; If var1 = var2 and var1 > var2
    CMP.L #0, D1
    BEQ STRING_COPIED\@
    CLR.L D2
    MOVE.W #8224, D2 ;Blank space
FILL_BLANK_CHAR\@
    MOVE.W D2, (A1)+
    SUB.L #2, D1
    CMP.L #0, D1
    BNE FILL_BLANK_CHAR\@   
STRING_COPIED\@    
    ENDM
*-----------------------------------------------------------
RETURN_GET_INTEGER 	MACRO
* Macro to add. 
* Parameters: \1: Param1   ; offset variable
* Modifies  : A5, A6, A7
*-----------------------------------------------------------
    CLR.L D0
    MOVE.L A7, A5
    MOVE.L A6, A7
    SUB.L #4, A7
    SUB.L #4, A7
    MOVE.L (A7), D0
    MOVE.L A5, A7
    MOVE.L D0, \1(A7)
    ENDM
*-----------------------------------------------------------
RETURN_GET_BOOLEAN 	MACRO
* Macro to add.
* Parameters: \1: Param1   ; offset variable
* Modifies  : D0
*-----------------------------------------------------------
    CLR.L D0
    MOVE.L A7, A5
    MOVE.L A6, A7
    SUB.L #4, A7
    SUB.L #2, A7A
    MOVE.W (A7), D0
    MOVE.L A5, A7
    MOVE.W D0, \1(A7)
    ENDM
*-----------------------------------------------------------
RETURN_GET_STRING	MACRO
* Macro to add.                          
* Parameters: \1: Param1   ;size return string
*             \2: Param1   ;offset variable
*             \3: Param1   ;sizevariable
* Modifies  : D0
*-----------------------------------------------------------   
    CLR.L D0
    CLR.L D1
    MOVE.L A6, A1
    SUB.L #4, A1
    SUB.L \1, A1
    MOVE.L A7, A2
    ADD.L \2, A2
    MOVE.L \3, D0
    MOVE.L \1, D1
    CMP.L #0, D1
    BEQ FILL_STRING\@
NEXT_CHAR\@
    MOVE.W (A1)+, D3
    MOVE.W D3, (A2)+
    SUB.L #2, D1 ;RESTAMOS 2 POR 2 BYTES
    CMP.L #0, D1
    BNE NEXT_CHAR\@
FILL_STRING\@
    CMP.L #0, D0
    BEQ END_STRING\@
    CLR.L D1
    MOVE.W #8224, D1 ;blank spaces
EMPTY_STRING\@
    MOVE.W D1, (A2)+
    SUB.L #2, D0
    CMP.L #0, D0
    BNE EMPTY_STRING\@
END_STRING\@    
    ENDM
*-----------------------------------------------------------
STANDARD_INPUT  	MACRO
* Macro to add.                          
* Parameters: \1 ; offset variable
* Modifies  : D0, D1
*-----------------------------------------------------------
    CLR.L D0
    CLR.L D1
    MOVE.L #4, D0
    TRAP #15
    MOVE.L D1, \1(A7)
    ENDM
; -----------------------------------------------------------------------------
OUTPUT_INTEGER      MACRO
; Input    - \1  ; Offset's variable with integer value
; Modifies - 
; -----------------------------------------------------------------------------
   CLR.L D0
   CLR.L D1
   MOVE.L \1(A7), D1
   MOVE.W #20, D0
   trap #15
   ENDM
; -----------------------------------------------------------------------------
OUTPUT_BOOLEAN      MACRO
; Input    - \1  ; Offset's variable with boolean value
; Modifies - 
; -----------------------------------------------------------------------------
   CLR.L D0
   CLR.L D1
   MOVE.W \1(A7), D1
   MOVE.W #20, D0
   trap #15
   ENDM
; -----------------------------------------------------------------------------
PRINT_STRING      MACRO
* Macro to add.                          
; Input    - \1  ; buffer direction
;          - \2  ; offset variable
;          - \3  ; size string / 2
; Modifies - 
; -----------------------------------------------------------------------------
    MOVE.L \1,A1
    MOVE.L A7, A2
    ADD.L \2, A2
    MOVE.L \3, D1
    CMP.L #0, D1
    BEQ END_PRINT\@
NEXT_CHAR_TO_PRINT\@ 
    MOVE.W  (A2)+, D0
    CMP.W #8224, D0
    BEQ END_PRINT\@
    MOVE.B D0 ,(A1)+
    SUB.L #1, D1
    CMP.L #0, D1
    BNE NEXT_CHAR_TO_PRINT\@
END_PRINT\@
    MOVE.W #1, D0
    MOVE.L \3, D1
    MOVE.L \1,A1
    TRAP #15

    ENDM
; -----------------------------------------------------------------------------
PRINT_BUFFER      MACRO
; Input    - \1  : size string
; -----------------------------------------------------------------------------
    MOVE.W  #0, D0
    MOVE.L \1, A1
    MOVE.L \2, D1
    TRAP      #15
    ENDM
; -----------------------------------------------------------------------------
RETURN_STRING      MACRO
; Input    - \1  ; offset return
;          - \2  ; offset variable
;          - \3  ; size string
; Modifies - 
; -----------------------------------------------------------------------------
    MOVE.L A7, A2
    ADD.L \1, A2
    MOVE.L A7, A1
    ADD.L \2, A1
    MOVE.L \3, D1
    CMP.L #0, D1
    BEQ FINAL_RETURN_STRING\@
NEXT_CHAR\@ 
    MOVE.W (A1)+, D2
    MOVE.W D2, (A2)+
    SUB.L #2, D1
    CMP.L #0, D1
    BNE NEXT_CHAR\@ 
FINAL_RETURN_STRING\@
    ENDM 
*-----------------------------------------------------------
OP_SUMA_VAR_VAR 	MACRO
* Macro to add.                          
* Parameters: \1: Param1 desti                        
*             \2: Param2 op1
*             \3: Param3 op2
* Modifies  : D0
*-----------------------------------------------------------
   CLR.L D0
   CLR.L D1

   MOVE.L \2(A7), D0
   MOVE.L \3(A7), D1
   
   ADD.L D1,D0
   
   MOVE.L D0, \1(A7)
   
   ENDM
*-----------------------------------------------------------
OP_RESTA_VAR_VAR  	MACRO
* Macro to add.                          
* Parameters: \1: Param1 desti                        
*             \2: Param2 op1
*             \3: Param3 op2
* Modifies  : D0
*-----------------------------------------------------------
   CLR.L D0
   CLR.L D1

   MOVE.L \2(A7), D0
   MOVE.L \3(A7), D1
   
   SUB.L D1,D0
   
   MOVE.L D0, \1(A7)
   
   ENDM
*-----------------------------------------------------------
OP_MULTI_VAR_VAR  	MACRO
* Macro to add.                          
* Parameters: \1: Param1 desti                        
*             \2: Param2 op1
*             \3: Param3 op2
* Modifies  : D0
*-----------------------------------------------------------
   CLR.L D0
   CLR.L D1

   MOVE.L \2(A7), D0
   MOVE.L \3(A7), D1
   
   MULS D1,D0
   
   MOVE.L D0, \1(A7)
   
   ENDM
*-----------------------------------------------------------
OP_DIV_VAR_VAR  	MACRO
* Macro to add.                          
* Parameters: \1: Param1 desti                        
*             \2: Param2 op1
*             \3: Param3 op2
* Modifies  : D0
*-----------------------------------------------------------
   CLR.L D0
   CLR.L D1

   MOVE.L \2(A7), D0
   MOVE.L \3(A7), D1
   
   DIVU D1,D0
   
   MOVE.L D0, \1(A7)
   
   ENDM
