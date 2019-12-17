/*
 *
 *
 * This is the main file to be run
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


/**
 *
 */
public class A4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String edgesFilename = "email-dnc.edges";
        Graph                       graph = readGraph(edgesFilename);
        List<Integer>               nodes = graph.getGraphNodes();
        List<GraphNode>               nodes2 = graph.getGraphNodes2();
        Map<Integer, List<Integer>> edges = graph.getGraphEdges();

        System.out.println("Number of nodes in the Graph: " + nodes.size());

        // for(Integer node : nodes) {
        //     System.out.println("Node number: " + node);
        //     System.out.print("Adjacent Nodes: ");
        //     if (edges.containsKey(node)) {
        //         for(Integer edge : edges.get(node)) {
        //             System.out.print(edge + " ");
        //         }
        //     }
        //     System.out.println();
        //     System.out.println("------------------------------------");
        //
        // }
        GraphNode[] greatest = graph.mostInfluence();
        System.out.println("------------------------------------");
        System.out.println("The 10 most influential nodes in this network are: ");
        for(GraphNode node: greatest){
            System.out.println(node.getNumber() + "\tpr: " + node.getPr());
        }

        PrintWriter writer = new PrintWriter("test.txt", "UTF-8");
        for(GraphNode node: graph.getGraphNodes2()){
            writer.println(node.getNumber() + "\tpr: " + node.getPr());
        }
        writer.println("------------------------------------");
        writer.println("Number of nodes in the Graph: " + nodes.size());
        writer.println("------------------------------------");
        writer.println("The 10 most influential nodes in this network are: ");
        for(GraphNode node: greatest){
            writer.println(node.getNumber() + "\tpr: " + node.getPr());
        }
        writer.close();

    }

    private static Graph readGraph(String edgesFilename) throws FileNotFoundException, IOException {
        System.getProperty("user.dir");
        URL edgesPath = A4.class.getResource(edgesFilename);
        BufferedReader csvReader = new BufferedReader(new FileReader(edgesPath.getFile()));
        String row;
        List<Integer>               nodes = new ArrayList<Integer>();
        Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>();

        boolean first = false;
        while ((row = csvReader.readLine()) != null) {
            if (!first) {
                first = true;
                continue;
            }

            String[] data = row.split(",");

            Integer u = Integer.parseInt(data[0]);
            Integer v = Integer.parseInt(data[1]);

            if (!nodes.contains(u)) {
                nodes.add(u);
            }
            if (!nodes.contains(v)) {
                nodes.add(v);
            }

            if (!edges.containsKey(u)) {
                // Create a new list of adjacent nodes for the new node u
                List<Integer> l = new ArrayList<Integer>();
                l.add(v);
                edges.put(u, l);
            } else {
                edges.get(u).add(v);
            }
        }

        for (Integer node : nodes) {
            if (!edges.containsKey(node)) {
                edges.put(node, new ArrayList<Integer>());
            }
        }

        csvReader.close();
        return new Graph(nodes,edges,convertNodeList(nodes));
    }

    private static List<GraphNode> convertNodeList(List<Integer> nodes){
        List<GraphNode> graphNodes = new ArrayList<GraphNode>();
        for(Integer node: nodes){
            graphNodes.add(new GraphNode(node));
        }
        return graphNodes;
    }

}
