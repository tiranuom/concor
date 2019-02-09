package com.tix.concor.core.framework;

import com.tix.concor.core.framework.taskWrapper.TaskWrapper;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JoinAssignmentFunction implements Consumer<JoinType> {

    private TaskWrapper taskWrapper;
    private Function<JoinType, Join> joinSupplier;

    public JoinAssignmentFunction(Function<JoinType, Join> joinSupplier) {
        this.joinSupplier = joinSupplier;
    }

    @Override
    public void accept(JoinType joinType) {
        try {
            if (taskWrapper != null) {
                System.out.println("Assigning " + joinType + " JOIN for taskwrapper " + taskWrapper.getId());
                taskWrapper.assignJoin(joinSupplier.apply(joinType));
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setTaskWrapper(TaskWrapper taskWrapper) {
        if (this.taskWrapper == null) {
            System.out.println("Setting task wrapper");
            this.taskWrapper = taskWrapper;
        }
    }

    public JoinAssignmentFunction newAssignmentFunction() {
        System.out.println("Generating assignment function");
        return new JoinAssignmentFunction(joinSupplier);
    }
}
