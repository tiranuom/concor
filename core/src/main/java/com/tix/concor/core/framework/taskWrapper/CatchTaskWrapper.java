package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.Context;
import com.tix.concor.core.framework.task.CatchTask;

public class CatchTaskWrapper<A> extends TaskWrapper<A, A>{

    private CatchTask<A> catchTask;

    public CatchTaskWrapper(CatchTask<A> catchTask, String id) {
        super(id);
        this.catchTask = catchTask;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (!context.isSuccessful()) {
            FlowTraceLog.trace("HANDLE_ERROR|{}|{}|{}", id, context.getId(), context.recover(), context.getThrowable());
            try {
                nextTask.apply(catchTask.onError(context.getThrowable()), context);
            } catch (Throwable throwable) {
                context.setThrowable(throwable, context.recover());
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(a, context);
        }
    }

    @Override
    protected String eventType() {
        return "error";
    }
}
