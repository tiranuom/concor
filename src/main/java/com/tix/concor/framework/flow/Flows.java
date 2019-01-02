package com.tix.concor.framework.flow;

import com.tix.concor.framework.Context;
import com.tix.concor.framework.taskWrapper.TaskWrapper;

public class Flows {

    public static <A> FlowBuilder<A, A> create(String id) {

        TaskWrapper<A, A> taskWrapper = new TaskWrapper<A, A>(id + ":INIT") {
            @Override
            protected void applyNext(A a, Context context) {
                nextTask.apply(a, context);
            }
        };
        Flow<A> flow = new Flow<>(taskWrapper, id);

        FlowBuilder<A, A> flowBuilder = new FlowBuilder<>(taskWrapper, null, flow);

        FlowManager.getInstance().register(flow);

        return flowBuilder;
    }

}
