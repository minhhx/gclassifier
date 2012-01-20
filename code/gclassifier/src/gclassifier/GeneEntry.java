package gclassifier;

import java.util.*;

public class GeneEntry implements Comparable {

    String name;
    double val;

    GeneEntry(String name, double val) {
        this.name = name;
        this.val = val;
    }

    public int compareTo(Object o) {
        GeneEntry g = (GeneEntry) o;  //this method ensures that only the largest value elements are kept in the queue
        if (this.val > g.val) {
            return 1;
        } else if (this.val < g.val) {
            return -1;
        } else {
            return 0;
        }
    }
}
