package com.gmail.robertzylan.atiperaTask.configuration;

import com.gmail.robertzylan.atiperaTask.exceptions.HeaderValueNotAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class WrongApiCallInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String undesiredHeader = request.getHeader("Accept");
        if (!undesiredHeader.equals("application/json")) {
            throw new HeaderValueNotAllowed(String.format("Value of 'Accept' header should be 'application/json', not %s", undesiredHeader));
        }
        return true;
    }
}