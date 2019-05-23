/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Symbols;

import SymbolsTable.TypeDescription;
import SymbolsTable.TypeDescription.BASIC_SUBJACENT_TYPE;
import SymbolsTable.TypeDescription.CONTENT_DESCRIPTION;

/**
 *
 * @author Jaime
 */
public class SymbolValue extends SymbolBase{
    
    public CONTENT_DESCRIPTION contentDescription;
    public BASIC_SUBJACENT_TYPE basicSubjacentType;
    
    public String nameType;
    public boolean isConstant;
    public Object valueType;
    
    public String idVariable;
    
    public boolean isString = false;
    public int stringSize = 0; // We only fill this field if isString is true
    
    public SymbolValue(TypeDescription.BASIC_SUBJACENT_TYPE basicSubjacentType) {
        super("value", 0);
        this.contentDescription = CONTENT_DESCRIPTION.idnull;
        this.basicSubjacentType = basicSubjacentType;
        this.isConstant = false;
    }
    
    public SymbolValue(TypeDescription.BASIC_SUBJACENT_TYPE basicSubjacentType, Object valueType) {
        super("value", 0);
        this.contentDescription = CONTENT_DESCRIPTION.idnull;
        this.basicSubjacentType = basicSubjacentType;
        this.isConstant = true;
        this.valueType = valueType;
    }
    
    
    public SymbolValue(CONTENT_DESCRIPTION contentDescription, String nameType) {
        super("value", 0);
        this.contentDescription = contentDescription;
        this.nameType = nameType;
        this.isConstant = false;
    }
    
        public SymbolValue(CONTENT_DESCRIPTION contentDescription, String nameType, Object valueType) {
        super("value", 0);
        this.contentDescription = contentDescription;
        this.nameType = nameType;
        this.isConstant = true;
        this.valueType = valueType;
    }

}
