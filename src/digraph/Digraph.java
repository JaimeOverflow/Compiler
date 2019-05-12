/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaime
 */
public class Digraph {
    
    public static final String DIGRAPH_OUTPUT_FILENAME = "src/practicacompiladores/files/output/digraph.dot";
    
    public ArrayList<NodeGraph> nodes;
    public Digraph(){
        nodes = new ArrayList<NodeGraph>();
    }
    
    public void addNode(NodeGraph node, ArrayList<NodeGraph.VARIABLES> childs) {
 
        for (int i = 0; i < childs.size(); i++) {
            for (int j = nodes.size() - 1; j >= 0; j--) {
                NodeGraph aux = nodes.get(j);
                if (childs.get(i) == aux.variable) {
                    node.childs.add(nodes.get(j));
                    nodes.remove(j);
                    break;
                }
            }
        }

        nodes.add(node);
    }
    
    public void addNode(NodeGraph node) {
        nodes.add(node);
    }
    
    public void generateDotFile() {
        NodeGraph root = nodes.get(0);

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(DIGRAPH_OUTPUT_FILENAME));
            String result = "digraph G { " + generateContentDot(root) + " }";
            
            out.write(result);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Digraph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String generateContentDot(NodeGraph node) {
        String result = "";
        
        if (node.childs != null) {
            for (int i = 0; i < node.childs.size(); i++) {
                result += "\"" + node.description + "\" -> \"" + node.childs.get(i).description + "\"\n";
                result += generateContentDot(node.childs.get(i));
            }
            
        }
        return result;
    }
    
}
