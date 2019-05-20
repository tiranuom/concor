package com.tix.simulator.perfsimulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    @Value("${tps}")
    private long tps;

    @Value("${period}")
    private long incrementPeriod;

    @Value("${step}")
    private long incrementStep;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private MessageProvider messageProvider;
    private ScheduledFuture<?> scheduledFuture;


    @PostConstruct
    public void init() {
        reset(tps);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            tps += incrementStep;
            reset(tps);
        }, incrementPeriod, incrementPeriod, TimeUnit.SECONDS);
    }


    public void reset(long tps) {

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }

        long delay = 1000000 / tps; //in microseconds
        scheduledFuture = Executors.newScheduledThreadPool(128)
                .scheduleAtFixedRate(this::execute, 100, delay, TimeUnit.MICROSECONDS);
    }

    private void execute() {
        messageSender.sendMessage(messageProvider.getMessage());
    }
}
