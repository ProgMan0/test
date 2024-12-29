package com.cryptochenger.services.checker;

import com.cryptochenger.aop.AccessCheckType;
import com.cryptochenger.aop.Accessible;
import com.cryptochenger.services.AccessCheckService;
import com.cryptochenger.services.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import java.security.Principal;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractAccessCheckerService<T extends AbstractAccessCheckerService.AccessData> implements AccessCheckService {

    @Override
    public boolean check(HttpServletRequest request, Accessible accessible) {
        if (!isAuthenticated()) {
            throw new RuntimeException("not authenticated !");
        }

        T accessData = getAccessData(request);
        return check(accessData);
    }

    @SuppressWarnings("unchecked")
    protected <V> V getFromPathVariable(HttpServletRequest request, String key, Function<String, V> function) {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return function.apply(pathVariables.get(key));
    }

    protected AppUserDetails getCurrentAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails details) {
            return details;
        }

        throw new RuntimeException("not good details");
    }


    @Override
    public AccessCheckType getType() {
        return AccessCheckType.EVENT;
    }

    private boolean isAuthenticated() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return SecurityContextHolder.getContext() != null &&
                SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AppUserDetails;
    }

    protected abstract T getAccessData(HttpServletRequest httpServletRequest);

    protected abstract boolean check(T accessData);

    interface AccessData {}
}
