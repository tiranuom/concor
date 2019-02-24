package com.tix.mgateway.mo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.undertow.Undertow;
import io.undertow.io.Receiver;
import io.undertow.server.BlockingHttpExchange;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class MOReceiver {

    private HttpServer httpServer;

    @Autowired
    private MOFlow moFlow;


    @PostConstruct
    public void init() throws IOException {

        Undertow server = Undertow.builder().addHttpListener(8888, "0.0.0.0")
                .setHandler(this::dispatch).build();

        server.start();

//        httpServer = HttpServer.create(new InetSocketAddress(8888), 0);
//        httpServer.setExecutor(Executors.newCachedThreadPool());
//        httpServer.createContext("/ussd/in", httpExchange -> {
//
//            try (InputStream requestBody = httpExchange.getRequestBody();
//                 InputStreamReader inputStreamReader = new InputStreamReader(requestBody);
//                 OutputStream responseBody = httpExchange.getResponseBody();
//            ) {
//                StringWriter stringWriter = new StringWriter();
//
//                IOUtils.copy(inputStreamReader, stringWriter);
//
//                moFlow.apply(stringWriter.toString());
//
//                String response = "OK";
//
//                httpExchange.sendResponseHeaders(200, response.length());
//                responseBody.write(response.getBytes(Charset.forName("UTF-8")));
//            } catch (Throwable t) {
//                t.printStackTrace();
//            } finally {
//                httpExchange.close();
//            }
//
//        });
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        httpServer.setExecutor(executor);
//
//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
//            System.out.println(((ThreadPoolExecutor) executor).getPoolSize());
//        }, 1, 1, TimeUnit.SECONDS);
//
//        httpServer.start();
    }

    private void dispatch(HttpServerExchange httpServerExchange) {
        httpServerExchange.getRequestReceiver().receiveFullString((httpServerExchange1, s) -> moFlow.apply(s));

        httpServerExchange.getResponseSender().send("OK");
    }

}
