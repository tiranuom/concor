package com.tix.simulator.perfsimulator;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Stream;

public class MessageProvider {

    @Value("message.template")
    private String messageTemplate;

    private PrimitiveIterator.OfLong longStream = new Random().longs().iterator();
    private Iterator<String> prefixStream;

    @PostConstruct
    public void init() {
        List<String> prefix = Arrays.asList("070","071","071","072","075","076","077","077","078");
        Collections.shuffle(prefix);

        prefixStream = Stream.iterate(0, i -> i & 0x8).map(prefix::get).iterator();

    }

    public String getMessage() {

        String prefix = prefixStream.next();
        Long suffix = longStream.next();

        return messageTemplate.replace("{msisdn}", String.format("%s00%5d", prefix, suffix))
                .replace("{shortcode}", prefix)
                .replace("{message}", "test");
    }

}

