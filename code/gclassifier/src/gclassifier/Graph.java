package gclassifier;

// this program creates the PPI network from the given dataset
import java.util.*;

class Node {

    String label;  //to store the actual number of a node
    int id;    // to store the location of the node in the nodes arraylist
    public ArrayList<Integer> neighbors;  //this contains the indices of the ArrayList "edges" which will give us the neighborhood of every node
    //static HashSet<String> protVal; //this contains the sample names for which the protein takes val 1

    Node() {
        neighbors = new ArrayList();
        //protVal = new HashSet();
    }

    @Override
    public boolean equals(Object u) {
        if (!(u instanceof Node)) {
            return false;
        }
        Node _u = (Node) u;
        return (label == null ? _u.label == null : label.equals(_u.label)) && id == _u.id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.label != null ? this.label.hashCode() : 0);
        hash = 67 * hash + this.id;
        return hash;
    }
}

class Edge {

    String node1;
    String node2;

    Edge() {
    }

    ;
}

public class Graph {

    HashSet<Node> nodes;
    ArrayList<Node> nodes_test;
    ArrayList<Edge> edges;
    //static HashMap<String, Integer> Z = new HashMap();  //this stores the label of each node associated with its position in the nodes list

    Graph() {
        nodes = new HashSet();
        edges = new ArrayList();
        nodes_test = new ArrayList();
    }

    void GraphBuild(String a, String b, HashMap<String, Integer> protid) {

        if (protid.containsKey(a) == true && protid.containsKey(b) == true) {
            Node n1 = new Node();
            n1.label = a;
            n1.id = protid.get(a);
            nodes.add(n1);

            Node n2 = new Node();
            n2.label = b;
            n2.id = protid.get(b);

            nodes.add(n2);

            Edge e1 = new Edge();
            e1.node1 = n1.label;
            e1.node2 = n2.label;
            edges.add(e1);
            int s = edges.indexOf(e1);
            n1.neighbors.add(s);
            Edge e2 = new Edge();  //the same edge is added again from the perspective of node2
            e2.node1 = n2.label;
            e2.node2 = n1.label;
            edges.add(e2);
            int t = edges.indexOf(e2);
            n2.neighbors.add(t);

        }
        /*int checka = 0; int checkb = 0;int index1=0; int index2=0;
        //System.out.println("test");
        int num = nodes.size();

        if(num>0){
        for(int i = 0; i< num; i++){
        if(nodes.get(i).label == null ? a == null : nodes.get(i).label.equals(a)){
        checka = 1;
        index1 = i;
        //System.out.println("test1");

        }
        if(nodes.get(i).label == b){
        checkb = 1;
        index2 = i;
        //System.out.println("test2");
        }

        }
        }

        if(checka == 0){//System.out.println("test1");
        // if (c.protid.containsKey(a) == true){     //if the proteins a and b are in the training set, then it is included in the graph
        Node n1 = new Node();
        //System.out.println("testing a");
        n1.label = a; n1.id = c.protid.get(a);
        nodes.add(n1); //System.out.println("nodes size" + nodes.size());
        index1 = nodes.size()-1;

        }

        if(checkb == 0){
        // System.out.println("test2");
        //if(c.protid.containsKey(b) == true){
        Node n2 = new Node();
        // System.out.println("testing b");

        n2.label = a; n2.id = c.protid.get(b);
        nodes.add(n2);
        index2 = nodes.size()-1;

        }

        //System.out.println("test3");
        Node n1 = nodes.get(index1); Node n2 = nodes.get(index2);
        //System.out.println("testing a and b");
        Edge e1 = new Edge();
        e1.node1 = n1.label; e1.node2 = n2.label;
        edges.add(e1);
        int s = edges.indexOf(e1);
        n1.neighbors.add(s);
        Edge e2 = new Edge();  //the same edge is added again from the perspective of node2
        e2.node1 = n2.label; e2.node2 = n1.label;
        edges.add(e2);
        int t = edges.indexOf(e2);
        n2.neighbors.add(t);




        //System.out.println("finished graphbuild");*/
    }
    /*  public static void main(String[] args) throws IOException{
    String X = new String();
    String[] Y = new String[2];
    Graph g = new Graph();
    c = new Controller();

    String filename = "/home/kasturi/notun/network_constrained_dtree/PPInetwork/PPI.txt";



    try{


    BufferedReader br = new BufferedReader(new FileReader(filename));

    while(br.ready())
    {
    X = br.readLine();
    Y = X.split("\t");

    String A = Y[0].trim();  String B = Y[1].trim();


    g.GraphBuild(A,B);


    }

    }catch(Exception e){System.out.println(e);}

    //writing to a file
    String filename2 = "/home/kasturi/notun/network_constrained_dtree/PPInetwork/PPIout_new.txt";



    try{
    BufferedWriter br2 = new BufferedWriter(new FileWriter(filename2));
    for(int i = 0; i < g.nodes.size(); i++){
    br2.write((g.nodes.get(i)).label);
    br2.write("\t");
    int t = (g.nodes.get(i)).neighbors.size();
    for(int j = 0; j< t; j++){
    int w = (g.nodes.get(i)).neighbors.get(j);
    String r = (g.edges.get(w)).node2;
    br2.write(String.valueOf(r));
    br2.write("\t");

    // System.out.println(String.valueOf(c.nodes.get(i).label) + "\t" + String.valueOf(r));
    }br2.newLine();
    }
    br2.close();
    }catch(Exception e){System.out.println(e);}

    System.out.println("number of nodes " + g.nodes.size());
    }*/
}
