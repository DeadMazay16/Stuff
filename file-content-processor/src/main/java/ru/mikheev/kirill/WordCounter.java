package ru.mikheev.kirill;

import java.io.File;

public abstract class WordCounter {

    protected final File file;

    public WordCounter(String filePath) {
        this.file = new File(filePath);
    }

    public abstract long countWord(String wordToCount);
}
