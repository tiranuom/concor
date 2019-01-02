package com.tix.concor.framework.taskWrapper;

import com.tix.concor.framework.Context;
import com.tix.concor.framework.Join;

import java.util.Objects;

public abstract class TaskWrapper<A, B> {

    protected TaskWrapper<B, ?> nextTask;

    private Join join = null;

    public TaskWrapper(String id) {
        this.id = id;
    }

    private String id;

    public void apply(A a, Context context) {
        context.hitTask(id);
        boolean executed = false;
        synchronized (this) {
            if (join != null) {
                join.handle(() -> applyNext(a, context), context);
                executed = true;
            }
        }
        if (!executed) {
            applyNext(a, context);
        }
    }

    @SuppressWarnings("unchecked")
    protected abstract void applyNext(A a, Context context);

    public void setNextTask(TaskWrapper<B, ?> nextTask) {
        this.nextTask = nextTask;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Join assignJoinIfMatched(String id, Join join) {
        if (Objects.equals(this.id, id)) {
            if (join == null) {
                Join existingJoin = this.join;
                if (existingJoin != null) {
                    existingJoin.setTaskId(null);
                }
                synchronized (this) {
                    this.join = null;
                }
                return existingJoin;
            } else {
                this.join = join;
                this.join.setTaskId(this.id);
            }
        } else {
            if (nextTask != null)
                return nextTask.assignJoinIfMatched(id, join);
            else return null;
        }
        return null;
    }

    public void setIndexes(int index) {
        this.id = String.format(id, index);
        if (nextTask != null) {
            nextTask.setIndexes(index + 1);
        }
    }
}
