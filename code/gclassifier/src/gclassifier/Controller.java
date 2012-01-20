package gclassifier;

import java.util.*;
import java.io.*;
import java.io.FileReader;

public class Controller {

    Controller() {
    }

    ;
    static HashMap<String, Integer> protid;  //this is created and updated to get the number of proteins to work with
    static HashMap<String, Boolean> SampleBinary;   //to store class value true or false for every sample
    static ArrayList<ArrayList<String>> temp;   //this reads the entire file nto an arraylist of arraylists, this is used just once in th beginning
    static HashMap<String, Integer> sampleid;  //to store the index of every sample
    static boolean[][] storeFile;//to store the whole file in this array, this is what will be used henceforth

    public static void main(String[] args) {
//        String file1 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/ProteinExp.txt";
//        String file2 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/SampletoBinary.txt";

//        String file1 = "/media/Entertain/Code/GSE6988/pe_test.txt";
//        String file2 = "/media/Entertain/Code/GSE6988/samp_test.txt";
        String file1 = "/media/Entertain/Code/GSE6988/ProteinExp.txt";
        String file2 = "/media/Entertain/Code/GSE6988/SampletoBinary.txt";

        Controller c = new Controller();
        protid = new HashMap();

        SampleBinary = new HashMap();

        temp = new ArrayList<ArrayList<String>>();

        sampleid = new HashMap();

        Boolean b1 = true;
        Boolean b2;

        int linenum = 0; //to keep track of the number of rows in the file (which is equal to the number of samples)

        try {

            BufferedReader br1 = new BufferedReader(new FileReader(file1));

            int count = 1;
            while (br1.ready()) {
                String[] X = br1.readLine().split("\t");
                ArrayList<String> temp1 = new ArrayList();

                for (int i = 0; i < X.length - 1; i++) {

                    temp1.add(X[i].trim());
//                    if (protid.isEmpty()) {
//                        protid.put(X[i + 1].trim(), 1);
//                    } else
                    if (protid.containsKey(X[i + 1].trim()) == false) {// || protid.get(X[i + 1].trim()) == null) {
                        protid.put(X[i + 1].trim(), count++);
                    }

                }
                temp1.add(X[X.length - 1]);  //adding the last protein
                linenum++;
                temp.add(temp1);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        //System.out.println("temp 0th cell" + temp.get(0).get(0));

        int numprot = protid.size();   //number of proteins
        System.out.println("num of proteins " + numprot);
        /*for(int i = 0; i<numsamples; i++)
        System.out.println("proteins " + protid.get(i));*/

        try {
            BufferedReader br = new BufferedReader(new FileReader(file2));
            while (br.ready()) {
                String X = br.readLine();
                String[] Y = X.split("\t");
                if (Y[1].trim().equals("0")) {
                    b1 = false;
                } else if (Y[1].trim().equals("1")) {
                    b1 = true;
                }
                SampleBinary.put(Y[0].trim(), b1);

            }
        } catch (Exception e) {
            System.out.println(e);
        }


        storeFile = new boolean[linenum][protid.size() + 1];


        for (int i = 0; i < linenum; i++) {
            for (int j = 0; j < numprot; j++) {
                storeFile[i][j] = false;   //initializing all protein expression values to false
            }
        }

        /* System.out.println("checking num of samples " + linenum);
        System.out.println("checking num of samples AGAIN " + SampleBinary.size()); */



        //filling up storeFile:

        for (int i = 0; i < linenum; i++) {
            String Samp = temp.get(i).get(0); //getting sample name

            sampleid.put(Samp, i); //recording the samplename and the index in which it will be stored
            storeFile[i][0] = SampleBinary.get(Samp);  //the class labels
            for (int j = 0; j < temp.get(i).size() - 1; j++) {
                String prot = temp.get(i).get(j + 1);  //getting the 1 vlue proteins for this sample
                System.out.println(String.valueOf(protid.get(prot)));
                int k = protid.get(prot);  //getting the index of this protein

                storeFile[i][k] = true;
            }

        }
        /* for(int i =0; i<linenum; i++){
        if((temp.get(i)).get(0).equals(String.valueOf(0)))
        storeFile[i][0] = false;
        else  storeFile[i][0] = true;


        // storeFile[i][0] = Boolean.parseBoolean((temp.get(i)).get(0));
        for(int j = 0; j< temp.size(); j++){
        b2 = SampleBinary.get(temp.get(i));
        if (b2!=null)
        storeFile[i][1] = b2;
        String S = (temp.get(i)).get(j+1);  //get the name of the protein
        int l = protid.get(S);   //get the index number of the protein where you should store i in the storefile 2D array
        storeFile[i][l] = true;

        }
        }*/

        System.out.println("number of proteins to work with " + protid.size());


        /* for(int i = 0; i<linenum; i++){int count = 0; System.out.print("for the "+ i + "th sample");
        for(int j =0; j<protid.size(); j++){
        if(storeFile[i][j]==true){
        count++;  System.out.print("proteins:  " + j + "\t");
        }

        //System.out.print((storeFile[i][j]).toString() + "\t");

        } System.out.println();System.out.println("number of trues for  " + i + "th sample "+ count);
        }*/



        //building the PPI network

        Graph g = new Graph();

        String filename = "/home/kasturi/notun/network_constrained_dtree/PPInetwork/PPI.txt";

        try {


            BufferedReader br = new BufferedReader(new FileReader(filename));

            while (br.ready()) {
                String X = br.readLine();
                String[] Y = X.split("\t");

                String A = Y[0].trim();
                String B = Y[1].trim();

                if (protid.containsKey(A) && protid.containsKey(B)) {
                    g.GraphBuild(A, B);
                }
                //System.out.println("One run of graph building completed");

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        //writing to a file
       /* String filename2 = "/home/kasturi/notun/network_constrained_dtree/PPInetwork/PPIout_new.txt";
        


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
        }catch(Exception e){System.out.println(e);} */

        System.out.println("number of nodes in nodes list" + g.nodes.size());



        //decision tree implementation
        /*  Decision_tree d = new Decision_tree();

        double Gain = 1;

        ArrayList<Integer> sampleIndex = new ArrayList();

        for(int i = 0; i< linenum;i++){
        sampleIndex.add(i);
        }

        String file3 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/Classification.txt";

        d.BuildDecTree(sampleIndex, file3);*/

        /* System.out.println("storeFile " + c.storeFile[0].length);
        System.out.println("storeFile " + linenum);*/

        Tree t = new Tree();

        Decision_tree d = new Decision_tree(storeFile);
        ArrayList<Integer> sampleIndex = new ArrayList();

        for (int i = 0; i < linenum; i++) {
            sampleIndex.add(i);
        }

        System.out.println("sampleIndex " + sampleIndex.size());

        String file3 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/Classification_test.txt";

        d.BuildDecTree(sampleIndex, file3, t, c);

        t.InOrderTraversal(t.rootNode);


    }
}
    

