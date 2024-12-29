package com.cryptochenger.configs;

import com.cryptochenger.aop.AccessCheckType;
import com.cryptochenger.services.AccessCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ProjectConfig {

    @Bean
    public Map<AccessCheckType, AccessCheckService> accessCheckServiceMap(Collection<AccessCheckService> checkServices) {
        return checkServices.stream().collect(Collectors.toMap(AccessCheckService::getType, Function.identity()));
    }
}
