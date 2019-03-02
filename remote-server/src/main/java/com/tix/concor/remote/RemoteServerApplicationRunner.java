package com.tix.concor.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class RemoteServerApplicationRunner implements WrapperListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServerApplicationRunner.class);

    public static void main(String[] args) {
        WrapperManager.start(new RemoteServerApplicationRunner(), args);
    }


    @Override
    public void controlEvent(int event) {
        if ((event == WrapperManager.WRAPPER_CTRL_LOGOFF_EVENT) && WrapperManager.isLaunchedAsService()) {
            // Ignore
        } else {
            WrapperManager.stop(0);
            // Will not get here.
        }
    }

    @Override
    public Integer start(String[] arg0) {
        LOGGER.info("============== STARTING REMOTE-SERVER ===========");
        SpringApplication.run(RemoteServerApplication.class, arg0);
        LOGGER.info("============== REMOTE-SERVER STARTED ============");
        return null;
    }

    @Override
    public int stop(int exitCode) {
        LOGGER.info("============== Stopping REMOTE-SERVER ===========");
        return exitCode;
    }
    
}
