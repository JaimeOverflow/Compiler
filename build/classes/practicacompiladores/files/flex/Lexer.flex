package practicacompiladores;

import java_cup.runtime.*;
import Exceptions.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

%%

// Public class
%public

// Name of our class
%class ScannerLex

// Enable cup
%cup

// Enable line counter
%line

//Enable column counter
%column

%{
    public static final String TOKENS_OUTPUT_FILENAME = "src/practicacompiladores/files/output/tokens.txt";

    public static ArrayList<TokenLex> tokens = new ArrayList<TokenLex>();

    // Write tokens in a file
    public void writeTokensInFile(ArrayList<TokenLex> tokens) throws IOException {
      BufferedWriter out = new BufferedWriter(new FileWriter(TOKENS_OUTPUT_FILENAME));

      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Date date = new Date();

      out.write("========================= INFO =========================\n");
      out.write("DATE: " + formatter.format(date) + "\n");
      out.write("NUMBER OF TOKENS: " + tokens.size());
      out.write("========================================================\n");
      out.write("======================== TOKENS ========================\n\r");

      for (int i = 0; i < tokens.size(); i++) {
        out.write(tokens.get(i).toString());
      }
      out.write("========================================================");
      out.close();
    }

    // Symbol is a class in cup library
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

// When we finish to process the tokens we must to write all of them in a file
%eof{
  writeTokensInFile(tokens);
%eof}

%eofval{
  return symbol(ParserSym.EOF);
%eofval}

// end option
// Declarations

LETTER          = [a-zA-Z]
DIGIT           = [0-9]

WHITE           = ( " " |\t|\r|\n|\r\n)
COMMENT         = ( "//"[^\n\r]*| "/*".*"*/")

ID              = (({LETTER} | "_")({LETTER}|{DIGIT}| "_" )*)
NUMBER          = ( "0" | [1-9]{DIGIT}*)
TEXT            = \" [^\"]* \"
BOOL            = ("true" | "false")

CONSTANT        = ("const")

INST_IF         = ("if")
INST_ELIF       = ("elif")
INST_ELSE       = ("else")
INST_SWITCH     = ("switch")
INST_CASE       = ("case")
INST_BREAK      = ("break")
INST_DEFAULT    = ("default")

INST_WHILE      = ("while")
INST_FOR        = ("for")

INST_FUNCTION   = ("function")
INST_RETURN     = ("return")
INST_CALL       = ("call")
INST_CALL_MAIN  = ("call_main")
INST_INPUT      = ("input")
INST_OUTPUT     = ("output")

LPAREN          = ("(")
RPAREN          = (")")
LBRACKET        = ("{")
RBRACKET        = ("}")
SEPARATOR       = (",")
TWO_POINTS      = (":")
FINAL_SENTENCE  = ("\.")

OP_RELATIONAL   = ("==" | "!=" | "<" | "<=" | ">" | ">=")
OP_ASSIGN       = ("=")
OP_LOGIC        = ("&&" | "||")
OP_ARITHMETIC   = ( "+" | "-" | "*" | "/" | "%")

%%

//===============================================================
{WHITE}           {/*Ignore*/}
{COMMENT}         {/* Ignore */}
//===============================================================
{INST_IF}         {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_IF, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_if);
                  }
//===============================================================
{INST_ELIF}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_ELIF, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_elif);
                  }
//===============================================================
{INST_ELSE}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_ELSE, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_else);
                  }
//===============================================================
{INST_SWITCH}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_SWITCH, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_switch);
                  }
//===============================================================
{INST_CASE}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_CASE, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_case);
                  }
//===============================================================
{INST_BREAK}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_BREAK, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_break);
                  }
//===============================================================
{INST_DEFAULT}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_DEFAULT, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_default);
                  }
//===============================================================
{INST_WHILE}      {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_WHILE, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_while);
                  }
//===============================================================     
{INST_FOR}        {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_FOR, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_for);
                  }
//===============================================================               
{INST_FUNCTION}   {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_FUNCTION, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_function);
                  }
//===============================================================
{INST_RETURN}     {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_RETURN, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_return);
                  }
//===============================================================
{INST_CALL}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_CALL, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_call);
                  }
//===============================================================
{INST_CALL_MAIN}  {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_CALL_MAIN, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_call_main);
                  }
//===============================================================
{INST_INPUT}      {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_INPUT, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_input);
                  }
//===============================================================
{INST_OUTPUT}     {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.INST_OUTPUT, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.inst_output);
                  }
//===============================================================
{LPAREN}          {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.LPAREN, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.lparen);
                  }
//===============================================================
{RPAREN}          {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.RPAREN, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.rparen);
                  }
//===============================================================
{LBRACKET}        {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.LBRACKET, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.lbracket);
                  }
//===============================================================
{RBRACKET}        {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.RBRACKET, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.rbracket);
                  }
//===============================================================
{SEPARATOR}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.SEPARATOR, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.separator);
                  }
//===============================================================
{TWO_POINTS}      {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.TWO_POINTS, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.two_points);
                  }
//===============================================================
{FINAL_SENTENCE}  {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.FINAL_SENTENCE, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.final_sentence);
                  }
//===============================================================
{OP_RELATIONAL}   {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.OP_RELATIONAL, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.op_relational, lexeme);
                  }
//===============================================================
{OP_ASSIGN}       {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.OP_ASSIGN, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.op_assign);
                  }
//===============================================================
{OP_LOGIC}        {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.OP_LOGIC, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.op_logic, lexeme);
                  }
//===============================================================
{BOOL}      {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.BOOL, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.bool, lexeme);
                  }
//===============================================================
{OP_ARITHMETIC}   {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.OP_ARITHMETIC, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.op_arithmetic, lexeme);
                  }
//===============================================================
{CONSTANT}        {
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.CONSTANT, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.constant);
                  }
//===============================================================
{ID}              {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.ID, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.id, lexeme);
                  }
//===============================================================
{NUMBER}          {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.NUMBER, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.number, lexeme);
                  }
//===============================================================
{TEXT}            {
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.TEXT, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    return symbol(ParserSym.text, lexeme);
                  }
//===============================================================
// Throw an error when we don't match any other regular expression
[^]               { 
                    String lexeme = new String(yytext());
                    TokenLex token = new TokenLex(TokenLex.TOKEN_ID.ERROR, lexeme, yyline, yycolumn);
                    tokens.add(token);

                    try{
                        throw new DoesNotExistException(
                                CompilerException.CompilerErrorType.lexer,
                                "TOKEN: [" + TokenLex.TOKEN_ID.ERROR + "]\nLEXEME: [" + lexeme +"]\nLINE: [" + yyline +"] - COLUMN: [" + yycolumn +"]"
                                
                        );
                    } catch (DoesNotExistException ex) {
                        System.err.println("ERROR: " + ex.getMessage());
                    }

                    return symbol(ParserSym.error, lexeme);
                  }
