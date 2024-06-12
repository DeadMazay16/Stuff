package ru.mikheev.kirill.request.limiter;

import ru.mikheev.kirill.request.limiter.commons.Request;
import ru.mikheev.kirill.request.limiter.commons.RequestProcessor;
import ru.mikheev.kirill.request.limiter.commons.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedIntervalCounterFilter extends RequestProcessor {

    private final Map<String, IntervalCounter> intervalsByUser = new ConcurrentHashMap<>();

    private final int counterSize;
    private final long intervalLength;

    private final RequestProcessor delegate;

    public FixedIntervalCounterFilter(RequestProcessor delegate, int counterSize, long intervalLength) {
        this.delegate = delegate;
        this.counterSize = counterSize;
        this.intervalLength = intervalLength;
    }

    @Override
    public Response process(Request request) {
        String name = request.getHeaders().get("name");
        IntervalCounter interval = intervalsByUser.computeIfAbsent(name, this::createIntervalCounter);
        if (interval.requestAllowed()) {
            return delegate.process(request);
        }
        return tooManyRequestsErrorResult();
    }

    private IntervalCounter createIntervalCounter(String name) {
        return new IntervalCounter();
    }

    private class IntervalCounter {

        long nextIntervalTimestamp;
        int counter;

        private IntervalCounter() {
            nextIntervalTimestamp = System.currentTimeMillis() + intervalLength;
            counter = counterSize;
        }

        synchronized boolean requestAllowed() {
            refill();
            if (counter > 0) {
                counter--;
                return true;
            }
            return false;
        }

        void refill() {
            long currentTime = System.currentTimeMillis();
            if (currentTime >= nextIntervalTimestamp) {
                counter = counterSize;
                nextIntervalTimestamp += ((currentTime - nextIntervalTimestamp) / intervalLength + 1) * intervalLength;
            }
        }
    }
}
