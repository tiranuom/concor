package com.tix.concor.framework;

public abstract class Context {

    private Throwable throwable;

    public void hitJoin(Join join) {

    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isSuccessful() {
        return throwable == null;
    }

    public void hitTask(String taskWrapperId) {

    }

    public void complete() {

    }
}
