package com.cryptochenger.services;

import com.cryptochenger.aop.AccessCheckType;
import com.cryptochenger.aop.Accessible;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessCheckService {
    boolean check(HttpServletRequest request, Accessible accessible);

    AccessCheckType getType();
}
