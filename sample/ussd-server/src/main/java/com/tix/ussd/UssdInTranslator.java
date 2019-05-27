package com.tix.ussd;

import com.google.gson.Gson;
import com.tix.ussd.domain.UssdMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UssdInTranslator {

    private static final Logger logger = LoggerFactory.getLogger(UssdInTranslator.class);

    private static final Gson GSON = new Gson();

    public UssdMessage apply(String msg) throws Throwable {
        UssdMessage ussdMessage = GSON.fromJson(msg, UssdMessage.class);

        return ussdMessage;
    }
}
