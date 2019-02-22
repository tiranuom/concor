package com.tix.simulator.perfsimulator;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class MessageSender {

    private OkHttpClient okHttpClient;

    @Value("remote.url")
    private String remoteUrl;
    @Autowired
    private MessageCounter messageCounter;


    @PostConstruct
    public void init() {
        okHttpClient = new OkHttpClient();
    }

    public void sendMessage(String message) {
        Request request = new Request.Builder()
                .url(remoteUrl)
                .post(RequestBody.create(MediaType.parse("text/plain"), message))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageCounter.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                messageCounter.onFail();
            }
        });
    }

}