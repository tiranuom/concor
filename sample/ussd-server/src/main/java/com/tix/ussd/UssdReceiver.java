package com.tix.ussd;

import com.sun.net.httpserver.HttpServer;
import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class UssdReceiver {

    private HttpServer httpServer;

    @Autowired
    private UssdFlowI ussdFlow;

    @PostConstruct
    public void init() throws IOException {

        Undertow server = Undertow.builder().addHttpListener(8888, "0.0.0.0")
                .setHandler(this::dispatch).build();

        server.start();
    }

    private void dispatch(HttpServerExchange httpServerExchange) {
        httpServerExchange.getRequestReceiver().receiveFullString((httpServerExchange1, s) -> ussdFlow.apply(s));

        httpServerExchange.getResponseSender().send("OK");
    }

}
