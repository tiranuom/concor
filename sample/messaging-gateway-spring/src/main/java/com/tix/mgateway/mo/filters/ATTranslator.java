package com.tix.mgateway.mo.filters;

import com.google.gson.Gson;
import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ATTranslator implements SimpleTask<MOMessage, MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ATTranslator.class);

    private static Gson GSON = new Gson();

    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {

        logger.debug("Translating message to remote request format");

        moMessage.setResponse(GSON.toJson(moMessage));

        logger.debug("Message Translation completed");
        return moMessage;
    }
}
