package ru.mikheev.kirill.counter.concurrent;

import ru.mikheev.kirill.concurrent.WorkExecutor;
import ru.mikheev.kirill.counter.WordCounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class ConcurrentWordCounter extends WordCounter {

    public ConcurrentWordCounter(String filePath) {
        super(filePath);
    }

    @Override
    public long countWord(String wordToCount) {
        WorkExecutor workExecutor = new WorkExecutor(wordToCount, 4, 60);
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(this.file, Charset.forName("Cp1251"))
        )){
            String line = bufferedReader.readLine();
            while (line != null) {
                workExecutor.putLine(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        workExecutor.completeWork();
        return workExecutor.getResult();
    }
}
