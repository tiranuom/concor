package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.Continuation;
import com.tix.concor.core.framework.task.TransitionTask;
import com.tix.mgateway.mo.domain.MOMessage;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ATMessageSender implements TransitionTask<MOMessage, MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ATMessageSender.class);

    private OkHttpClient client;

    private AtomicReference<DurationData> durationData = new AtomicReference<>(new DurationData());

    @PostConstruct
    public void init() {
        client = new OkHttpClient();
        client.dispatcher().setMaxRequestsPerHost(400);

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(
                        () -> logger.info("Connection Count [{}]| average time [{} ms]", client.connectionPool().connectionCount(), durationData.getAndSet(new DurationData()).avg())
                        , 1, 1, TimeUnit.SECONDS);
    }


    @Override
    public void apply(MOMessage moMessage, Continuation<MOMessage> continuation) throws Throwable {

        logger.debug("Sending message to remote server");

        Request request = new Request.Builder().url(moMessage.getSession().getRemoteInfo().getServerInfo().get(0).getUrl())
                .post(RequestBody.create(MediaType.get("text/plain"), moMessage.getResponse()))
                .build();

        logger.debug("Message preparation completed");

        long before = System.currentTimeMillis();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("An error occurred while sending the message");
                durationData.updateAndGet(data -> data.addDuration(System.currentTimeMillis() - before));
                continuation.onError(() -> e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.debug("Message successfully delivered");
                durationData.updateAndGet(data -> data.addDuration(System.currentTimeMillis() - before));
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

    private class DurationData {
        int count = 0;
        long totalDuration = 0;

        private DurationData addDuration(long duration) {
            count++;
            totalDuration += duration;
            return this;
        }

        private long avg() {
            return count != 0 ? totalDuration / count : 0;
        }
    }
}
