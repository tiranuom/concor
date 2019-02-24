package com.tix.simulator.perfsimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageCounter {

    private static final Logger logger = LoggerFactory.getLogger("TPS_LOG");

    private AtomicInteger success = new AtomicInteger(0);
    private AtomicInteger failed = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::log, 1, 1, TimeUnit.SECONDS);
    }

    private void log() {
        int successCount = success.getAndSet(0);
        int failedCount = failed.getAndSet(0);

        logger.info("" + successCount + "|" + failedCount + "|" + (successCount + failedCount));
    }

    public void onSuccess() {
        success.incrementAndGet();
    }

    public void onFail() {
        failed.incrementAndGet();
    }
}
