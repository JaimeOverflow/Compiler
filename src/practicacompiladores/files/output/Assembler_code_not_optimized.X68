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
    SUB.L #24, A6 ; Update stack pointer for a new activation block

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: bool_value, value: true], null, [OPERATOR. Type operator: variable, value: 0]]
    ASSIGNATION_BOOLEAN #1, -2

* True condition *
* Intermediate code => [condTrue, [OPERATOR. Type operator: variable, value: 0], null, [OPERATOR. Type operator: label, value: 1]]
    MOVE.W -2(A7), D0
    CMP.W #1, D0
    BEQ LABEL_1

* False condition *
* Intermediate code => [condFalse, [OPERATOR. Type operator: variable, value: 0], null, [OPERATOR. Type operator: label, value: 2]]
    MOVE.W -2(A7), D0
    CMP.W #0, D0
    BEQ LABEL_2

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 1]]
LABEL_1:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "hola"], null, [OPERATOR. Type operator: variable, value: 1]]
    ASSIGNATION_STRING #-10, #string_id_0, #8

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 1]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-10, #4

* PRINT BUFFER *
    PRINT_BUFFER #buffer, #0 

* JUMP (LABEL) *
* Intermediate code => [jump, null, null, [OPERATOR. Type operator: label, value: 3]]
    JMP LABEL_3

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 2]]
LABEL_2:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: bool_value, value: true], null, [OPERATOR. Type operator: variable, value: 2]]
    ASSIGNATION_BOOLEAN #1, -12

* True condition *
* Intermediate code => [condTrue, [OPERATOR. Type operator: variable, value: 2], null, [OPERATOR. Type operator: label, value: 5]]
    MOVE.W -12(A7), D0
    CMP.W #1, D0
    BEQ LABEL_5

* False condition *
* Intermediate code => [condFalse, [OPERATOR. Type operator: variable, value: 2], null, [OPERATOR. Type operator: label, value: 6]]
    MOVE.W -12(A7), D0
    CMP.W #0, D0
    BEQ LABEL_6

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 5]]
LABEL_5:

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "adios"], null, [OPERATOR. Type operator: variable, value: 3]]
    ASSIGNATION_STRING #-22, #string_id_1, #10

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 3]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-22, #5

* PRINT BUFFER *
    PRINT_BUFFER #buffer, #0 

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
* Intermediate code => [assign, [OPERATOR. Type operator: string_value, value: "3"], null, [OPERATOR. Type operator: variable, value: 4]]
    ASSIGNATION_STRING #-24, #string_id_2, #2

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 4]]
    CLR.L buffer  
    PRINT_STRING #buffer, #-24, #1

* PRINT BUFFER *
    PRINT_BUFFER #buffer, #0 

* SKIP (LABEL) *
* Intermediate code => [skip, null, null, [OPERATOR. Type operator: label, value: 7]]
LABEL_7:

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    ADD.L #4, A7
    RTS

*Put variables and constants here
buffer ds.b 1024
string_id_0 dc.b 'hola', 0
string_id_1 dc.b 'adios', 0
string_id_2 dc.b '3', 0

    END    START
