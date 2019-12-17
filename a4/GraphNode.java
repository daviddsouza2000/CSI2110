/*
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GraphNode {
    private int number;
    private double pr;
    private List<Integer> nodesIn;

    public GraphNode(int number){
        this.number = number;
        pr=1.0;
        nodesIn = new ArrayList<Integer>();
    }

    public int getNumber(){
        return number;
    }

    public double getPr(){
        return pr;
    }

    public void setPr(double pr){
        this.pr = pr;
    }

    public void addNodeIn(Integer node){
        nodesIn.add(node);
    }

    public List<Integer> getNodesIn(){
        return nodesIn;
    }

    public int getNumberOfNodesIn(){
        return nodesIn.size();
    }

}
