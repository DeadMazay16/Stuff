package ru.mikheev.kirill;

import org.openjdk.jmh.annotations.*;
import ru.mikheev.kirill.counter.simple.SimpleEqualsWordCounter;
import ru.mikheev.kirill.counter.WordCounter;

import java.util.concurrent.TimeUnit;

public class WordCounterTest {

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void simpleWordCounterTest() {
        WordCounter wordCounter = new SimpleEqualsWordCounter("src\\test\\resource\\wap.txt");
        var result = wordCounter.countWord("Пьер");
    }
}
