package com.tix.mgateway.mo.filters.router.domain;

public class Route {

    private String prefix;
    private String destination;

    public Route(String prefix, String destination) {
        this.prefix = prefix;
        this.destination = destination;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDestination() {
        return destination;
    }
}
