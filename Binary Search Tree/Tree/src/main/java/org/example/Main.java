package org.example;

public class Main {
    public static void main(String[] args) {
        BSTree tree = new BSTree();

        /*
        tree.insert(50);
        tree.insert(40);
        tree.insert(60);
        tree.insert(30);
        tree.insert(45);
        tree.insert(55);
        tree.insert(65);
        tree.insert(53);
        tree.insert(63);
        tree.insert(70);
        */
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(65);
        tree.insert(80);
        tree.insert(75);
        tree.insert(75);

        System.out.print("Inorder Traversal: ");
        tree.inOrder(tree.getRoot());
        System.out.println();

        System.out.println(tree.getSize());


        if (tree.delete(50)) {
            System.out.println("deleted");
        }

        System.out.print("Inorder Traversal: ");
        tree.inOrder(tree.getRoot());
        System.out.println();

        System.out.println(tree.getSize());

    }
}