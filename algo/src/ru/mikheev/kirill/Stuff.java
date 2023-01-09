package ru.mikheev.kirill;

import ru.mikheev.kirill.structures.AVLTree;
import ru.mikheev.kirill.utilities.ClassInformationHolder;

public class Stuff {
    public static void main(String[] args){
        //System.out.println("PUFF");
        ClassInformationHolder cih = new ClassInformationHolder(AVLTree.class);
        cih.getAllInformation();
    }
}
