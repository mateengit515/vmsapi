package com.vmsapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {
    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof String) {
            return (String) auth.getPrincipal();
        }
        return null;
    }

    public static String getCurrentRole(HttpServletRequest request) {
        Object role = request.getAttribute("role");
        return role != null ? role.toString() : null;
    }
}

