package ru.mikheev.kirill.counter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class SimpleWordCounter extends WordCounter {

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
                var wordsInLine = line.split("(?U)\\W+");
                for(var wordIter : wordsInLine) {
                    if(wordIter.equalsIgnoreCase(wordToCount)) counter++;
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }
}
