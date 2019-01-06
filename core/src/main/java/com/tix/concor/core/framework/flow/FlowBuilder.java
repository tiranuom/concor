package com.tix.concor.core.framework.flow;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.task.*;
import com.tix.concor.core.framework.taskWrapper.*;

public class FlowBuilder<S, A> {

    private FlowBuilder<S, ?> previousFlowBuilder;
    private TaskWrapper<?, A> previousTaskWrapper;
    private Flow<S> flow;

    FlowBuilder(TaskWrapper<?, A> previousTaskWrapper, FlowBuilder<S, ?> previousFlowBuilder, Flow<S> flow) {
        this.previousTaskWrapper = previousTaskWrapper;
        this.previousFlowBuilder = previousFlowBuilder;
        this.flow = flow;
    }

    public <B> FlowBuilder<S, B> map(SimpleTask<A, B> task) {
        SimpleTaskWrapper<A, B> nextTaskWrapper = new SimpleTaskWrapper<>(task, flow.getId() + ":%d:map");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public <B> FlowBuilder<S, B> map(SimpleTask<A, B> task, String id) {
        SimpleTaskWrapper<A, B> nextTaskWrapper = new SimpleTaskWrapper<>(task, flow.getId() + ":" + id + ":map");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public <B> FlowBuilder<S, B> mapSynchronized(SynchronizedTask<A, B> task) {
        SynchronizedTaskWrapper<A, B> nextTaskWrapper = new SynchronizedTaskWrapper<>(task, flow.getId() + ":%d:sync");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }
    
    public <B> FlowBuilder<S, B> mapSynchronized(SynchronizedTask<A, B> task, String id) {
        SynchronizedTaskWrapper<A, B> nextTaskWrapper = new SynchronizedTaskWrapper<>(task, flow.getId() + ":" + id + ":sync");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }
    
    public <B> FlowBuilder<S, B> mapAsynchronous(AsynchronousTask<A, B> task) {
        AsynchronousTaskWrapper<A, B> nextTaskWrapper = new AsynchronousTaskWrapper<>(task, flow.getId() + ":%d:sync");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }
    
    public <B> FlowBuilder<S, B> mapAsynchronous(AsynchronousTask<A, B> task, String id) {
        AsynchronousTaskWrapper<A, B> nextTaskWrapper = new AsynchronousTaskWrapper<>(task, flow.getId() + ":" + id + ":sync");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public <B> FlowBuilder<S, B> bind(TransitionTask<A, B> task) {
        TransitionTaskWrapper<A, B> nextTaskWrapper = new TransitionTaskWrapper<>(task, flow.getId() + ":%d:trans");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public <B> FlowBuilder<S, B> bind(TransitionTask<A, B> task, String id) {
        TransitionTaskWrapper<A, B> nextTaskWrapper = new TransitionTaskWrapper<>(task, flow.getId() + ":" + id + ":trans");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    @SuppressWarnings("unchecked")
    public <B> FlowBuilder<S, B> join(FlowBuilder<A, B> flowBuilder) {
        FlowBuilder<A, ?> iteratingFlowBuilder = flowBuilder;

        do {
            iteratingFlowBuilder = iteratingFlowBuilder.previousFlowBuilder;
        } while (previousFlowBuilder.previousTaskWrapper != null);

        //Always flow builder starts with <A, A>
        previousTaskWrapper.setNextTask(((TaskWrapper<A, ?>) iteratingFlowBuilder.previousTaskWrapper));

        return new FlowBuilder<>(flowBuilder.previousTaskWrapper, this, flow);
    }

    public FlowBuilder<S, A> forEach(SideEffect<A> task) {
        SideEffectTaskWrapper<A> nextTaskWrapper = new SideEffectTaskWrapper<>(task, flow.getId() + ":%d:side");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public FlowBuilder<S, A> forEach(SideEffect<A> task, String id) {
        SideEffectTaskWrapper<A> nextTaskWrapper = new SideEffectTaskWrapper<>(task, flow.getId() + ":" + id + ":side");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public FlowBuilder<S, A> catching(CatchTask<A> catchTask) {
        CatchTaskWrapper<A> nextTaskWrapper = new CatchTaskWrapper<>(catchTask, flow.getId() + ":%d:catch");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public FlowBuilder<S, A> catching(CatchTask<A> catchTask, String id) {
        CatchTaskWrapper<A> nextTaskWrapper = new CatchTaskWrapper<>(catchTask, flow.getId() + ":" + id + ":catch");
        previousTaskWrapper.setNextTask(nextTaskWrapper);
        return new FlowBuilder<>(nextTaskWrapper, this, flow);
    }

    public Flow<S> build() {
        TaskWrapper<A, Void> finalizedTask = new TaskWrapper<A, Void>(flow.getId() + ":%d:COMPLETE") {
            @Override
            protected void applyNext(Object o, Context context) {
                context.complete();
            }
        };
        previousTaskWrapper.setNextTask(finalizedTask);
        FlowManager.getInstance().register(flow);
        return flow;
    }
}
