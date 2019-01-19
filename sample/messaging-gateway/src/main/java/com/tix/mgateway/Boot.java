package com.tix.mgateway;

import com.tix.mgateway.mo.MOFlow;

public class Boot {

    public static void main(String[] args) {
        MOFlow moFlow = new MOFlow();
        moFlow.init();
    }
    
}
