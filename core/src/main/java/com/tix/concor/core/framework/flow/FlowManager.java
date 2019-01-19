package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.SniffingContext;
import com.tix.concor.core.framework.StaticContext;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.function.Supplier;

public class FlowManager {

    private static final FlowManager FLOW_MANAGER = new FlowManager();
    private static final StaticContext STATIC_CONTEXT = new StaticContext();

    private FlowManagerConfig config;
    private FlowManagementLogic flowManagementLogic = new RemoteFlowManagementLogic();

    private FlowManager() {
    }

    public static FlowManager getInstance() {
        return FLOW_MANAGER;
    }

    public void reconfigure(FlowManagerConfig config) {
        this.config = config;
    }

    public void setFlowManagementLogic(FlowManagementLogic flowManagementLogic) {
        this.flowManagementLogic = flowManagementLogic;
    }

    public void register(Flow flow) {
        flow.init(getContextSupplier(flow));
        flowManagementLogic.addFlow(flow.getId(), flow);
    }

    public void unregister(Flow flow) {
        this.unregister(flow.getId());
    }

    private void unregister(String id) {
        Flow flow = flowManagementLogic.removeFlow(id);
        //TODO do flow finalize work.
    }

    //TODO Refactor logic to send multiple requests.
    private ContextSupplier getContextSupplier(Flow flow) {
        return () -> {
            int nextIndex = flow.nextIndex();
            if ((nextIndex & config.samplePeriod) == 0 ||           //0th element
                    (nextIndex & config.samplePeriod) == 0b10 ||    //2nd element
                    (nextIndex & config.samplePeriod) == 0b100      //4th element
            ) {
                return new SniffingContext((joinStats, taskStats) -> {
                    flowManagementLogic.onStats(joinStats, taskStats, nextIndex, flow);
                });
            }
            return STATIC_CONTEXT;
        };
    }

    interface ContextSupplier extends Supplier<Context> {

    }

    public static class FlowManagerConfig {
        //Should be in a pattern of 2^x
        private int samplePeriod = 1;
        private long initialBfferSize = 1024;
        private String host = "0.0.0.0";
        private int port = 12099;

        public int getSamplePeriod() {
            return samplePeriod;
        }

        public void setSamplePeriod(int samplePeriod) {
            this.samplePeriod = samplePeriod;
        }

        public long getInitialBfferSize() {
            return initialBfferSize;
        }

        public void setInitialBfferSize(long initialBfferSize) {
            this.initialBfferSize = initialBfferSize;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
