/*
 */
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class Graph {
    private final double d = 0.85;

    private List<GraphNode> graphNodes;
    private List<Integer> nodes;
    private Map<Integer, List<Integer>> edges;

    // nodes and graphNodes must have the same indexes for each node
    public Graph(List<Integer> nodes , Map<Integer, List<Integer>> edges, List<GraphNode> graphNodes) {
        this.nodes = nodes;
        this.edges = edges;
        this.graphNodes = graphNodes;
        calculateNodesIn();
    }

    public List<Integer> getGraphNodes() {
        return this.nodes;
    }

    public List<GraphNode> getGraphNodes2(){
        return this.graphNodes;
    }

    public Map<Integer, List<Integer>> getGraphEdges() {
        return this.edges;
    }

    private void calculateNodesIn(){
        List<Integer> listOfOutgoing;
        Integer temp;
        for(Integer node: nodes){
            listOfOutgoing = edges.get(node);
            for(Integer current: listOfOutgoing){
                temp = nodes.indexOf(current);
                graphNodes.get(temp).addNodeIn(node);
            }
        }
    }

    private void calculatePr(){
        Map<Integer, Double> matrix = new HashMap<Integer, Double>();;
        double sumation=0;
        List<Integer> nodesIn;
        int index;
        for(Integer node: nodes){
            index=nodes.indexOf(node);
            nodesIn= graphNodes.get(index).getNodesIn();
            for(Integer current: nodesIn){
                sumation += graphNodes.get(nodes.indexOf(current)).getPr() / edges.get(current).size();
            }

            matrix.put(index,(1-d) + (d*sumation));
            // graphNodes.get(index).setPr( (1-d) + (d*sumation) );

            sumation=0;
        }

        for(int i: matrix.keySet()){
            graphNodes.get(i).setPr(matrix.get(i));
        }

    }

    public GraphNode[] mostInfluence(){
        for(int i=0;i<20;i++){
            calculatePr();
        }

        GraphNode[] ret = new GraphNode[10];
        for(GraphNode node: graphNodes){
            for(int i=0;i<10;i++){
                if(ret[i]==null){
                    ret[i]=node;
                    break;
                }
                else{
                    if(ret[i].getPr()<node.getPr()){
                        if(i==9){
                            ret[i]=node;
                            break;
                        }
                        else{
                            for(int j=9;j>i;j--){
                                ret[j]=ret[j-1];
                            }

                            ret[i]=node;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }
}
