package com.tix.simulator.perfsimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class PerfSimulatorApplicationRunner implements WrapperListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfSimulatorApplicationRunner.class);

    public static void main(String[] args) {
        WrapperManager.start(new PerfSimulatorApplicationRunner(), args);
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
        LOGGER.info("============== STARTING PERF-SIMULATOR ===========");
        SpringApplication.run(PerfSimulatorApplication.class, arg0);
        LOGGER.info("============== PERF-SIMULATOR STARTED ============");
        return null;
    }

    @Override
    public int stop(int exitCode) {
        LOGGER.info("============== Stopping PERF-SIMULATOR ===========");
        return exitCode;
    }

}
