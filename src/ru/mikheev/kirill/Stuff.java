package ru.mikheev.kirill;

import ru.mikheev.kirill.somejunk.SimpleQueue;

public class Stuff {
    public static void main(String[] args){
        System.out.println("PUFF");
        SimpleQueue<Integer> myQueue = new SimpleQueue<>();
        for (int i = 10; i > 0; i-- ){
            myQueue.push(i);
        }
        for (int i = 0; i < 10; i++){
            System.out.println(myQueue.pop());
        }
    }
}
