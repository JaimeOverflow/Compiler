/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SymbolsTable;

/**
 *
 * @author Jaime
 */
public class TypeDescription {
    
    public static enum CONTENT_DESCRIPTION {
        idnull,
        dtype,
        dconst,
        dvar,
        dfunc,
        darg
    }
    
    public static enum BASIC_SUBJACENT_TYPE {
        ts_boolean,
        ts_integer,
        ts_string,
        ts_none
    }
    
    // For dtype
    public CONTENT_DESCRIPTION contentDescription;
    public BASIC_SUBJACENT_TYPE basicSubjacentType;
    public int size;
    public int lowerLimit;
    public int higherLimit;
    
    // For dvar, dproc, darg
    public String nameType;
    
    // Only for constants (dconst)
    public Object value;
    
    // dtype
    public TypeDescription(CONTENT_DESCRIPTION contentDescription, BASIC_SUBJACENT_TYPE basicSubjacentType, int size, int lowerLimit, int higherLimit) {
        this.contentDescription = contentDescription;
        this.basicSubjacentType = basicSubjacentType;
        this.size = size;
        this.lowerLimit = lowerLimit;
        this.higherLimit = higherLimit;
    }
    
    // dvar, dproc, darg
    public TypeDescription(CONTENT_DESCRIPTION contentDescription, String typeName) {
        this.contentDescription = contentDescription;
        this.nameType = typeName;
    }
    
    // dconst
    public TypeDescription(CONTENT_DESCRIPTION contentDescription, String typeName, Object value) {
        this.contentDescription = contentDescription;
        this.nameType = typeName;
        this.value = value;

    }
        @Override
    public String toString() {

        String result = "{"
                + "TypeDescription Item, content description: " + this.contentDescription;
        
        if (this.contentDescription == CONTENT_DESCRIPTION.dtype) {
                result += ", basic subjacent type: " + this.basicSubjacentType
                + ", size: " + this.size
                + ", lower limit: " + this.lowerLimit
                + ", higher limit: " + this.higherLimit;
                
        } else if (this.contentDescription == CONTENT_DESCRIPTION.dfunc
                || this.contentDescription == CONTENT_DESCRIPTION.darg
                || this.contentDescription == CONTENT_DESCRIPTION.dvar
                ) {
            result += ", name of type: " + this.nameType;
        } else if (this.contentDescription == CONTENT_DESCRIPTION.dconst) {
            result += ", name of type: " + this.nameType;
            
            if (this.nameType.equals("int")) {
                Integer value = (Integer) this.value;
                result += ", value: " + value;
            }
            
            if (this.nameType.equals("string")) {
                String value = (String) this.value;
                result += ", value: " + value;
            }
                        
            if (this.nameType.equals("boolean")) {
                Boolean value = (Boolean) this.value;
                result += ", value: " + value;
            }
                
               
        }
        result += "}";
        
        return result;
    }
    
}
