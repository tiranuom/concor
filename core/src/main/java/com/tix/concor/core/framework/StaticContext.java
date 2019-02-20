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
        try {
            complete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            slot.release(this);
        }
    }
}
