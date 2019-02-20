package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.*;
import com.tix.concor.core.framework.task.Continuation;
import com.tix.concor.core.framework.task.TransitionTask;

import java.util.concurrent.Callable;

public class TransitionTaskWrapper<A, B> extends TaskWrapper<A, B> {

    private TransitionTask<A, B> task;

    private Join continuationJoin = null;

    public TransitionTaskWrapper(TransitionTask<A, B> task, String id) {
        super(id);
        this.task = task;
    }

    @Override
    public void init(JoinAssignmentFunction joinAssignmentFunction) {
        joinAssignmentFunction.accept(JoinType.CACHED);
        nextTask.init(joinAssignmentFunction.newSecondaryAssignmentFunction());
    }

    @Override
    public void assignJoin(Join join) throws ConfigurationException {
        if (join.getTarget() == JoinTarget.PRIMARY) super.assignJoin(join);
        else {
            this.continuationJoin = join;
            this.continuationJoin.setTaskId(this.id);
        }
    }

    @Override
    public Join assignJoinIfMatched(String id, Join join, JoinType previousJoinType) throws ConfigurationException {
        return super.assignJoinIfMatched(id, join, previousJoinType);
    }

    @Override
    protected void applyNext(A a, Context context) {
        FlowTraceLog.trace("ASYNCHRONOUS_REMOTE|{}|{}", id, a);
        if (context.isSuccessful()) {
            try {
                task.apply(a, new Continuation<B>() {
                    @Override
                    public void continuing(Callable<B> callable) {
                        if (continuationJoin != null) {
                            continuationJoin.handle(() -> handleContinuation(callable), context);
                        } else {
                            handleContinuation(callable);
                        }
                    }

                    private void handleContinuation(Callable<B> callable) {
                        try {
                            FlowTraceLog.trace("ASYNCHRONOUS_REMOTE_SUCCESS|{}|{}|{}", id, context.getId(), a);
                            nextTask.apply(callable.call(), context);
                        } catch (Exception e) {
                            context.setThrowable(e);
                            nextTask.apply(null, context);
                        }
                    }

                    @Override
                    public void onError(Callable<Throwable> callable) {
                        if (continuationJoin != null) {
                            continuationJoin.handle(() -> handleError(callable), context);
                        } else {
                            handleError(callable);
                        }
                    }

                    private void handleError(Callable<Throwable> callable) {
                        try {
                            FlowTraceLog.trace("ASYNCHRONOUS_REMOTE_ERROR|{}|{}", id, a);
                            context.setThrowable(callable.call());
                        } catch (Exception e) {
                            context.setThrowable(e);
                        }
                        nextTask.apply(null, context);
                    }
                });
            } catch (Throwable throwable) {
                context.setThrowable(throwable);
                nextTask.apply(null, context);
            }
        } else {
            nextTask.apply(null, context);
        }
    }

    @Override
    protected String eventType() {
        return "asynchronous-remote";
    }
}
