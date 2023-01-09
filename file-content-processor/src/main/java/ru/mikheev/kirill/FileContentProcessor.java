package ru.mikheev.kirill;

import java.io.File;

public class FileContentProcessor {


    public static void main(String[] args) throws Exception {
        File currDir = new File(".");
        String currDirPath = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 2);
        FileGenerator fileGenerator = new FileGenerator(currDirPath + "\\src\\test\\resource", "test");
        fileGenerator.generate(500_000_000L, 1);
        System.out.println("Concurrent");
        var startTime = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            WordCounter wordCounter = new ConcurrentWordCounter(currDirPath + "\\src\\test\\resource\\test0.txt");
            wordCounter.countWord("помидор");
            System.out.println("" + (i+1) + " : " + (System.currentTimeMillis() - startTime)/(i+1));
        }
        System.out.println("avg : " + (System.currentTimeMillis() - startTime)/10);

        System.out.println("Single thread");
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            WordCounter wordCounter = new SimpleWordCounter(currDirPath + "\\src\\test\\resource\\test0.txt");
            wordCounter.countWord("помидор");
            System.out.println("" + (i+1) + " : " + (System.currentTimeMillis() - startTime)/(i+1));
        }
        System.out.println("avg : " + (System.currentTimeMillis() - startTime)/10);
        fileGenerator.removeAllFiles();
    }
}
