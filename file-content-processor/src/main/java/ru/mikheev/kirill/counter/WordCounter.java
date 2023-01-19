package ru.mikheev.kirill.counter;

import java.io.File;

public abstract class WordCounter {

    protected final File file;
    protected final String wordToCount;

    public WordCounter(String filePath, String wordToCount) {
        this.file = new File(filePath);
        this.wordToCount = wordToCount;
    }

    public abstract long countWord();
}
