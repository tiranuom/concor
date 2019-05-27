package com.tix.ussd;

import com.tix.ussd.domain.UssdMessage;
import com.tix.ussd.domain.UssdSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UssdSessionManager {

    private Map<String, UssdSession> sessionMap = new ConcurrentHashMap<>();

    public UssdSession apply(UssdMessage ussdMessage) {
        UssdSession ussdSession = sessionMap.computeIfAbsent(ussdMessage.getMsisdn(), UssdSession::new);
        ussdSession.setUssdMessage(ussdMessage);
        return ussdSession;
    }
}
