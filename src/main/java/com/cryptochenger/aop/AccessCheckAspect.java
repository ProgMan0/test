package com.cryptochenger.aop;

import com.cryptochenger.services.AccessCheckService;
import com.cryptochenger.services.checker.EventCheckerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class AccessCheckAspect {
    private final Map<AccessCheckType, AccessCheckService> accessCheckServiceMap;

    @Before("@annotation(accessible)")
    public void check(JoinPoint joinPoint, Accessible accessible) {
        if (accessible == null) {
            throw new RuntimeException("accessible is null");
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalArgumentException("RequestAttributes not present!");
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        AccessCheckService accessCheckService = accessCheckServiceMap.get(accessible.checkBy());

        if (!accessCheckService.check(request, accessible)) {
            throw new RuntimeException("no access to do this operation!");
        }
    }
}
