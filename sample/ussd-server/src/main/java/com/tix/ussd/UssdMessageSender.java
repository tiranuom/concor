package com.tix.ussd;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.ussd.domain.UssdSession;
import okhttp3.*;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class UssdMessageSender {

    private OkHttpClient client;

    @PostConstruct
    public void init() {
        client = new OkHttpClient();
        client.dispatcher().setMaxRequestsPerHost(400);
    }

    public void send(UssdSession ussdSession, Continuation<UssdSession> continuation) {

        Request request = new Request.Builder().url("http://localhost:5555/server/hutch_S1")
                .post(RequestBody.create(MediaType.get("text/plain"), ussdSession.getMessage()))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                continuation.onError(() -> e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                continuation.continuing(() -> ussdSession);
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }
}
