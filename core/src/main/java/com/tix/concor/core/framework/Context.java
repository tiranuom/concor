package com.tix.concor.core.framework;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public abstract class Context {

    private static AtomicInteger counter = new AtomicInteger(0);
    private static Supplier<Long> idGenerator = () -> System.currentTimeMillis() << 16 + (counter.incrementAndGet() & 0xFF);

    private Long id = idGenerator.get();
    private Throwable throwable;
    private Object lastKnown;

    public void hitJoin(Join join) {

    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable, Object lastKnown) {
        this.throwable = throwable;
        this.lastKnown = lastKnown;
    }

    public boolean isSuccessful() {
        return throwable == null;
    }

    public void hitTask(String taskWrapperId) {

    }

    public void init() {

    }

    public void complete() {

    }

    public Long getId() {
        return id;
    }

    public Object recover() {
        return lastKnown;
    }
}
