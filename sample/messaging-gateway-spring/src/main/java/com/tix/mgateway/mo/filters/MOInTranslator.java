package com.tix.mgateway.mo.filters;

import com.google.gson.Gson;
import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MOInTranslator implements SimpleTask<String, MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(MOInTranslator.class);

    private static final Gson GSON = new Gson();

    @Override
    public MOMessage apply(String s) throws Throwable {
        logger.debug("Translating message [{}]", s);
        Msg msg = GSON.fromJson(s, Msg.class);
        return new MOMessage(msg.msisdn, msg.shortCode, msg.message);
    }
    
    private class Msg {
        private String msisdn, shortCode, message;

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getShortCode() {
            return shortCode;
        }

        public void setShortCode(String shortCode) {
            this.shortCode = shortCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
