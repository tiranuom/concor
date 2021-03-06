package com.tix.concor.core.framework;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class Join {

    private static final EventTranslatorTwoArg<JoinTransferEvent, Runnable, Context> TRANSLATOR = (joinEvent, l, runnable, context) -> {
        joinEvent.runnable = runnable;
        joinEvent.context = context;
    };

    private RingBuffer<JoinTransferEvent> ringBuffer;
    private JoinEventFactory joinEventFactory = new JoinEventFactory();
    private Disruptor<JoinTransferEvent> disruptor;
    private int initialBufferSize;
    private Executor executor;
    private String joinId;
    private String taskId;
    private JoinType joinType;
    private JoinTarget target;

    public Join(int initialBufferSize, Executor executor, String joinId, JoinType joinType, JoinTarget target) {

        this.initialBufferSize = initialBufferSize;
        this.executor = executor;
        this.joinId = joinId;
        this.joinType = joinType;
        this.target = target;
    }

    public void init() {
        disruptor = new Disruptor<>(joinEventFactory, initialBufferSize, executor);
        disruptor.handleEventsWith(new JoinEventHandler());
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    public synchronized void destroy() {
        disruptor.shutdown();
    }

    public long getBufferSize() {
        return initialBufferSize - ringBuffer.remainingCapacity();
    }

    public long getActiveThreadCount() {
        if (joinType == JoinType.SINGLE_THREADED) return 1;
        return ((ThreadPoolExecutor) executor).getQueue().size();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public JoinTarget getTarget() {
        return target;
    }

    public synchronized void handle(Runnable runnable, Context context) {
        ringBuffer.publishEvent(TRANSLATOR, runnable, context);
    }

    private static class JoinTransferEvent {
        private Runnable runnable;
        private Context context;
    }

    private class JoinEventFactory implements EventFactory<JoinTransferEvent> {

        @Override
        public JoinTransferEvent newInstance() {
            return new JoinTransferEvent();
        }
    }

    private class JoinEventHandler implements EventHandler<JoinTransferEvent> {

        @Override
        public void onEvent(JoinTransferEvent joinEvent, long l, boolean b) throws Exception {
            joinEvent.context.hitJoin(Join.this);
            try {
                joinEvent.runnable.run();
            } catch (Throwable t) {
                System.out.println("----------------------------------------------------------------");
                t.printStackTrace();
                System.out.println("----------------------------------------------------------------");
            }
        }
    }

    public String getJoinId() {
        return joinId;
    }

}
