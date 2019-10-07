<h1>Compilers 1</h1>

<p>Front-end of the compiler, which is composed of lexical, syntactic and semantic analyzers.</p>
<p>Main features:
<ul>
<li>Basic types: number, text string, logical (Boolean).</li>
<li>Declaration and use of both variables and constants.</li>
<li>Relational, arithmetic, logical and assignment operations.</li>
<li>Conditionals: In this case two conditionals, ‘if’ and ‘switch’.</li>
<li>Loops: ‘while’ and ‘for’.</li>
<li>Keyboard input and output instruction.</li>
<li>Functions or procedures.</li>
</ul>
</p>

<h2>Technologies</h2>
The programming language chosen for practice has been <strong>Java</strong>, which gives us the
versatility and tools to perform the tasks of the compiler.<br><br>
In order to perform the lexical analysis we have used the <strong>‘jflex’</strong> library which
allows to create tokens using patterns (regular expressions), obtain
lexemes if necessary and also takes care of the entire process of
creation of token identification (generation of deterministic finite automaton).
The library is responsible for transpiling a file (with <strong>.flex</strong> extension) into a java file
which deals with the conversion of a source file to token generation.<br><br>
In order to create syntactic and semantic analysis, we will use the <strong>‘cup’</strong> library. With
said library we can generate a grammar and use its semantic routines to
create our semantic analyzer.<br><br>
The <strong>‘cup’</strong> library offers us the <strong>‘Symbol’</strong> class which can be used in the <strong>‘jflex’</strong>
to generate the tokens and this allows us to join the lexicon with the syntax. By
on the other hand, the syntax itself will generate a class with the token identifiers that
we want to create, in our case it is the <strong>ParseSym class</strong> and which we can
use in our <strong>flex</strong> file to link the token identifier of the lexicon with the
syntactic.<br><br>
The symbols table and the error manager are completely made in Java.

<h2> Language </h2>

Our language is a mix between Java and an invented language.

Example 1:

```java
// Function without parameters
function int getInputNum() {
	num : int = input(). // Standard input
	return num.
}

// Function with parameters and return and integer
function int getSquare(num : int) {

	result : int = (num * num). // Arithmetic operation

	return result.
}

// Main function, without parameters and return value.
function none main() {
	output("Put a number: "). // Standard output

	numFact : int = call(getInputNum). // Call function without parameters
	result : int = call(getSquare, numFact). // Call function with parameters

	output("Square: ").
	output(result).
}

call_main(main).
```
Example 2:
```java

function int recursiveFact(num: int) {
	result : int = 0.

	if({num == 1}) {
		result = 1.
	} else {

		decrement : int = (num - 1).
		factResult : int = call(recursiveFact, decrement).
		
		result = (num * factResult).
	}

	return result.
}


function int iterativeFact(num : int, incr : int) { 
	result : int = 1.

	while ({num != 1}) { // Relational operation
		result = ((result * num) * incr). // Multiple arithmetic operations
		num = (num - 1).
	}

	return result.
}


function none main() {
	//result : int = call(iterativeFact, 5, 1).
	result : int = call(recursiveFact, 6).

	output("Result factorial: ").
	output(result).
}

call_main(main).
```

<h1>Compilers 2</h1>

<p>The backend consisting of
the intermediate code, code optimization and assembly code.</p>

It has the ability to process the source code provided in a file
of text. This file will be made from the front-end part of the compiler.
Then proceed to generate the intermediate code, optimize the code if it is
possible and generate the resulting assembly code. It will also generate a series of
files as a result of its execution:
<ul>
<li>Table of variables, procedures and labels.</li>
<li>Intermediate code file corresponding to the program.</li>
<li>File with assembly code not optimized. For each instruction of three
directions a comment will be displayed with the instruction and then the
translation.</li>
<li>File with optimized assembly code.</li>
<li>Errors, in case they are detected, they must be dumped into a file with the
errors detected, indicating the type of error, the line and the type of explanatory message.</li>
</ul>
<br>
The main characteristics that it must have:
<ul>
  <li>Subprogram calls can be made within subprogram.</li>
  <li>Subprogram declarations can be made with parameters.</li>
</ul>

<h2>Assembler code</h2>

Source code:
```java
function none main() {
	num1 : int = 10.
	num2 : int = 5.

	isBigger : boolean = true.

	// Conditional if
	if ({num1 > num2} && isBigger) { // Logical operator
		output("Num1 is bigger").

	} elif ({num1 > num2} || isBigger) { // Logical operator
			output("Num1 must be bigger").
	} else {
			output("Num1 is smaller than num2").
	}

	num : int = (1 + 2 + 5).
	output(num).
}

call_main(main).
```

Destination code:

```assembler
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
```







