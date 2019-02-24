package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.SniffingContext;
import com.tix.concor.core.framework.StaticContext;
import stormpot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class FlowManager {

    private static final FlowManager FLOW_MANAGER = new FlowManager();
//    private static final StaticContext STATIC_CONTEXT = new StaticContext();

    private static final BlazePool<StaticContext> contextPool;
    private static final Timeout timeout = new Timeout(5, TimeUnit.MINUTES);

    static {
        ContextAllocator contextAllocator = new ContextAllocator();
        Config<StaticContext> staticContextConfig = new Config<StaticContext>().setSize(1).setAllocator(contextAllocator);
        contextPool = new BlazePool<>(staticContextConfig);
    }

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
        flow.init(getContextSupplier(flow), flowManagementLogic.getJoinGenerator());
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
            try {
                return contextPool.claim(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException("Cannot create context");
            }
        };
    }

    public FlowManagementLogic getFlowManagementLogic() {
        return flowManagementLogic;
    }

    interface ContextSupplier extends Supplier<Context> {

    }

    public static class FlowManagerConfig {
        //Should be in a pattern of 2^x
        private int samplePeriod = 1;
        private long initialBfferSize = 1024;
        private String host = "localhost";
        private int port = 12099;
        private int bufferSize = 1024;

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

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }
    }

    private static class ContextAllocator implements Allocator<StaticContext> {

        @Override
        public StaticContext allocate(Slot slot) throws Exception {
            return new StaticContext(slot);
        }

        @Override
        public void deallocate(StaticContext poolable) throws Exception {
            poolable.release();
        }
    }
}
