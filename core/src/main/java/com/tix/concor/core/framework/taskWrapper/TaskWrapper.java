package com.tix.concor.core.framework.taskWrapper;

import com.tix.concor.core.framework.*;

import java.util.List;
import java.util.Objects;

public abstract class TaskWrapper<A, B> {

    protected TaskWrapper<B, ?> nextTask;

    private Join join = null;

    public TaskWrapper(String id) {
        this.id = id;
    }

    protected String id;

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

    public Join assignJoinIfMatched(String id, Join join, JoinType previousJoinType) throws ConfigurationException {
        if (Objects.equals(this.id, id)) {
            this.assertJoinAssignable(join == null ? previousJoinType : join.getJoinType(), id);

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
                return nextTask.assignJoinIfMatched(id, join, this.join == null ? previousJoinType : this.join.getJoinType());
            else return null;
        }
        return null;
    }

    protected void assertJoinAssignable(JoinType joinType, String taskId) throws ConfigurationException {
        if (Objects.equals(taskId, id)) {
            if (this.nextTask != null) {
                assertLocalJoinAssignability(joinType);
                this.nextTask.assertJoinAssignable(joinType, taskId);
            }
        } else {
            if (this.join == null && this.nextTask != null) {
                assertLocalJoinAssignability(joinType);
                this.nextTask.assertJoinAssignable(joinType, taskId);
            }
        }
    }

    protected void assertLocalJoinAssignability(JoinType joinType) throws ConfigurationException {

    }

    public void setIndexes(int index) {
        this.id = String.format(id, index);
        if (nextTask != null) {
            nextTask.setIndexes(index + 1);
        }
    }

    protected abstract String eventType();

    public void collectSchema(List<TaskInfo> taskInfo) {
        taskInfo.add(new TaskInfo(id, eventType()));
        if (nextTask != null) {
            nextTask.collectSchema(taskInfo);
        }
    }
}
