package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.concor.core.framework.task.TransitionTask;
import com.tix.mgateway.mo.domain.MOMessage;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ATMessageSender implements TransitionTask<MOMessage, MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ATMessageSender.class);

    private OkHttpClient client;

    @PostConstruct
    public void init() {
        client = new OkHttpClient();
        client.dispatcher().setMaxRequestsPerHost(100);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() ->
                logger.info("Connection Count [{}]", client.connectionPool().connectionCount()),  1, 1, TimeUnit.SECONDS);
    }


    @Override
    public void apply(MOMessage moMessage, Continuation<MOMessage> continuation) throws Throwable {

        logger.debug("Sending message to remote server");

        Request request = new Request.Builder().url(moMessage.getSession().getRemoteInfo().getServerInfo().get(0).getUrl())
                .post(RequestBody.create(MediaType.get("text/plain"), moMessage.getResponse()))
                .build();

        logger.debug("Message preparation completed");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("An error occurred while sending the message");
                continuation.onError(() -> e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.debug("Message successfully delivered");
                continuation.continuing(() -> moMessage);
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });

        logger.debug("Message sent to the remote server");

//        Thread.sleep(10);
//        continuation.continuing(() -> moMessage);
    }
}
