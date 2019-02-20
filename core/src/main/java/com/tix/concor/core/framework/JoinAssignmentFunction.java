package com.tix.concor.core.framework;

import com.tix.concor.core.framework.taskWrapper.TaskWrapper;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class JoinAssignmentFunction implements Consumer<JoinType> {

    private TaskWrapper taskWrapper;
    private BiFunction<JoinType, JoinTarget, Join> joinSupplier;
    private JoinTarget joinTarget;

    public JoinAssignmentFunction(BiFunction<JoinType, JoinTarget, Join> joinSupplier, JoinTarget joinTarget) {
        this.joinSupplier = joinSupplier;
        this.joinTarget = joinTarget;
    }

    @Override
    public void accept(JoinType joinType) {
        try {
            if (taskWrapper != null) {
                System.out.println("Assigning " + joinType + " JOIN for taskwrapper " + taskWrapper.getId());
                taskWrapper.assignJoin(joinSupplier.apply(joinType, joinTarget));
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
        return new JoinAssignmentFunction(joinSupplier, JoinTarget.PRIMARY);
    }

    public JoinAssignmentFunction newSecondaryAssignmentFunction() {
        System.out.println("Generating secondary assignment function");
        return new JoinAssignmentFunction(joinSupplier, JoinTarget.SECONDARY);
    }
}
