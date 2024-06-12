package ru.mikheev.kirill.request.limiter;

import ru.mikheev.kirill.request.limiter.commons.DefaultProcessor;
import ru.mikheev.kirill.request.limiter.commons.Request;
import ru.mikheev.kirill.request.limiter.commons.RequestProcessor;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {

    public static void main(String[] args) {
        Random random = new Random();
        DefaultProcessor defaultProcessor = new DefaultProcessor();
        RequestProcessor fullChain = new TokenBucketFilter(defaultProcessor, 4, 2300, 4);
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        long startTime = System.currentTimeMillis();
        System.out.println("START " + startTime);
        long endTime =  System.currentTimeMillis();
        while (endTime - startTime < 2000) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(random.nextInt(50));
                } catch (InterruptedException e) {
                }
                fullChain.process(new Request(
                        Map.of("name", "test name"),
                        null
                ));
            });
            endTime =  System.currentTimeMillis();
        }

        System.out.println("END " + endTime + " " +  (endTime - startTime));
        System.out.println("Result: " + defaultProcessor.getRequestCounter());
        threadPool.shutdownNow();
    }
}
