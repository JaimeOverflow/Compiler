*-----------------------------------------------------------
* Title      : Practice compilers II.
* Written by : Andrés Ramos Seguí, Alex Mateo Fiol, Jaime Crespí Valero.
* Date       : 17/06/2019
* Description: Assembler code for compilers II practice.
*-----------------------------------------------------------

* ------------------------ INCLUDES ---------------------- *
    INCLUDE "MACROS.X68"
* -------------------------------------------------------- *

* ----------------------- MAIN PROGRAM -------------------- *
    ORG    $1000
START:

* CALL MAIN PROCEDURE *
* Intermediate code => [procedureCallMain, null, null, [OPERATOR. Type operator: procedure, value: 2]]
    JSR PROCEDURE_MAIN
    SIMHALT

* INITIAL LABEL (PROCEDURE) *
* Intermediate code => [procedureName, null, null, [OPERATOR. Type operator: procedure, value: 0]]
PROCEDURE_ASE2:

* PREAMBLE (PROCEDURE) *
* Intermediate code => [procedurePreamble, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    SUB.L #8, A7
    SUB.L #4, A7 ; Block pointer
    MOVE.L #0, (A7)
    MOVE.L A7, A6
    SUB.L #12, A6 ; Update stack pointer for a new activation block

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: int_value, value: 20], null, [OPERATOR. Type operator: variable, value: 2]]
    ASSIGNATION_INTEGER #20, -4

* ARITHMETIC OPERATION *
* Intermediate code => [sum, [OPERATOR. Type operator: variable, value: 0], [OPERATOR. Type operator: variable, value: 2], [OPERATOR. Type operator: variable, value: 3]]
    OP_SUMA_VAR_VAR -8, 4, -4

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 3], null, [OPERATOR. Type operator: variable, value: 4]]
    ASSIGNATION_VARIABLE_INTEGER -8, -12

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 4]]
    OUTPUT_INTEGER -12

* PRINT BUFFER *
    PRINT_BUFFER #buffer, #0 

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    ADD.L #12, A7
    RTS

* INITIAL LABEL (PROCEDURE) *
* Intermediate code => [procedureName, null, null, [OPERATOR. Type operator: procedure, value: 1]]
PROCEDURE_ASE:

* PREAMBLE (PROCEDURE) *
* Intermediate code => [procedurePreamble, null, null, [OPERATOR. Type operator: procedure, value: 1]]
    SUB.L #8, A7
    SUB.L #4, A7 ; Block pointer
    MOVE.L #0, (A7)
    MOVE.L A7, A6
    SUB.L #0, A6 ; Update stack pointer for a new activation block

* CALL PROCEDURE *
* Intermediate code => [procedureCall, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    MOVE.L A7, A5
    MOVE.L A6, A7
    MOVE.L A5, -(A7)  ;previous block pointer
    SUB.L #4, A7 ; Memory space for PC
    ************
    *   PARAM  *
    ************
    CLR.L D0 
    MOVE.L A7, A4 
    MOVE.L A5, A7 
    MOVE.L 8(A7), D0
    MOVE.L A4, A7 
    MOVE.L D0, -(A7) 
    ************
    *   PARAM  *
    ************
    CLR.L D0 
    MOVE.L A7, A4 
    MOVE.L A5, A7 
    MOVE.L 4(A7), D0
    MOVE.L A4, A7 
    MOVE.L D0, -(A7) 
    ADD.L #12, A7  ;Regresamos para poner el pc en el salto
    JSR PROCEDURE_ASE2
    ADD.L #0, A7 ;Jump return
    MOVE.L (A7)+, A5 ;Get block pointer
    MOVE.L A7, A6  ;A6 => Stack pointer
    MOVE.L A5, A7 ;Update out block pointer

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 1]]
    ADD.L #12, A7
    RTS

* INITIAL LABEL (PROCEDURE) *
* Intermediate code => [procedureName, null, null, [OPERATOR. Type operator: procedure, value: 2]]
PROCEDURE_MAIN:

* PREAMBLE (PROCEDURE) *
* Intermediate code => [procedurePreamble, null, null, [OPERATOR. Type operator: procedure, value: 2]]
    SUB.L #0, A7
    SUB.L #4, A7 ; Block pointer
    MOVE.L #0, (A7)
    MOVE.L A7, A6
    SUB.L #8, A6 ; Update stack pointer for a new activation block

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: int_value, value: 5], null, [OPERATOR. Type operator: variable, value: 7]]
    ASSIGNATION_INTEGER #5, -4

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: int_value, value: 10], null, [OPERATOR. Type operator: variable, value: 8]]
    ASSIGNATION_INTEGER #10, -8

* CALL PROCEDURE *
* Intermediate code => [procedureCall, null, null, [OPERATOR. Type operator: procedure, value: 1]]
    MOVE.L A7, A5
    MOVE.L A6, A7
    MOVE.L A5, -(A7)  ;previous block pointer
    SUB.L #4, A7 ; Memory space for PC
    ************
    *   PARAM  *
    ************
    CLR.L D0 
    MOVE.L A7, A4 
    MOVE.L A5, A7 
    MOVE.L -8(A7), D0
    MOVE.L A4, A7 
    MOVE.L D0, -(A7) 
    ************
    *   PARAM  *
    ************
    CLR.L D0 
    MOVE.L A7, A4 
    MOVE.L A5, A7 
    MOVE.L -4(A7), D0
    MOVE.L A4, A7 
    MOVE.L D0, -(A7) 
    ADD.L #12, A7  ;Regresamos para poner el pc en el salto
    JSR PROCEDURE_ASE
    ADD.L #0, A7 ;Jump return
    MOVE.L (A7)+, A5 ;Get block pointer
    MOVE.L A7, A6  ;A6 => Stack pointer
    MOVE.L A5, A7 ;Update out block pointer

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 2]]
    ADD.L #4, A7
    RTS

*Put variables and constants here
buffer ds.b 1024
;variables para mensajes 
informacion dc.b 'Introduzca un texto que no supere los 1024 caracteres:',0  
errorstring dc.b 'ERROR, La longitud del texto supera los 1024 carcateres',0 

    END    START
