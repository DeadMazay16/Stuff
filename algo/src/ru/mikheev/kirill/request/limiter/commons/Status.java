package ru.mikheev.kirill.request.limiter.commons;

import lombok.Getter;

public enum Status {
    OK(200),
    TOO_MANY_REQUESTS(429);

    @Getter
    private final int code;

    Status(int code) {
        this.code = code;
    }
}
