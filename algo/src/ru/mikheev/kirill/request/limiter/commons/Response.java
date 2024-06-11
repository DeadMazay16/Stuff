package ru.mikheev.kirill.request.limiter.commons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Response {
    private final Status status;
    private final Map<String, String> headers;
    private final Object body;
}
