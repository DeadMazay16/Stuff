package ru.mikheev.kirill.request.limiter.commons;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultProcessor implements RequestProcessor {

    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @Override
    public Response process(Request request) {
        requestCounter.incrementAndGet();
        return new Response(
                Status.OK,
                Collections.emptyMap(),
                null
        );
    }

    public int getRequestCounter() {
        return requestCounter.get();
    }
}
