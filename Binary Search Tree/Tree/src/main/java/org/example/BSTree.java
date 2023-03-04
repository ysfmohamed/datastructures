package org.example;

public class BSTree
{
    private TreeNode root;
    private int size;

    public BSTree() {
        this.root = null;
        this.size = 0;
    }

    public TreeNode find(int key) {
        if(root == null) {
            System.out.println("Tree is empty");
            return null;
        }

        TreeNode curr = root;

        while(curr.getData() != key) {
            if(key > curr.getData())
                curr = curr.getRight();

            else
                curr = curr.getLeft();

            if(curr == null)
                return null; // key doesn't exist
        }

        return curr; // found it
    }

    public void insert(int key) {
        TreeNode searchForKey = find(key);
        if(searchForKey != null) {
            System.out.println(key + " already exits in the tree.");
            return;
        }
        TreeNode newTreeNode = new TreeNode(key);

        if(root == null) {
            root = newTreeNode;
            this.size++;
            return;
        }

        TreeNode curr = root;
        TreeNode parent = null;

        boolean insertAtRight = false;

        while(curr != null) {
            if(key > curr.getData()) {
                parent = curr;
                curr = curr.getRight();
                insertAtRight = true;
            }

            else if(key < curr.getData()) {
                parent = curr;
                curr = curr.getLeft();
                insertAtRight = false;
            }
        }

        if(insertAtRight) {
            parent.setRight(newTreeNode);
        }
        else {
            parent.setLeft(newTreeNode);
        }

        this.size++;
    }

    public TreeNode findParent(int key) {
        TreeNode curr = root;
        TreeNode parent = null;

        while(curr.getData() != key) {
            parent = curr;
            if(key > curr.getData())
                curr = curr.getRight();
            else
                curr = curr.getLeft();
        }

        return parent;
    }

    public boolean delete(int key) {
        if(root == null) {
            System.out.println("Tree is already empty.");
            return false;
        }

        TreeNode nodeToBeDeleted = find(key);

        if(nodeToBeDeleted == null)
            return false;

        // If the node to be deleted is leaf node (has no children)
        if(nodeToBeDeleted.getLeft() == null && nodeToBeDeleted.getRight() == null) {
            if(nodeToBeDeleted == root) {
                root = null;
            }
            else {
                TreeNode parent = findParent(nodeToBeDeleted.getData());
                if (parent.getData() < nodeToBeDeleted.getData())
                    parent.setRight(null);
                else
                    parent.setLeft(null);
            }
        }
        // If the node to be deleted has only one child on the LEFT
        else if(nodeToBeDeleted.getLeft() != null && nodeToBeDeleted.getRight() == null) {
            if(nodeToBeDeleted == root) {
                root = nodeToBeDeleted.getLeft();
            }
            else {
                TreeNode parent = findParent(nodeToBeDeleted.getData());

                if (key < parent.getData()) {
                    parent.setLeft(nodeToBeDeleted.getLeft());
                }
                else {
                    parent.setRight(nodeToBeDeleted.getLeft());
                }
            }
        }
        // If the node to be deleted has only one child on the RIGHT
        else if(nodeToBeDeleted.getLeft() == null && nodeToBeDeleted.getRight() != null) {
            if(nodeToBeDeleted == root) {
                root = nodeToBeDeleted.getRight();
            }
            else {
                TreeNode parent = findParent(nodeToBeDeleted.getData());

                if(key < parent.getData()) {
                    parent.setLeft(nodeToBeDeleted.getRight());
                }
                else {
                    parent.setRight(nodeToBeDeleted.getRight());
                }
            }
        }
        // If the node to be deleted has two children
        else {
            // Find the next greatest key after the key of the node to be deleted, which is called Successor.
            TreeNode successorNode = getSuccessor(nodeToBeDeleted);
            // Find the successor parent
            TreeNode successorParent = findParent(successorNode.getData());

            if(successorNode != nodeToBeDeleted.getRight()) {
                handleConnections(successorParent, successorNode, nodeToBeDeleted);
            }

            if(nodeToBeDeleted == root) {
                root = successorNode;
            }

            else {
                // Find the parent of the node to be deleted
                TreeNode parent = findParent(nodeToBeDeleted.getData());
                /*
                  Successor node has two possible positions relative to the node to be deleted,
                  the successor node can be the right child of the node to be deleted, or
                  it can be one of this right child's left descendants

                  If the successor is the right child of the node to be deleted, then we should do 2 steps:
                  1- Unplug the node to be deleted from the right/left child field of its parent and set this field to point to successor.
                  2- Unplug left child field from the node to be deleted and plug it into the left child field of the successor node.
                */
                if (key > parent.getData()) {
                    parent.setRight(successorNode);
                } else {
                    parent.setLeft(successorNode);
                }
            }
            // this should be executed anyway, to handle the other node.
            successorNode.setLeft(nodeToBeDeleted.getLeft());
        }

        this.size--;
        return true;
    }

    public TreeNode getSuccessor(TreeNode delNode) {
        //TreeNode successorParent = delNode;
        TreeNode successor = delNode;
        TreeNode curr = delNode.getRight();

        while(curr != null) {
            //successorParent = successor;
            successor = curr;
            curr = curr.getLeft();
        }

        return successor;
    }

    private void handleConnections(TreeNode successorParent, TreeNode successor, TreeNode delNode) {
        successorParent.setLeft(successor.getRight());
        successor.setRight(delNode.getRight());
    }

    public int findMin() {
        if(root == null) {
            System.out.println("Tree is empty.");
            return -1;
        }

        TreeNode curr = root;
        TreeNode prev = null;

        while(curr != null) {
            prev = curr;
            curr = curr.getLeft();
        }

        return prev.getData();
    }

    public int findMin(TreeNode root) {
        if(root == null)
            return -1;

        else if(root.getLeft() == null)
            return root.getData();

        else
            return findMin(root.getLeft());
    }

    public int findMax(TreeNode root) {
        if(root == null)
            return -1;

        else if(root.getRight() == null)
            return root.getData();

        else
            return findMax(root.getRight());
    }

    public int findMax() {
        if(root == null) {
            System.out.println("Tree is empty.");
            return -1;
        }

        TreeNode curr = root;
        TreeNode prev = null;

        while(curr != null) {
            prev = curr;
            curr = curr.getRight();
        }

        return prev.getData();
    }
    public void inOrder(TreeNode root) {
        if(root == null)
            return;

        inOrder(root.getLeft());
        System.out.print(root.getData() + " ");
        inOrder(root.getRight());
    }

    public void preOrder(TreeNode root) {
        if(root == null)
            return;

        System.out.print(root.getData() + " ");
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    public void postOrder(TreeNode root) {
        if(root == null)
            return;

        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getData() + " ");
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }
}
