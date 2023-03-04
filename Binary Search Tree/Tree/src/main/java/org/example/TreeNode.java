package org.example;

public class TreeNode
{
    private TreeNode left;
    private TreeNode right;
    private int data;

    public TreeNode(int data) {
        this.data = data;
        left = null;
        right = null;
    }
    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
