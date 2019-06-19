*-----------------------------------------------------------
* Title      : Practice compilers II.
* Written by : Andres Ramos Segui, Alex Mateo Fiol, Jaime Crespi Valero.
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
PROCEDURE_GETINPUTNUM:

* PREAMBLE (PROCEDURE) *
* Intermediate code => [procedurePreamble, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    SUB.L #0, A7
    SUB.L #4, A7 ; Block pointer
    MOVE.L #0, (A7)
    MOVE.L A7, A6
    SUB.L #8, A6 ; Update stack pointer for a new activation block

* STANDARD INPUT *
* Intermediate code => [standardInput, null, null, [OPERATOR. Type operator: variable, value: 0]]
    STANDARD_INPUT -4

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 0], null, [OPERATOR. Type operator: variable, value: 1]]
    ASSIGNATION_VARIABLE_INTEGER -4, -8

* RETURN (PROCEDURE) *
* Intermediate code => [procedureReturn, [OPERATOR. Type operator: procedure, value: 0], null, [OPERATOR. Type operator: variable, value: 1]]
    MOVE.L -8(A7), D0
    MOVE.L D0, 8(A7)

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    ADD.L #4, A7
    RTS

* INITIAL LABEL (PROCEDURE) *
* Intermediate code => [procedureName, null, null, [OPERATOR. Type operator: procedure, value: 1]]
PROCEDURE_GETSQUARE:

* PREAMBLE (PROCEDURE) *
* Intermediate code => [procedurePreamble, null, null, [OPERATOR. Type operator: procedure, value: 1]]
    SUB.L #4, A7
    SUB.L #4, A7 ; Block pointer
    MOVE.L #0, (A7)
    MOVE.L A7, A6
    SUB.L #8, A6 ; Update stack pointer for a new activation block

* ARITHMETIC OPERATION *
* Intermediate code => [mult, [OPERATOR. Type operator: variable, value: 2], [OPERATOR. Type operator: variable, value: 2], [OPERATOR. Type operator: variable, value: 3]]
    ARITH_OPERATION_MULT -4, 4, 4

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 3], null, [OPERATOR. Type operator: variable, value: 4]]
    ASSIGNATION_VARIABLE_INTEGER -4, -8

* RETURN (PROCEDURE) *
* Intermediate code => [procedureReturn, [OPERATOR. Type operator: procedure, value: 1], null, [OPERATOR. Type operator: variable, value: 4]]
    MOVE.L -8(A7), D0
    MOVE.L D0, 12(A7)

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 1]]
    ADD.L #8, A7
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
    SUB.L #60, A6 ; Update stack pointer for a new activation block

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "Put a number: "], null, [OPERATOR. Type operator: variable, value: 5]]
    ASSIGNATION_STRING #-28, #string_id_0, #28

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 5]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-28, #14

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* CALL PROCEDURE *
* Intermediate code => [procedureCall, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    MOVE.L A7, A5
    MOVE.L A6, A7
    MOVE.L A5, -(A7)  ;previous block pointer
    SUB.L #4, A7 ; Memory space for return
    SUB.L #4, A7 ; Memory space for PC
    ADD.L #4, A7
    JSR PROCEDURE_GETINPUTNUM
    ADD.L #4, A7 ;Jump return
    MOVE.L (A7)+, A5 ;Get block pointer
    MOVE.L A7, A6  ;A6 => Stack pointer
    MOVE.L A5, A7 ;Update out block pointer

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: procedure, value: 0], null, [OPERATOR. Type operator: variable, value: 6]]
    RETURN_GET_INTEGER -32

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 6], null, [OPERATOR. Type operator: variable, value: 7]]
    ASSIGNATION_VARIABLE_INTEGER -32, -36

* CALL PROCEDURE *
* Intermediate code => [procedureCall, null, null, [OPERATOR. Type operator: procedure, value: 1]]
    MOVE.L A7, A5
    MOVE.L A6, A7
    MOVE.L A5, -(A7)  ;previous block pointer
    SUB.L #4, A7 ; Memory space for return
    SUB.L #4, A7 ; Memory space for PC
    * ---- BEGIN PARAMETERS  ----*
    CLR.L D0 
    MOVE.L A7, A4 
    MOVE.L A5, A7 
    MOVE.L -36(A7), D0
    MOVE.L A4, A7 
    MOVE.L D0, -(A7) 
    * ---- END PARAMETERS  ----*
    ADD.L #8, A7
    JSR PROCEDURE_GETSQUARE
    ADD.L #4, A7 ;Jump return
    MOVE.L (A7)+, A5 ;Get block pointer
    MOVE.L A7, A6  ;A6 => Stack pointer
    MOVE.L A5, A7 ;Update out block pointer

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: procedure, value: 1], null, [OPERATOR. Type operator: variable, value: 8]]
    RETURN_GET_INTEGER -40

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 8], null, [OPERATOR. Type operator: variable, value: 9]]
    ASSIGNATION_VARIABLE_INTEGER -40, -44

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "Square: "], null, [OPERATOR. Type operator: variable, value: 10]]
    ASSIGNATION_STRING #-60, #string_id_1, #16

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 10]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-60, #8

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 9]]
    OUTPUT_INTEGER -44

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 2]]
    ADD.L #4, A7
    RTS

*Put variables and constants here
buffer ds.b 1024
string_id_0 dc.b 'Put a number: ', 0
string_id_1 dc.b 'Square: ', 0

    END    START
