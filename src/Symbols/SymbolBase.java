package Symbols;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

/**
 * Assignatura 21742 - Compiladors I 
 * Estudis: Grau en Informàtica 
 * Itinerari: Computació 
 * Curs: 2018-2019
 *
 * Professor: Pere Palmer
 */


/**
 * Classe que implementa la classe base a partir de la que s'implementen totes
 * les varaibles de la gramàtica.
 * 
 * Bàsicament conté un valor enter
 * 
 * @author Pere Palmer
 */
public class SymbolBase extends Symbol {
    private static int idAutoIncrement = 0;
    
    public SymbolBase(String variable, Integer valor) {
        super(valor, variable);
    }
 }
