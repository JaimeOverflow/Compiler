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
PRINT      MACRO
* Macro to add.                          
; Input    - \1  : Direccion de memoria del buffer
;          - \2  : Desplazamiento var
;          - \3  : ocupacion string / 2
; Modifies - 
; -----------------------------------------------------------------------------
            MOVE.L \1,A1     ;buffer to print
            MOVE.L A7, A2
            ADD.L \2, A2     ;Init string
            MOVE.L \3, D1
            ;mirar si es 0 la ocupacion
            CMP.L #0, D1
            BEQ print_f\@
print_loop\@ 
            MOVE.W  (A2)+, D0
            CMP.W #8224, D0
            BEQ print_f\@ ; sin son espacios salir
            MOVE.B  D0 ,(A1)+
            SUB.L #1, D1      
            CMP.L   #0, D1
            BNE     print_loop\@      ; Loop again
print_f\@            
            MOVE.W  #1, D0
            MOVE.L  \3, D1
            MOVE.L  \1,A1    ; Reload original pointing address of bufferptr
            TRAP      #15           ; Display it

            ENDM
; -----------------------------------------------------------------------------
PRINT_BUFFER      MACRO
; Escribe un string de longitud n de max 255 char por pantalla.
; Input    - \1  : size string
; -----------------------------------------------------------------------------
    MOVE.W  #0, D0
    MOVE.L \1, A1
    MOVE.L \2, D1
    TRAP      #15
    ENDM
