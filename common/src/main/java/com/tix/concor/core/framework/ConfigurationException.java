package com.tix.concor.core.framework;

public class ConfigurationException extends Exception {
    private String message;

    public ConfigurationException(String message, String id) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
