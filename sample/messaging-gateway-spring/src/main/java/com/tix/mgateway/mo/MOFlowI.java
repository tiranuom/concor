package com.tix.mgateway.mo;

import javax.annotation.PostConstruct;

public interface MOFlowI {
    @PostConstruct
    void init();

    void apply(String moMessage);
}
