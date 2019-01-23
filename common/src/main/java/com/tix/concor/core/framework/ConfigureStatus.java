package com.tix.concor.core.framework;

import java.io.Serializable;

public class ConfigureStatus implements Serializable {

    private boolean success;
    private String error;

    private ConfigureStatus(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public static ConfigureStatus success() {
        return new ConfigureStatus(true, null);
    }

    public static ConfigureStatus fail(String error) {
        return new ConfigureStatus(false, error);
    }
}
