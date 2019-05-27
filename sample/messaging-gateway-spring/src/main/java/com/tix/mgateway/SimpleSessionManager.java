package com.tix.mgateway;

import com.tix.mgateway.mo.domain.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleSessionManager implements SessionManagerI {

    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public Session getSession(String sessionKey) {
        return sessionMap.computeIfAbsent(sessionKey, Session::new);
    }


}
