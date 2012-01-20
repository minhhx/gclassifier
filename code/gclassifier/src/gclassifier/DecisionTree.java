package gclassifier;

//this builds the Decision tree

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class DecisionTree extends Graph{
    
    
    HashMap<String, String> SampleBinary = new HashMap();
    ArrayList<String> AllSamples = new ArrayList();
    ArrayList<String> sampleSet0 = new ArrayList();  //these will contain the samples in the set after dividing down the dcision tree
    ArrayList<String> sampleSet1 = new ArrayList();
    ArrayList<String> features = new ArrayList();
        
    String p;String q;  double content;
    double p1; double p2;
    double infoContent; double remainder; double threshold = 0.5;
    
    
    
    DecisionTree(){}; 
    
    void SampleClassify(String file2){   //to associate a class 1 or 0 with every sample
        try{
            BufferedReader br = new BufferedReader(new FileReader(file2));
            while(br.ready()){
                String X = br.readLine();
                String[] Y = X.split("\t");
                SampleBinary.put(Y[0].trim(), Y[1].trim());
            }
        }catch (Exception e){System.out.println(e);}
    }
    
     void EnumSamples(String file1, ArrayList<Node> nodes){
          
          try{
               BufferedReader br1 = new BufferedReader(new FileReader(file1));
           
           while(br1.readLine()!=null){
               String[] X = br1.readLine().split("\t"); 
               for(int i = 0; i< (X.length-1); i++){
                   if(AllSamples.contains(X[0]) == false)
                       AllSamples.add(X[0]);
                   //int m = Z.get(X[i+1].trim());
                   //Node n = nodes.get(m);
                   //n.protVal.add(X[0].trim());  //adding the sample names for which this protein has value 1
               }
           } 
    
        }catch (Exception e){System.out.println(e);}
            
      }
     
     void SetSamples(ArrayList<String> z){
         AllSamples.clear();
         AllSamples.addAll(z);
     }
     
     Boolean checkClass(ArrayList<String> m){
         String v = m.get(0); Boolean check = true;
         for(int i =0; i<m.size();i++){
             if(SampleBinary.get(m.get(i)).equals(v)==false){
                 check = false;
                 break;
             }
         } return check;    //check = true implies all of the same class already. no more classification reqd
     }
     
     double Info(ArrayList<String> U){
         int class1 = 0; int class0 = 0; double info=0.0;  double count1 = 0.0; double count0 = 0.0;
         double part1 = 0.0; double part0 = 0.0;
         System.out.println("Info S.size " + U.size());
         if(U.size()!=0){
         for(int i =0; i<U.size();i++){
             if(SampleBinary.get(U.get(i)).equals("1"))
                 class1 ++;
             else if(SampleBinary.get(U.get(i)).equals("0"))
                 class0 ++;
         }
         if(class1!=0){
          count1 = class1/U.size();   
          part1 = -count1*(Math.log(count1)/Math.log(2));
         }
         if(class0!=0){
         count0 = class0/U.size();
          part0 = -count0 *(Math.log(count0)/Math.log(2));
         }
         info = part1 + part0;
         }
         return info;
         
     }
     
     double InfoGain(Node x,ArrayList<String> T){  System.out.println("T.size " + T.size());
         //x.Set1.clear();x.Set0.clear(); //the samples which are in the respective sets after using this feature to 
     int set1 = 0; int set0 = 0;
         System.out.println("T.size i " + T.size() );     
         double infoFeature = 0.0;double gainFeature;
        // System.out.println("S.size " + S.size());
         for(int i =0; i<T.size();i++){
//             if(x.protVal.contains(T.get(i)))
//                 set1++;
//             else set0++;
               
         }
         
         //System.out.println("Set0.size " + x.Set0.size()); System.out.println("T.size again " + T.size());
        //System.out.println("Info gain 1st part " + (x.Set1.size()/T.size()));
        // System.out.println("Info gain 2nd part " + ((x.Set0.size()/T.size()) * Info(x.Set0)));
         //infoFeature = ((set1/T.size()) * Info(x.Set1)) + ((set0/T.size()) * Info(x.Set0)); //the amt of info that would still be needed after artitioning using feature x
         System.out.println("Info gain infoFeature " + infoFeature);
         System.out.println("Info gain S.size " + T.size());
         gainFeature = Info(T) - infoFeature;
         return gainFeature;
     
     }
     
    int chooseFeature(ArrayList<String> feature, ArrayList<String> S, ArrayList<Node> nodes){
        int n = 0; double temp = 0.0;
        for(int i = 0; i<feature.size();i++){
            System.out.println("feature.get(i) " + feature.get(6));
            double r = InfoGain(nodes.get(i), S);
             if (feature.get(i).equals("0") && r>temp){System.out.println("hit a larger info gain");
                 temp = r;
                 n = i;
             }
         }
        return n;
     }
    
    void BuildDec_tree(ArrayList<String> S, ArrayList<String> feature, String file3, ArrayList<Node> nodes){
        System.out.println("Build_Dec S.size " + S.size());
         if (S.size() != 0){
            if(checkClass(S)==true){//if everything is already classified
            try{
                BufferedWriter br = new BufferedWriter(new FileWriter(file3));
                for(int i = 0; i<S.size();i++){
                    br.write(S.get(i));
                    br.write("\t");
                }br.close();
                
            }catch (Exception e){System.out.println(e);}
        }
        
                
        else {
           
                       
                int i = chooseFeature(feature,S,nodes);System.out.println("test1 " + i);
              
                    System.out.println(nodes.size());
                Node a = nodes.get(i);
                System.out.println("S size " + S.size());
                double gain = InfoGain(a,S);
                feature.set(i, "1");
                //BuildDec_tree(a.Set0, feature, file3, nodes);
                //BuildDec_tree(a.Set1, feature, file3, nodes);
                
            }
        }
        }
                  
           
       
    
    public static void main(String[] args){
        Graph c = new Graph();
        String filename = "/home/kasturi/notun/network_constrained_dtree/PPInetwork/PPI.txt";
        try{
            
            
            BufferedReader br = new BufferedReader(new FileReader(filename));
            
            while(br.ready())
			{
				String X = br.readLine(); 
                                String[] Y = X.split("\t");
                                
                                String A = Y[0].trim();  String B = Y[1].trim();
                                
                                c.GraphBuild(A,B);
                                
                                
                        }
            
            }catch(Exception e){System.out.println(e);}
                     
        
        System.out.println("number of nodes " + nodes.size());
        
                   
         String file1 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/ProteinExp.txt";     
     
         String file2 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/SampletoBinary.txt"; 
         
         String file3 = "/home/kasturi/notun/network_constrained_dtree/GSE6988/Classification.txt"; 
         DecisionTree d = new DecisionTree();
         
         
         d.SampleClassify(file2);//to associate class 1 or 0 with each sample
         //d.EnumSamples(file1, nodes);  //this is called just once and protval is set
         double infoGain = 0.0; int nodeid = 0;
         
         for(int i =0; i<nodes.size();i++){
             d.features.add("0");  //initially none of the features have been used, so 0
         }
         System.out.println("size of features " + d.features.size());
         
         //d.BuildDec_tree(d.AllSamples, d.features, file3, nodes);
         
  
    }
    

}
