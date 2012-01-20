package gclassifier;

//this program first selects only the genes which can be mapped to protein
// then it outputs a list of all samples and proteins which have a value of 0 or 1, depending on whether they were amongst the top 25% of the highly expressed ones
import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;

class SampleExp {

    String label;
    ArrayList<String> geneExp = new ArrayList();
    ArrayList<String> geneName = new ArrayList();
    // ArrayList<String> binExp = new ArrayList(); ;  //this stores a 0 or a 1 corrsp to every gene

    SampleExp() {
    }
}

public class GenetoExpression {

    static ArrayList<SampleExp> samples = new ArrayList();

    GenetoExpression() {
    }

    static void StoreSample(String a) {
        SampleExp s = new SampleExp();
        s.label = a;  //the sample name to be stored
        samples.add(s);

    }

    public static void main(String[] args) {

        String File1 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/genetoprot.txt";   //the file contains the mapping from gene to protein
        String File2 = "/home/kasturi/notun/network_constrained_dtree/PPInetwork/protidfinal.txt";  //name of protein and corrp int id
        String file1 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/SamplesGenes.txt";// the entire file with samples, and the gene expr values
        String file2 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/ProteinExp.txt";  //to write to

        String W;
        String X;
        String[] Y;
        SampleExp t = new SampleExp();

        HashMap<String, String> protid = new HashMap();  //stores the name of the protein and its id
        HashMap<String, String> genes = new HashMap();  //stores the genes and the corrsp protein id which we are to select

        try {
            int counter = 0;
            BufferedReader x = new BufferedReader(new FileReader(File2));
            while (x.readLine() != null) {
                String[] A = x.readLine().split("\t");
                if (protid.containsKey(A[0].trim()) == false) {
                    if (A[1].trim().equals("") == false) {
                        protid.put(A[0].trim(), A[1].trim());
                        counter++;
                    }
                }
            }
            System.out.println("counter" + counter);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(File1));
            while (br.readLine() != null) {
                String[] Z = br.readLine().split("\t");  //Z[0] will have the gene name, Z[1] the protein name
                if (protid.containsKey(Z[1].trim()) == true) {
                    genes.put(Z[0].trim(), protid.get(Z[1].trim()));
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(file1));

            int line = 0;

            while ((W = br1.readLine()) != null) {
                X = W.trim();
                Y = X.split("\t");

                line++;
                if (line == 1) {
                    for (int i = 0; i < (Y.length - 1); i++) {
                        if ((String.valueOf(Y[i + 1].trim())).equals(null) == false) {
                            StoreSample(String.valueOf(Y[i + 1].trim()));  //creating

                        } else {
                            StoreSample("noname");

                            System.out.println("empty string");
                        }
                    }

                } else if (line > 1) {

                    if (genes.containsKey(Y[0].trim()) == true) {  //checking if the gene is to be selected
                        for (int i = 1; i < (Y.length); i++) {
                            t = samples.get(i - 1);

                            t.geneName.add(genes.get(Y[0].trim()));  //storing the corresp protein id
                            if (String.valueOf(Y[i]).equals("")) { //System.out.println("gene val " + Y[i].trim());
                                t.geneExp.add("0");
                                // t.binExp.add("0");  //setting a default value of 0 to every element of binExp
                            } else {
                                t.geneExp.add(Y[i].trim());  //storing the value of the gene
                                // t.binExp.add("0");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        t = samples.get(0);
        int numGenes = t.geneName.size(); //the total number of genes that there are
        int req = (int) (0.25 * (numGenes));  //the number required for the gene to be given a 1 for its expression

        for (int y = 0; y < numGenes; y++) {
            System.out.println("prots " + t.geneName.get(y));
        }
        System.out.println("num of samples" + samples.size());
        System.out.println("num of genes" + numGenes);
        System.out.println("req " + req);

        try {

            BufferedWriter br2 = new BufferedWriter(new FileWriter(file2));
            long time = System.currentTimeMillis();
            for (int i = 0; i < samples.size(); i++) {

                t = samples.get(i);

                PriorityQueue<GeneEntry> geneSort = new PriorityQueue();

                for (int j = 0; j < t.geneName.size(); j++) {
                    String u = t.geneName.get(j);
                    double v = Double.parseDouble(t.geneExp.get(j));
                    GeneEntry g = new GeneEntry(u, v);

                    if (geneSort.size() < req) {
                        geneSort.add(g);
                    } else if (geneSort.size() == req) {
                        GeneEntry r = geneSort.peek();
                        if (g.val > r.val) {
                            geneSort.poll();
                            geneSort.add(g);
                        }
                    }
                }

                System.out.println("sorting time " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                br2.write(t.label);
                br2.write("\t");

                for (int k = 0; k < req; k++) {
                    GeneEntry h = geneSort.poll();
                    System.out.println(k + "th top gene " + h.name);
                    //int x = t.geneName.indexOf(h.name); //System.out.println("gene index " + x);
                    //t.binExp.set(x,"1");
                    br2.write(h.name);
                    br2.write("\t");
                }
                br2.newLine();
            }
            br2.close();
            System.out.println("total time " + (System.currentTimeMillis() - time));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
