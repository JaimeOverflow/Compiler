package Symbols;

/**
 *
 * @author Jaime
 */
public class SymbolJumpLabel extends SymbolBase{
    
    public String idLabel;
    
    public SymbolJumpLabel(String idLabel) {
        super("SymbolJumpLabel", 0);
        this.idLabel = idLabel;
    }
    
    public SymbolJumpLabel() {
        super("SymbolJumpLabel", 0);
        this.idLabel = "";
    }
    
    @Override
    public String toString(){
        return "Label: " + this.idLabel;
    }
}
