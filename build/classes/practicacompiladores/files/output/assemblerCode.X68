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
    SUB.L #4, A6 ; Update stack pointer for a new activation block

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: bool_value, value: true], null, [OPERATOR. Type operator: variable, value: 0]]
    ASSIGNATION_BOOLEAN #1, -2

* ASSIGNATION VARIABLE *
* Intermediate code => [assign, [OPERATOR. Type operator: variable, value: 0], null, [OPERATOR. Type operator: variable, value: 1]]
    ASSIGNATION_VARIABLE_BOOLEAN -2, -4

* STANDARD OUTPUT *
* Intermediate code => [standardOutput, null, null, [OPERATOR. Type operator: variable, value: 1]]
    OUTPUT_BOOLEAN -4

* PRINT BUFFER *
    PRINT_BUFFER #buffer, #0 

* PREAMBLE END (PROCEDURE) *
* Intermediate code => [procedureEnd, null, null, [OPERATOR. Type operator: procedure, value: 0]]
    ADD.L #4, A7
    RTS

*Put variables and constants here
buffer ds.b 1024
;variables para mensajes 
informacion dc.b 'Introduzca un texto que no supere los 1024 caracteres:',0  
errorstring dc.b 'ERROR, La longitud del texto supera los 1024 carcateres',0 

    END    START
