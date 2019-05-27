package com.tix.simulator.perfsimulator;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MessageProvider {

    @Value("${message.template}")
    private String messageTemplate;

    private PrimitiveIterator.OfLong longStream = new Random().longs(0, 100_000).iterator();
    private Iterator<String> prefixStream;
    private Iterator<String> messageProvider;
    private PrimitiveIterator.OfLong values;

    @PostConstruct
    public void init() {
        List<String> prefix = Arrays.asList("070", "071", "071", "072", "075", "076", "077", "077", "078");
        Collections.shuffle(prefix);

        prefixStream = Stream.iterate(0, i -> ++i & 0x7).map(prefix::get).iterator();

        messageProvider = new Random().ints().map(i -> i % 2).mapToObj(String::valueOf).iterator();

        values = new Random().longs().iterator();
    }

    public String getMessage() {

        String prefix = prefixStream.next();
        Long suffix = longStream.next();
        String message = messageProvider.next();
        Long amount = values.next() % 10000;


        return messageTemplate.replace("{msisdn}", prefix + "000" + String.format("%04d", suffix))
                .replace("{shortcode}", prefix)
                .replace("{message}", message)
                .replace("{operation}", message.equals("0") ? "CREDIT" : "DEBIT")
                .replace("{value}", String.valueOf(amount));
    }

}

