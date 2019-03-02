package com.tix.concor.core.framework;

import stormpot.Poolable;
import stormpot.Slot;

public class StaticContext extends Context implements Poolable {

    private Slot slot;

    public StaticContext(Slot slot) {
        this.slot = slot;
    }

    @Override
    public void release() {
        complete();
    }

    @Override
    public void complete() {
        try {
            super.complete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (slot != null) {
                slot.release(this);
            }
        }
    }
}
