package ru.mikheev.kirill;


import ru.mikheev.kirill.somejunk.AVLTree;

public class Stuff {
    public static void main(String[] args){
        System.out.println("PUFF");
        AVLTree<Integer> myTree = new AVLTree<>();
        for (int i = 0; i < 10; i++){
            myTree.add(i);
            System.out.println(myTree);
        }
        for (int i = 0; i < 10; i++){
            System.out.println(i);
            System.out.println(myTree);
            myTree.remove(i);
        }
    }
}
