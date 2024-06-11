package ru.mikheev.kirill.request.limiter;

import ru.mikheev.kirill.request.limiter.commons.Request;
import ru.mikheev.kirill.request.limiter.commons.RequestProcessor;
import ru.mikheev.kirill.request.limiter.commons.Response;
import ru.mikheev.kirill.request.limiter.commons.Status;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketFilter implements RequestProcessor {

    private final Map<String, TokenBucket> bucketsByUser = new ConcurrentHashMap<>();

    private final int bucketSize;
    private final long refillTimeMillis;
    private final int refillCount;

    private final RequestProcessor delegate;

    public TokenBucketFilter(RequestProcessor delegate, int bucketSize, long refillTimeMillis, int refillCount) {
        this.delegate = delegate;
        this.bucketSize = bucketSize;
        this.refillTimeMillis = refillTimeMillis;
        this.refillCount = refillCount;
    }

    @Override
    public Response process(Request request) {
        String name = request.getHeaders().get("name");
        TokenBucket tokenBucket = bucketsByUser.computeIfAbsent(name, this::createTokenBucket);
        if (tokenBucket.requestAllowed()) {
            return delegate.process(request);
        }
        return errorResult();
    }

    private TokenBucket createTokenBucket(String name) {
        return new TokenBucket();
    }

    private Response errorResult() {
        return new Response(
                Status.TOO_MANY_REQUESTS,
                Collections.emptyMap(),
                null
        );
    }

    private class TokenBucket {

        long lastRefillTimeStamp;
        int tokenStorage;

        Lock lock = new ReentrantLock();

        private TokenBucket() {
            lastRefillTimeStamp = System.currentTimeMillis();
            tokenStorage = bucketSize;
        }

        boolean requestAllowed() {
            lock.lock();
            try {
                if (tokenStorage > 0) {
                    tokenStorage--;
                    return true;
                }
                if (refill()) {
                    tokenStorage--;
                    return true;
                }
                return false;
            } finally {
                lock.unlock();
            }
        }

        boolean refill() {
            int refillsCountAfterLastRefill = (int)((System.currentTimeMillis() - lastRefillTimeStamp) / refillTimeMillis);
            if (refillsCountAfterLastRefill > 0) {
                lastRefillTimeStamp += refillsCountAfterLastRefill * refillTimeMillis;
                tokenStorage = Math.min(refillsCountAfterLastRefill * refillCount, bucketSize);
                return true;
            }
            return false;
        }
    }
}
