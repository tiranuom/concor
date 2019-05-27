package com.tix.simulator.perfsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PerfSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerfSimulatorApplication.class, args);
    }

    @Bean
    public MessageProvider messageProvider() {
        return new MessageProvider();
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender();
    }

    @Bean
    public Scheduler scheduler() {
        return new Scheduler();
    }

    @Bean
    public MessageCounter messageCounter() {
        return new MessageCounter();
    }
}
