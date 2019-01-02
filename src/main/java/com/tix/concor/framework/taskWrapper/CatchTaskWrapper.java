package com.tix.concor.framework.taskWrapper;

import com.tix.concor.framework.Context;
import com.tix.concor.framework.task.CatchTask;

public class CatchTaskWrapper<A> extends TaskWrapper<A, A>{

    private CatchTask<A> catchTask;

    public CatchTaskWrapper(CatchTask<A> catchTask, String id) {
        super(id);
        this.catchTask = catchTask;
    }

    @Override
    protected void applyNext(A a, Context context) {
        if (!context.isSuccessful()) {
            try {
                nextTask.apply(catchTask.onError(context.getThrowable()), context);
            } catch (Throwable throwable) {
                context.setThrowable(throwable);
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(a, context);
        }
    }
}
