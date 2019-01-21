package com.tix.mgateway;

import com.tix.mgateway.mo.MOFlow;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Boot {

    public static void main(String[] args) {
        MOFlow moFlow = new MOFlow();
        moFlow.init();
    }
    
}
