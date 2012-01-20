package gclassifier;

// this program selects only those proteins which have a mapping from the genes
import java.util.*;
import java.io.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class SelectProt {

    SelectProt() {
    }
    public static void main(String[] args) {

        String file1 = "/cs/student/kbhattacharjee/Dec_tree/GSE3964/genetoprot.txt";
        String file2 = "/cs/student/kbhattacharjee/Dec_tree/HPRD_Release9_062910/BINARY_PROTEIN_PROTEIN_INTERACTIONS.txt";
        String file3 = "/cs/student/kbhattacharjee/Dec_tree/GSE3964/PPIfinal.txt";

        String X;
        String[] Y;
        String Z;
        String[] A;
        HashSet S = new HashSet();

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(file1));

            while (br1.ready()) {
                X = br1.readLine();
                Y = X.split("\t");
                S.add(Y[1].trim());
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(S.size());

        try {
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedWriter br3 = new BufferedWriter(new FileWriter(file3));

            while (br2.ready()) {
                Z = br2.readLine();
                A = Z.split("\t");

                if (S.contains(A[0].trim()) && S.contains(A[3].trim())) {   //both the node and its neighbor has to be from the previous file mapped from the genes

                    br3.write(A[1]);
                    br3.write("\t");
                    br3.write(A[4]);
                    br3.newLine();
                }
            }
            br3.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
