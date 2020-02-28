package com.purgerteam.log.trace.starter;

import com.purgerteam.log.trace.starter.filter.TraceFilter;
import com.purgerteam.log.trace.starter.filter.TraceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
public class TraceAutoConfiguration {

    @Bean
    public TraceFilter traceFilter() {
        return new TraceFilter();
    }

    @Bean
    public TraceHandler traceHandler(){
        return new TraceHandler();
    }

    @Bean
    public TraceContentFactory traceContentUtil() {
        return new TraceContentFactory();
    }
}
