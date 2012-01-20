package gclassifier;

//this program associates a value 0 or 1 with each sample depending on whether it has the disease or not
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;

public class SampletoBinary {

    SampletoBinary() {
    }

    ;

    public static void main(String[] args) {

        String file1 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/SampletoDisease.txt";  //file to read from
        String file2 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/SampletoBinary.txt"; //file to write into

        String X;
        String[] Y;

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedWriter br2 = new BufferedWriter(new FileWriter(file2));

            while (br1.ready()) {
                X = br1.readLine();
                Y = X.split("\t");

                if (Y[1].trim().contains("liver metastasis")) {
                    br2.write(Y[0].trim());
                    br2.write("\t");
                    br2.write("1");
                    br2.newLine();
                } else {
                    br2.write(Y[0].trim());
                    br2.write("\t");
                    br2.write("0");
                    br2.newLine();
                }
            }
            br2.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
