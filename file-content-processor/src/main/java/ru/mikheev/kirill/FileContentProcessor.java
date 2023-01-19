package ru.mikheev.kirill;

import ru.mikheev.kirill.counter.simple.SimpleEqualsWordCounter;
import ru.mikheev.kirill.counter.simple.SimpleHashWordCounter;
import ru.mikheev.kirill.counter.WordCounter;
import ru.mikheev.kirill.gen.FileGenerator;

import java.io.File;

public class FileContentProcessor {


    public static void main(String[] args) throws Exception {
        testCounters();
//        TableGenerator<TestData> tableGenerator = new TableGenerator<>(TestData.class);
//        List<TestData> testData = new ArrayList<>();
//        testData.add(new TestData(1, "Привет"));
//        System.out.println(tableGenerator.generate(testData));
    }

    public static void testCounters() {
        File currDir = new File(".");
        String currDirPath = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 2);
        FileGenerator fileGenerator = new FileGenerator(currDirPath + "\\src\\test\\resource", "test");
        fileGenerator.generate(50_000_000L, 1);
        System.out.println("Equals");
        var startTime = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            var singleRunStartTime = System.currentTimeMillis();
            WordCounter wordCounter = new SimpleEqualsWordCounter(currDirPath + "\\src\\test\\resource\\test0.txt");
            wordCounter.countWord("помидор");
            System.out.println("" + (i+1) + " : " + (System.currentTimeMillis() - singleRunStartTime));
        }
        System.out.println("avg : " + (System.currentTimeMillis() - startTime)/10);

        System.out.println("Hash");
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            var singleRunStartTime = System.currentTimeMillis();
            WordCounter wordCounter = new SimpleHashWordCounter(currDirPath + "\\src\\test\\resource\\test0.txt");
            wordCounter.countWord("помидор");
            System.out.println("" + (i+1) + " : " + (System.currentTimeMillis() - singleRunStartTime));
        }
        System.out.println("avg : " + (System.currentTimeMillis() - startTime)/10);
        fileGenerator.removeAllFiles();
    }
}
