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
    SIMHALT
* -------------------------------------------------------- *
* INITIAL LABEL FOR A NEW PROCEDURE *
* Intermediate code => [procedureName, null, null, [OPERATOR. Type operator: procedure, value: 0]]
E_MAIN:
