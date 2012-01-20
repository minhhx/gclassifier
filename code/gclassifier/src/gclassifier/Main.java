package gclassifier;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author minhhx
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String file1 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/ProteinExp.txt";
//        String file2 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/SampletoBinary.txt";
//        String file1 = "/media/Entertain/Code/GSE6988/pe_test.txt";
//        String file2 = "/media/Entertain/Code/GSE6988/samp_test.txt";

        String fileProteinExp = "../GSE6988/ProteinExp.txt";
        String fileSampletoBinary = "../GSE6988/SampletoBinary.txt";
        String filePPI = "./PPI.txt";
        
        Controller c = new Controller(fileProteinExp, fileSampletoBinary, filePPI);
        c.buildDecisionTree();
    }

}
