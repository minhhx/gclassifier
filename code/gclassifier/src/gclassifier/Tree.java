package gclassifier;

import java.util.*;

class TreeNode {

    int id;
    TreeNode zeroChild;
    TreeNode oneChild;
    ArrayList<Integer> Set0;
    ArrayList<Integer> Set1;

    TreeNode(int id) {
        this.id = id;
        this.zeroChild = null;
        this.oneChild = null;
        this.Set0 = new ArrayList();
        this.Set1 = new ArrayList();
    }
}

public class Tree {

    TreeNode rootNode;
    TreeNode currentNode;
    //TreeNode newNode;

    Tree() {
        rootNode = null;
    }


    boolean isEmpty() {
        if (rootNode == null) {
            return true;
        } else {
            return false;
        }
    }

    void insertrootNode(int n) {
        rootNode = new TreeNode(n);
        currentNode = rootNode;
    }

    void insert(TreeNode currentNode, TreeNode childNode, int m) {
        if (m == 0) {
            currentNode.zeroChild = childNode;
        } else {
            currentNode.oneChild = childNode;
        }
    }

    void InOrderTraversal(TreeNode node) {
        if (node != null) {
            InOrderTraversal(node.zeroChild);
            System.out.println("Traversed node " + String.valueOf(node.id));
            InOrderTraversal(node.oneChild);
        }
    }
}
