package org.example;

public class TreeNode
{
    private TreeNode parent;
    private TreeNode left;
    private TreeNode right;
    private int key;
    private int height;

    public TreeNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.height = 0;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
