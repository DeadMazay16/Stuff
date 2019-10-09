package ru.mikheev.kirill;

import ru.mikheev.kirill.somejunk.Calculator;
import ru.mikheev.kirill.somejunk.SimpleQueue;

public class Stuff {
    public static void main(String[] args){
        System.out.println("PUFF");
        Calculator calculator = new Calculator();

        System.out.println(calculator.calculate("2 + 2 + 3"));
    }
}
