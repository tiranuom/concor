package com.tix.mgateway.mo.domain;

import javax.print.attribute.standard.Destination;
import java.util.concurrent.atomic.AtomicInteger;

public class Session {

    private static AtomicInteger counter = new AtomicInteger(0);

    private String sessionId = String.valueOf(System.currentTimeMillis() & 0xffff) + (counter.incrementAndGet() & 0xff);
    private String sessionKey;
    private long lastUpdatedTime = System.currentTimeMillis();
    private String application;

    public Session(String sessionId) {
        this.sessionKey = sessionId;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public boolean isValid(long timeout) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > lastUpdatedTime + timeout) {
            return false;
        } else {
            this.lastUpdatedTime = currentTime;
            return true;
        }
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Session{");
        sb.append("sessionId='").append(sessionId).append('\'');
        sb.append(", sessionKey='").append(sessionKey).append('\'');
        sb.append(", lastUpdatedTime=").append(lastUpdatedTime);
        sb.append('}');
        return sb.toString();
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplication() {
        return application;
    }
}
