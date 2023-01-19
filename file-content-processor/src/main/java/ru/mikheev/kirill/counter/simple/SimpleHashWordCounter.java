package ru.mikheev.kirill.counter.simple;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleHashWordCounter extends SimpleWordCounter {

    private final long PRIME_FACTOR;
    private final long PRIME_FACTOR_POWER;

    public SimpleHashWordCounter(String filePath, String wordToCount, long primeFactor) {
        super(filePath, wordToCount);
        PRIME_FACTOR = primeFactor;
        PRIME_FACTOR_POWER = (long)Math.pow(PRIME_FACTOR, wordToCount.length() - 1);
    }

    @Override
    protected int count(String line) {
        long m = ThreadLocalRandom.current().nextInt(1_000_000_000, 2_000_000_000);
        long wordToCountHash = 0;
        long substringHash = 0;
        for(int i = 0; i < wordToCount.length(); i++) {
            wordToCountHash = (wordToCountHash * PRIME_FACTOR + convertCharToLocalAlphabet(wordToCount.charAt(i))) % m;
            substringHash = (substringHash * PRIME_FACTOR + convertCharToLocalAlphabet(line.charAt(i))) % m;
        }

        int counter = 0;

        for(int i = wordToCount.length(); i < line.length(); i++) {
            if(substringHash == wordToCountHash) counter++;
            substringHash = (
                    (
                            substringHash - PRIME_FACTOR_POWER * convertCharToLocalAlphabet(line.charAt(i - wordToCount.length()))
                    ) * PRIME_FACTOR + convertCharToLocalAlphabet(line.charAt(i))
            ) % m;


            if(substringHash < 0) substringHash += m;
        }
        return counter;
    }

    //TODO : Сделать поддержку символов и заглавных букв
    private int convertCharToLocalAlphabet(char ch) {
        if(ch <= '9' && ch >= '0') return 'я' - 'а' + 3 + (ch - '0');
        if(ch == ' ') return 'я' - 'а' + 2;
        return ch - 'а' + 1;
    }

}

