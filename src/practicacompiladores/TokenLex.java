/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicacompiladores;

/**
 *
 * @author Jaime
 */
public class TokenLex {
    public enum TOKEN_ID {
        ERROR,
        ID,              
        NUMBER,         
        TEXT,
        BOOL,    
        CONSTANT,        
        INST_IF,
        INST_ELIF,
        INST_ELSE,
        INST_SWITCH,
        INST_CASE,
        INST_BREAK,
        INST_DEFAULT,
        INST_WHILE,
        INST_FOR,
        INST_FUNCTION,  
        INST_CALL,       
        INST_RETURN,    
        INST_INPUT,      
        INST_OUTPUT,
        LPAREN,
        RPAREN,
        LBRACKET,
        RBRACKET, 
        SEPARATOR, 
        TWO_POINTS,
        FINAL_SENTENCE,
        OP_RELATIONAL,
        OP_ASSIGN,
        OP_LOGIC,   
        OP_ARITHMETIC
    }
    
    private TOKEN_ID tokenId;
    private String lexeme;
    private boolean hasLexeme;
    private int line;
    private int column;
    
    public TokenLex(TOKEN_ID tokenId, int line, int column) {
        
        this.tokenId = tokenId;
        this.line = line;
        this.column = column;
        this.hasLexeme = false;
    }
    
    public TokenLex(TOKEN_ID tokenId, String lexeme,  int line, int column) {
        this.tokenId = tokenId;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
        this.hasLexeme = true;
    }
    
    @Override
    public String toString(){
        
        String result = "TOKEN: [" + this.tokenId + "]\n";
        
        if (this.hasLexeme){
            result += "LEXEME: [" + this.lexeme + "]\n";
        }
        
        result += "LINE: [" + this.line + "] - COLUMN: [" + this.column + "]\n\r";
        
        return result;
    }
    

}