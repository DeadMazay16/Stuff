package ru.mikheev.kirill.counter.simple;

public class SimpleEqualsWordCounter extends SimpleWordCounter {

    public SimpleEqualsWordCounter(String filePath) {
        super(filePath);
    }

    @Override
    protected int count(String line, String wordToCount) {
        int counter = 0;
        var wordsInLine = line.split("(?U)\\W+");
        for(var wordIter : wordsInLine) {
            if(wordIter.equalsIgnoreCase(wordToCount)) counter++;
        }
        return counter;
    }
}
