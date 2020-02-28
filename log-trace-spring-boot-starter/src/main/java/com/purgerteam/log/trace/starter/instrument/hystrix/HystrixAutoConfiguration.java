package com.purgerteam.log.trace.starter.instrument.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class HystrixAutoConfiguration {

    @PostConstruct
    public void init() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new TraceHystrixConcurrencyStrategy());
    }
}
