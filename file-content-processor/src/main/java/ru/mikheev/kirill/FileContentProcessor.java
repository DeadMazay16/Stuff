package ru.mikheev.kirill;

import ru.mikheev.kirill.counter.WordCounter;
import ru.mikheev.kirill.counter.simple.SimpleHashWordCounter;
import ru.mikheev.kirill.counter.simple.SimplePrefWordCounter;
import ru.mikheev.kirill.gen.FileGenerator;

import java.io.File;

public class FileContentProcessor {


    public static void main(String[] args) throws Exception {
        testCounters(10);
//        TableGenerator<TestData> tableGenerator = new TableGenerator<>(TestData.class);
//        List<TestData> testData = new ArrayList<>();
//        testData.add(new TestData(1, "Привет"));
//        System.out.println(tableGenerator.generate(testData));
    }

    public static void testCounters(int testsCount) {
        File currDir = new File(".");
        String currDirPath = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 2);
        FileGenerator fileGenerator = new FileGenerator(currDirPath + "\\src\\test\\resource", "test");
        fileGenerator.generate(50_000_000L, 1);
        System.out.println("Pref");
        var startTime = System.currentTimeMillis();
        for(int i = 0; i < testsCount; i++) {
            var singleRunStartTime = System.currentTimeMillis();
            WordCounter wordCounter = new SimplePrefWordCounter(currDirPath + "\\src\\test\\resource\\test0.txt", "помидор");
            System.out.println(wordCounter.countWord());
            System.out.println("" + (i+1) + " : " + (System.currentTimeMillis() - singleRunStartTime));
        }
        System.out.println("avg : " + (System.currentTimeMillis() - startTime)/testsCount);

        System.out.println("Hash");
        startTime = System.currentTimeMillis();
        for(int i = 0; i < testsCount; i++) {
            var singleRunStartTime = System.currentTimeMillis();
            WordCounter wordCounter = new SimpleHashWordCounter(currDirPath + "\\src\\test\\resource\\test0.txt", "помидор", 31);
            System.out.println(wordCounter.countWord());
            System.out.println("" + (i+1) + " : " + (System.currentTimeMillis() - singleRunStartTime));
        }
        System.out.println("avg : " + (System.currentTimeMillis() - startTime)/testsCount);
        fileGenerator.removeAllFiles();
    }
}
