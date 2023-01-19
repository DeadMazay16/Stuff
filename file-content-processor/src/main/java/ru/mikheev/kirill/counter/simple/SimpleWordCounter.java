package ru.mikheev.kirill.counter.simple;

import ru.mikheev.kirill.counter.WordCounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class SimpleWordCounter extends WordCounter {

    public SimpleWordCounter(String filePath) {
        super(filePath);
    }

    @Override
    public long countWord(String wordToCount) {

        long counter = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(this.file, Charset.forName("Cp1251"))
        )){
            String line = bufferedReader.readLine();
            while (line != null) {
                count(line, wordToCount);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    protected abstract int count(String line, String wordToCount);
}
