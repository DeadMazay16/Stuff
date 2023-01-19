package ru.mikheev.kirill.counter.simple;

public class SimpleEqualsWordCounter extends SimpleWordCounter {

    public SimpleEqualsWordCounter(String filePath, String wordToCount) {
        super(filePath, wordToCount);
    }

    @Override
    protected int count(String line) {
        int counter = 0;
        var wordsInLine = line.split("(?U)\\W+");
        for(var wordIter : wordsInLine) {
            if(wordIter.equalsIgnoreCase(wordToCount)) counter++;
        }
        return counter;
    }
}
