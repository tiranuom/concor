package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.task.SideEffect;

public class SideEffectTaskWrapper<A> extends TaskWrapper<A, A> {

    private SideEffect<A> task;
    public SideEffectTaskWrapper(SideEffect<A> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (context.isSuccessful()) {
            FlowTraceLog.trace("SIDE_EFFECT|{}|{}|{}", id, context.getId(), a);
            try {
                task.apply(a);
                nextTask.apply(a, context);
            } catch (Throwable throwable) {
                context.setThrowable(throwable, a);
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(null, context);
        }
    }

    @Override
    protected String eventType() {
        return "side-effect";
    }
}
