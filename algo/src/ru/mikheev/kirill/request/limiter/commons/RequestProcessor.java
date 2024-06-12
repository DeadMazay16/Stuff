package ru.mikheev.kirill.request.limiter.commons;

import java.util.Collections;

public abstract class RequestProcessor {

    abstract public Response process(Request request);

    protected Response tooManyRequestsErrorResult() {
        return new Response(
                Status.TOO_MANY_REQUESTS,
                Collections.emptyMap(),
                null
        );
    }

}
