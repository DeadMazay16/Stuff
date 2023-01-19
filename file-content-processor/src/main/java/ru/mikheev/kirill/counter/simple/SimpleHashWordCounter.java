package ru.mikheev.kirill.counter.simple;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleHashWordCounter extends SimpleWordCounter {

    private final int PRIME_FACTOR = 31;
    int[] hashes = new int[0];

    public SimpleHashWordCounter(String filePath) {
        super(filePath);
    }

    @Override
    protected int count(String line, String wordToCount) {
        int m = ThreadLocalRandom.current().nextInt(1_000_000_000, 2_000_000_000);
        int wordToCountHash = convertCharToLocalAlphabet(wordToCount.charAt(0));
        for(int i = 1; i < wordToCount.length(); i++) {
            wordToCountHash += (wordToCountHash * PRIME_FACTOR + convertCharToLocalAlphabet(wordToCount.charAt(i))) % m;
        }
        if(hashes.length < line.length()) {
            hashes = new int[line.length()];
        }
        hashes[0] = convertCharToLocalAlphabet(line.charAt(0));
        for(int i = 1; i < wordToCount.length() - 1; i++) {
            hashes[i] = (hashes[i - 1] * PRIME_FACTOR + convertCharToLocalAlphabet(line.charAt(i))) % m;
        }
        int counter = 0;
        for(int i = wordToCount.length() - 1; i < line.length(); i++) {
            hashes[i] = (hashes[i - 1] * PRIME_FACTOR + convertCharToLocalAlphabet(line.charAt(i))) % m;
            if(hashes[i] == wordToCountHash) counter++;
        }
        return counter;
    }

    //TODO : Сделать поддержку символов и заглавных букв
    private int convertCharToLocalAlphabet(char ch) {
        return ch - 'a';
    }

}

