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
* Intermediate code => [procedureCallMain, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    JSR PROCEDURE_MAIN
    SIMHALT

* INITIAL LABEL (PROCEDURE) *
* Intermediate code => [procedureName, null, null, [OPERATOR. Type operator: procedure, value: 0]]
PROCEDURE_MAIN:

* PREAMBLE (PROCEDURE) *
* Intermediate code => [procedurePreamble, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    SUB.L #0, A7
    SUB.L #4, A7 ; Block pointer
    MOVE.L #0, (A7)
    MOVE.L A7, A6
    SUB.L #164, A6 ; Update stack pointer for a new activation block

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: int_value, value: 10], null, [OPERATOR. Type operator: variable, value: 1]]
    ASSIGNATION_INTEGER #10, -8

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: int_value, value: 5], null, [OPERATOR. Type operator: variable, value: 3]]
    ASSIGNATION_INTEGER #5, -16

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: bool_value, value: true], null, [OPERATOR. Type operator: variable, value: 5]]
    ASSIGNATION_BOOLEAN #1, -20

* COMPARISON OPERATION: greater *
* Intermediate code => [greater, [OPERATOR. Type operator: int_value, value: 1], [OPERATOR. Type operator: int_value, value: 3], [OPERATOR. Type operator: variable, value: 6]]
    COMPARISON_GREATER -22, -8, -16

* False condition *
* Intermediate code => [condFalse, [OPERATOR. Type operator: variable, value: 6], null, [OPERATOR. Type operator: label, value: 0]]
    MOVE.W -22(A7), D0
    CMP.W #0, D0
    BEQ LABEL_0

* LOGICAL OPERATION: and *
* Intermediate code => [and, [OPERATOR. Type operator: variable, value: 5], null, [OPERATOR. Type operator: variable, value: 6]]
    LOGICAL_OPERATION_AND -22, -20

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 0]]
LABEL_0:

* True condition *
* Intermediate code => [condTrue, [OPERATOR. Type operator: variable, value: 6], null, [OPERATOR. Type operator: label, value: 1]]
    MOVE.W -22(A7), D0
    CMP.W #1, D0
    BEQ LABEL_1

* False condition *
* Intermediate code => [condFalse, [OPERATOR. Type operator: variable, value: 6], null, [OPERATOR. Type operator: label, value: 2]]
    MOVE.W -22(A7), D0
    CMP.W #0, D0
    BEQ LABEL_2

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 1]]
LABEL_1:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "Num1 is bigger"], null, [OPERATOR. Type operator: variable, value: 7]]
    ASSIGNATION_STRING #-50, #string_id_0, #28

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 7]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-50, #14

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* JUMP (LABEL) *
* Intermediate code => [jump, null, null, [OPERATOR. Type operator: label, value: 7]]
    JMP LABEL_7

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 2]]
LABEL_2:

* COMPARISON OPERATION: greater *
* Intermediate code => [greater, [OPERATOR. Type operator: int_value, value: 1], [OPERATOR. Type operator: int_value, value: 3], [OPERATOR. Type operator: variable, value: 8]]
    COMPARISON_GREATER -52, -8, -16

* True condition *
* Intermediate code => [condTrue, [OPERATOR. Type operator: variable, value: 8], null, [OPERATOR. Type operator: label, value: 4]]
    MOVE.W -52(A7), D0
    CMP.W #1, D0
    BEQ LABEL_4

* LOGICAL OPERATION: or *
* Intermediate code => [or, [OPERATOR. Type operator: variable, value: 5], null, [OPERATOR. Type operator: variable, value: 8]]
    LOGICAL_OPERATION_OR -52, -20

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 4]]
LABEL_4:

* True condition *
* Intermediate code => [condTrue, [OPERATOR. Type operator: variable, value: 8], null, [OPERATOR. Type operator: label, value: 5]]
    MOVE.W -52(A7), D0
    CMP.W #1, D0
    BEQ LABEL_5

* False condition *
* Intermediate code => [condFalse, [OPERATOR. Type operator: variable, value: 8], null, [OPERATOR. Type operator: label, value: 6]]
    MOVE.W -52(A7), D0
    CMP.W #0, D0
    BEQ LABEL_6

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 5]]
LABEL_5:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "Num1 must be bigger"], null, [OPERATOR. Type operator: variable, value: 9]]
    ASSIGNATION_STRING #-90, #string_id_1, #38

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 9]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-90, #19

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 3]]
LABEL_3:

* JUMP (LABEL) *
* Intermediate code => [jump, null, null, [OPERATOR. Type operator: label, value: 7]]
    JMP LABEL_7

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 6]]
LABEL_6:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "Num1 is smaller than num2"], null, [OPERATOR. Type operator: variable, value: 10]]
    ASSIGNATION_STRING #-140, #string_id_2, #50

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 10]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-140, #25

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 7]]
LABEL_7:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: int_value, value: 8], null, [OPERATOR. Type operator: variable, value: 15]]
    ASSIGNATION_INTEGER #8, -160

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 15], null, [OPERATOR. Type operator: variable, value: 16]]
    ASSIGNATION_VARIABLE_INTEGER -160, -164

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 16]]
    OUTPUT_INTEGER -164

* PRINT NEW LINE *
    PRINT_NEW_LINE #buffer 

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    ADD.L #4, A7
    RTS

*Put variables and constants here
buffer ds.b 1024
string_id_0 dc.b 'Num1 is bigger', 0
string_id_1 dc.b 'Num1 must be bigger', 0
string_id_2 dc.b 'Num1 is smaller than num2', 0

    END    START
