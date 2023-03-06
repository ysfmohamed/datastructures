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

    public int findMin() {
        TreeNode curr = root;
        TreeNode prev = null;

        while(curr != null) {
            prev = curr;
            curr = curr.getLeft();
        }

        return prev.getKey();
    }

    public int findMax() {
        TreeNode curr = root;
        TreeNode prev = null;

        while(curr != null) {
            prev = curr;
            curr = curr.getRight();
        }

        return prev.getKey();
    }

    public void inOrder() {
        inOrder(root);
    }

    public void preOrder() {
        preOrder(root);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void inOrder(TreeNode root) {
        if(root == null)
            return;

        inOrder(root.getLeft());
        System.out.print(root.getKey() + " ");
        inOrder(root.getRight());
    }

    private void preOrder(TreeNode root) {
        if(root == null)
            return;

        System.out.print(root.getKey() + " ");
        inOrder(root.getLeft());
        inOrder(root.getRight());
    }

    private void postOrder(TreeNode root) {
        if(root == null)
            return;

        inOrder(root.getLeft());
        inOrder(root.getRight());
        System.out.print(root.getKey() + " ");
    }

    public void insert(int key) {
        TreeNode isAlreadyExist = find(key);
        if(isAlreadyExist != null) {
            System.out.println("There is already " + key + " in the tree.");
        }
        else {
            root = insert(root, key);
            size++;
        }
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

        /*
            Let's take an example, assume that we have a balanced binary search tree like that:
                        60
                     57    70
                   55   58

            and we want to insert(50).

            the balanced binary search tree becomes unbalanced as follows:
                        60
                     57    70
                   55   58
                 50

            Let's calculate balance factor of every node after inserting(50):
            bf of node(50) = 0 - 0 = 0
            bf of node(70) = 0 - 0 = 0
            bf of node(58) = 0 - 0 = 0
            bf of node(55) = 1 - 0 = 1
            bf of node(57) = 2 - 1 = 1
            bf of node(60) = 3 - 1 = 2

            so node(60) becomes imbalanced, and we must balance it to convert the unbalanced bst to balanced bst.

            Solution:
            - since bf of node(60) is 2 (LEFT HEAVY) and bf of node(57) is 1, so it means that we made a Left-Left imbalance insertion, so
              we should do Right Rotation.

            Right Rotation:
                1- pass the imbalanced node to the rotateRight method, and lets call it "x".

                2- assign the left child of x to a temporary variable called "lc".
                   TreeNode leftChildOfImbalancedNode = nodeToBeBalanced.getLeft();

                3- since we are doing right rotation, so we must handle the parents of x and lc.
                   so we need to assign the parent of x in the parent of lc,
                   and we need to assign lc in the parent of x
                   leftChildOfImbalancedNode.setParent(nodeToBeBalanced.getParent());
                   nodeToBeBalanced.setParent(leftChildOfImbalancedNode);

                   in the parents context, lc becomes the father of x and x becomes the child of lc.

                3- now our lc node has two children one on the left and the other on the right, and we must handle that right child,
                   to handle it just unplug it from the lc and plug it into the x as a left child.
                   nodeToBeBalanced.setLeft(leftChildOfImbalancedNode.getRight());

                   and its parent becomes x, so we must also handle this case
                   if(leftChildOfImbalancedNode.getRight() != null)
                       leftChildOfImbalancedNode.getRight().setParent(nodeToBeBalanced);

                4- now, move down the x to the right, and then move the lc up to take the place of x,
                   and plug the x as right child of lc.
                   leftChildOfImbalancedNode.setRight(nodeToBeBalanced);

            so the tree becomes:
                         57
                        /  \
                      55    60
                     /     /  \
                   50     58   70

            Let's calculate balance factor of every node after balancing:
            bf of node(50) = 0 - 0 = 0
            bf of node(70) = 0 - 0 = 0
            bf of node(58) = 0 - 0 = 0
            bf of node(55) = 1 - 0 = 1
            bf of node(57) = 2 - 2 = 0
            bf of node(60) = 1 - 1 = 0

            now the binary search tree becomes balanced.

            now we must handle the heights of the x and lc node since we changed their positions.
            nodeToBeBalanced.setHeight(calcAndRetHeight(nodeToBeBalanced));
            leftChildOfImbalancedNode.setHeight(calcAndRetHeight(leftChildOfImbalancedNode));

            finally return lc node.
        * */

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

    public void delete(int key) {
        root = delete(root, key);
    }

    private TreeNode delete(TreeNode root, int key) {
        // We didn't find the node with the given key
        if(root == null)
            return null;

        // We found the node with the given key
        if(root.getKey() == key) {
            // leaf node
            if(root.getLeft() == null && root.getRight() == null) {
                root = null;
            }
            // has only one child on the left
            else if(root.getLeft() != null && root.getRight() == null) {
                TreeNode temp = root;
                temp.getLeft().setParent(root.getParent());
                root = temp.getLeft();
                temp = null;
            }
            // has only one child on right
            else if(root.getLeft() == null && root.getRight() != null) {
                TreeNode temp = root;
                temp.getRight().setParent(root.getParent());
                root = temp.getRight();
                temp = null;
            }
            // has two children
            else {
                int successor = getSuccessor(key);
                root.setKey(successor);
                root.setRight(delete(root.getRight(), successor));
            }
        }
        else if(root.getKey() > key) {
            root.setLeft(delete(root.getLeft(), key));
        }
        else {
            root.setRight(delete(root.getRight(), key));
        }

        if(root != null) {
            root = performBalance(root);
            root.setHeight(calcAndRetHeight(root));
        }

        return root;
    }

    public int getSuccessor(int key) {
        TreeNode node = find(key);
        return node == null ? -1 : getSuccessor(node);
    }

    private int getSuccessor(TreeNode node) {
        TreeNode curr = node.getRight();
        TreeNode prev = null;

        while(curr != null) {
            prev = curr;
            curr = curr.getLeft();
        }

        return prev.getKey();
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
