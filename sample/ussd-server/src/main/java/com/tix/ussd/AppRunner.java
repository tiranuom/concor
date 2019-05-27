package com.tix.ussd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class AppRunner implements WrapperListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);

    public static void main(String[] args) {
        WrapperManager.start(new AppRunner(), args);
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
        LOGGER.info("============== STARTING MESSAGING-GATEWAY ===========");
        SpringApplication.run(UssdServerApplication.class, arg0);
        LOGGER.info("============== MESSAGING-GATEWAY STARTED ============");
        return null;
    }

    @Override
    public int stop(int exitCode) {
        LOGGER.info("============== Stopping MESSAGING-GATEWAY ===========");
        return exitCode;
    }

}
