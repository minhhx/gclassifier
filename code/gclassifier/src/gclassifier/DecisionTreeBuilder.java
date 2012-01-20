package gclassifier;

//the modified decision tree program
import java.util.*;

public class DecisionTreeBuilder {

    double Gain;
    int feature;
    HashMap<Integer, Integer> featureList;
    boolean[][] storeFile;
    
    // Graph g;
    DecisionTreeBuilder(boolean[][] _storeFile) {
        storeFile = _storeFile;
    }

    double Info(ArrayList<Integer> sampleIndex) {
        int class0 = 0;
        int class1 = 0;
        double p0;
        double p1;
        double info = 0.0;
        for (int i = 0; i < sampleIndex.size(); i++) {
            int j = sampleIndex.get(i);
            boolean b = storeFile[j][0];

            if (b == true) {
                class1++;
            } else if (b == false) {
                class0++;
            }
        }
        p0 = (double) class0 / (double) (class1 + class0);
        p1 = (double) class1 / (double) (class1 + class0);
        if (p0 != 0.0 && p1 != 0.0) {
            info = -(p0 * (Math.log(p0) / Math.log(2)) + p1 * (Math.log(p1) / Math.log(2)));
        } else if (p0 != 0.0 && p1 == 0.0) {
            info = -(p0 * (Math.log(p0) / Math.log(2)));
        } else if (p1 != 0.0 && p0 == 0.0) {
            info = -(p1 * (Math.log(p1) / Math.log(2)));
        } else if (p0 == 0.0 && p1 == 0.0) {
            info = 0.0;
        }

        return info;
    }

    double InfoFeature(int featureIndex, ArrayList<Integer> sampleIndex) {
        ArrayList<Integer> Val0 = new ArrayList();
        ArrayList<Integer> Val1 = new ArrayList();
        for (int i = 0; i < sampleIndex.size(); i++) {
            int j = sampleIndex.get(i);
            if (storeFile[j][featureIndex] == true) {
                Val1.add(j);
            } else {
                Val0.add(j);
            }
        }

        if (Val0.size() == 0 || Val1.size() == 0) {
            return 1;
        } else {
            double temp1 = (Val1.size() / (Val1.size() + Val0.size()));
            double temp2 = Info(Val1);
            double temp3 = (Val0.size() / (Val1.size() + Val0.size()));
            double temp4 = Info(Val0);
            double infofeature = (Val1.size() / (Val1.size() + Val0.size())) * Info(Val1) + (Val0.size() / (Val1.size() + Val0.size())) * Info(Val0);
            return infofeature;
        }
    }

    boolean checkClass(ArrayList<Integer> m) {
        boolean check = storeFile[m.get(0)][0];
        for (int i = 0; i < m.size(); i++) {
            if (storeFile[m.get(i)][0] != check) {
                check = false;
                break;
            }
        }
        return check;    //check = true implies all of the same class already. no more classification reqd
    }

    int chooseFeature(ArrayList<Integer> m) {
        double temp1 = Info(m);
        double temp2 = InfoFeature(1, m);

        Gain = Info(m) - InfoFeature(1, m);
        int val = 0;
        for (int i = 0; i < storeFile[0].length - 1; i++) {
            if (Info(m) - InfoFeature(i + 1, m) > Gain) {
                Gain = Info(m) - InfoFeature(i + 1, m);
                val = i;
            }
        }
        return val;
    }

    void BuildDecTree(ArrayList<Integer> S, String file3, Tree t) {

        feature = chooseFeature(S);

        t.insertrootNode(feature);
        TreeNode newNode = t.rootNode;

        //System.out.println("Set0 " + t.currentNode.Set0.size());
        for (int i = 0; i < S.size(); i++) {
            if (storeFile[S.get(i)][feature + 1] == true) {
                System.out.println("test " + storeFile[S.get(i)][feature + 1]);
                newNode.Set1.add(S.get(i));
            } else {
                newNode.Set0.add(S.get(i));
            }
        }

        if (newNode.Set0.size() == 0 || newNode.Set1.size() == 0 || checkClass(S)) {
            newNode.id = -1;
            newNode.Set0 = null;
            newNode.Set1 = null;
        } else {

            TreeNode temp = newNode;
            BuildDecTree(temp.Set0, file3, t, 0, t.rootNode);
            BuildDecTree(temp.Set1, file3, t, 1, t.rootNode);
        }

    }

    void BuildDecTree(ArrayList<Integer> S, String file3, Tree t, int leftOrRight, TreeNode currentNode) {

        System.out.println(S.size());
        feature = chooseFeature(S);

        TreeNode newNode = new TreeNode(feature);

        //System.out.println("Set0 " + t.currentNode.Set0.size());
        for (int i = 0; i < S.size(); i++) {
            if (storeFile[S.get(i)][feature + 1] == true) {
                System.out.println("test " + storeFile[S.get(i)][feature + 1]);
                newNode.Set1.add(S.get(i));
            } else {
                newNode.Set0.add(S.get(i));
            }
        }

        if (newNode.Set0.size() == 0 || newNode.Set1.size() == 0 || checkClass(S)) {
            newNode.id = -1;
            newNode.Set0 = null;
            newNode.Set1 = null;
            t.insert(currentNode, newNode, leftOrRight);
        } else {
            TreeNode temp = newNode;
            t.insert(currentNode, newNode, leftOrRight);
            BuildDecTree(temp.Set0, file3, t, 0, newNode);
            BuildDecTree(temp.Set1, file3, t, 1, newNode);
        }
    }
    /*public static void main(String[] args){
    
    Test_dec_tree d = new Test_dec_tree(); 
    
    ArrayList<Integer> samples = new ArrayList();
    for(int i = 0; i <5; i++)
    samples.add(i);
    
    String file3 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/Classification_test.txt";
    Tree t = new Tree();
    System.out.println("check " + d.storeFile_test[samples.get(0)][0]);
    d.BuildDecTree(samples, file3, t, d);
    
    t.InOrderTraversal(t.rootNode);
    }*/
}
