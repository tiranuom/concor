package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.concor.core.framework.task.TransitionTask;
import com.tix.mgateway.mo.domain.MOMessage;
import okhttp3.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

public class ATMessageSender implements TransitionTask<MOMessage, MOMessage> {

    private OkHttpClient client;

    @PostConstruct
    public void init() {
        client = new OkHttpClient();
    }


    @Override
    public void apply(MOMessage moMessage, Continuation<MOMessage> continuation) throws Throwable {

        Request request = new Request.Builder().url(moMessage.getSession().getRemoteInfo().getServerInfo().get(0).getUrl())
                .post(RequestBody.create(MediaType.get("text/plain"), moMessage.getResponse()))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                continuation.onError(() -> e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                continuation.continuing(() -> moMessage);
            }
        });

//        Thread.sleep(10);
//        continuation.continuing(() -> moMessage);
    }
}
