package org.example;

public class AVLTree
{
    private TreeNode root;
    private int size;

    public AVLTree() {
        this.root = null;
        size = 0;
    }

    public TreeNode find(int key) {
        TreeNode curr = root;

        while(curr.getKey() != key) {
            if(key > curr.getKey())
                curr = curr.getRight();
            else
                curr = curr.getLeft();
        }

        return curr;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private TreeNode insert(TreeNode root, int key) {
        if(root == null)
            return new TreeNode(key);

        if(key > root.getKey()) {
            root.setRight(insert(root.getRight(), key));
            root.getRight().setParent(root);
        }
        else {
            root.setLeft(insert(root.getLeft(), key));
            root.getLeft().setParent(root);
        }

        root = performBalance(root);

        root.setHeight(calcAndRetHeight(root));
        //size++;

        return root;
    }

    private TreeNode performBalance(TreeNode nodeToBeBalanced) {
        int balanceFactor = calcBalanceFactor(nodeToBeBalanced);

        // VERY IMPORTANT NOTE: WE ALWAYS PERFORM ROTATIONS ON JUST 3 NODES.

        /*
            We have two possibilities, either the balance factor is 2 or -2, and in every possibility we have another two possibilities,
            so we must check them by calling the calcBalanceFactor method again.

            NOTE: bf stands for balance factor.

            Let's take an example that covers a node with bf equals to 2 with their two possibilities:

            1-
                40
              20
            10
            This example means that the node(40) bf is 2 and LEFT HEAVY, and also means the node(20) bf is 1
            we call this LL-imbalance (Left-Left imbalance). So we must perform Right Rotation.

            2-
                40
              20
                25
            This example means that the node(40) bf is 2 and LEFT HEAVY, and also means the node(20) bf is -1
            we call this LR-imbalance (Left-Right imbalance). So we must perform Left Rotation then Right Rotation.

            =====================================================

            Let's take an example that covers a node with bf equals to -2 with their two possibilities:

            1-
                40
                   50
                      60
            This example means that the node(40) bf is -2 and RIGHT HEAVY, and also means the node(50) bf is -1
            we call this RR-imbalance (Right-Right imbalance). So we must perform Left Rotation.

            2-
                40
                   50
                 45
            This example means that the node(40) bf is -2 and RIGHT HEAVY, and also means the node(50) bf is 1
            we call this RL-imbalance (Right-Left imbalance). So we must perform Right Rotation then Left Rotation.
        * */

        // Current node is LEFT HEAVY
        if(balanceFactor == 2) {
            int leftBalanceFactor = calcBalanceFactor(root.getLeft());

            // Left-Left imbalance
            if(leftBalanceFactor == 1) {
                nodeToBeBalanced = rotateRight(nodeToBeBalanced);
            }
            // Left-Right imbalance
            else {
                nodeToBeBalanced.setLeft(rotateLeft(nodeToBeBalanced.getLeft()));
                nodeToBeBalanced = rotateRight(nodeToBeBalanced);
            }
        }
        // Current node is RIGHT HEAVY
        else if(balanceFactor == -2) {
            int rightBalanceFactor = calcBalanceFactor(root.getRight());

            // Right-Right imbalance
            if(rightBalanceFactor == -1) {
                nodeToBeBalanced = rotateLeft(nodeToBeBalanced);
            }
            // Right-Left imbalance
            else {
                nodeToBeBalanced.setRight(rotateRight(nodeToBeBalanced.getRight()));
                nodeToBeBalanced = rotateLeft(nodeToBeBalanced);
            }
        }

        return nodeToBeBalanced;
    }

    private TreeNode rotateRight(TreeNode nodeToBeBalanced) {
        // IMPORTANT NOTE: imbalanced node must have left child

        TreeNode leftChildOfImbalancedNode = nodeToBeBalanced.getLeft();
        leftChildOfImbalancedNode.setParent(nodeToBeBalanced.getParent());
        nodeToBeBalanced.setParent(leftChildOfImbalancedNode);
        nodeToBeBalanced.setLeft(leftChildOfImbalancedNode.getRight());

        if(leftChildOfImbalancedNode.getRight() != null)
            leftChildOfImbalancedNode.getRight().setParent(nodeToBeBalanced);

        leftChildOfImbalancedNode.setRight(nodeToBeBalanced);

        nodeToBeBalanced.setHeight(calcAndRetHeight(nodeToBeBalanced));

        leftChildOfImbalancedNode.setHeight(calcAndRetHeight(leftChildOfImbalancedNode));

        return leftChildOfImbalancedNode;
    }

    private TreeNode rotateLeft(TreeNode nodeToBeBalanced) {
        TreeNode rightChildOfImbalancedNode = nodeToBeBalanced.getRight();
        rightChildOfImbalancedNode.setParent(nodeToBeBalanced.getParent());
        nodeToBeBalanced.setParent(rightChildOfImbalancedNode);
        nodeToBeBalanced.setRight(rightChildOfImbalancedNode.getLeft());

        if(rightChildOfImbalancedNode.getLeft() != null)
            rightChildOfImbalancedNode.getLeft().setParent(nodeToBeBalanced);

        rightChildOfImbalancedNode.setLeft(nodeToBeBalanced);

        nodeToBeBalanced.setHeight(calcAndRetHeight(nodeToBeBalanced));
        rightChildOfImbalancedNode.setHeight(calcAndRetHeight(rightChildOfImbalancedNode));

        return rightChildOfImbalancedNode;
    }

    private int calcAndRetHeight(TreeNode node) {
        int left = height(node.getLeft());
        int right = height(node.getRight());

        return Math.max(left, right) + 1;
    }
    private int height(TreeNode node) {
        return node == null ? -1 : node.getHeight();
    }

    private int calcBalanceFactor(TreeNode root) {
        return height(root.getLeft()) - height(root.getRight());
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }
}
