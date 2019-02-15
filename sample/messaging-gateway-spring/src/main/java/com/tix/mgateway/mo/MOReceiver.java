package com.tix.mgateway.mo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

@Service
public class MOReceiver {

    private HttpServer httpServer;

    @Autowired
    private MOFlow moFlow;


    @PostConstruct
    public void init() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(8888), 0);
        httpServer.createContext("/ussd/in", httpExchange -> {
            try {
                InputStream requestBody = httpExchange.getRequestBody();

                StringWriter stringWriter = new StringWriter();

                IOUtils.copy(new InputStreamReader(requestBody), stringWriter);

                moFlow.apply(stringWriter.toString());

                String response = "OK";

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream responseBody = httpExchange.getResponseBody();
                responseBody.write(response.getBytes(Charset.forName("UTF-8")));
                requestBody.close();
                httpExchange.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }

        });
        httpServer.setExecutor(null);
        httpServer.start();
    }

}
