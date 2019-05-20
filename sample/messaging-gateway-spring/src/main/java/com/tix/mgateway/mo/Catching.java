package com.tix.mgateway.mo;

import com.tix.concor.core.framework.task.CatchTask;
import com.tix.mgateway.mo.domain.MOMessage;
import com.tix.mgateway.mo.filters.EndPointResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Catching implements CatchTask<MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(Catching.class);

    @Override
    public MOMessage onError(Throwable e) throws Throwable {
        logger.error("An error occurred while executing the request", e);
        return null;
    }
}
