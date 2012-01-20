package gclassifier;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kasturi
 */
// to extract the gene protein mappings
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;

public class GenetoProtein {

    GenetoProtein() {
    }

    ;

    public static void main(String[] args) {
        String filename1 = "/cs/student/kbhattacharjee/Dec_tree/GSE3964/mapping.txt";  //to read from
        String filename2 = "/cs/student/kbhattacharjee/Dec_tree/GSE3964/genetoprot.txt";  // to write into

        String X;
        String[] Y;

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(filename1));
            BufferedWriter br2 = new BufferedWriter(new FileWriter(filename2));


            while (br1.ready()) {
                X = br1.readLine();
                if (X.split(" ").length < 2) {
                    continue;
                }
                //if(X.indexOf(" ")!= -1){ 

                Y = X.split(" ");
                System.out.println("y0 " + Y[0] + " y1" + Y[Y.length - 1].trim());
                br2.write(Y[0]);
                br2.write("\t");
                br2.write(Y[Y.length - 1].trim());
                br2.newLine();



                //} 
            }
            br2.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
