package ru.mikheev.kirill.somejunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Calculator {

    public Calculator(){    }

    public int calculate(String expression){
        String withoutSpaces = expression.replaceAll("\\s", "");

        String[] strings = withoutSpaces.split("\\D");
        ArrayList<Integer> numbers = new ArrayList<>();
        for(String tmp : strings){
            numbers.add(Integer.parseInt(tmp));
        }
        return numbers.get(1);
    }
}
