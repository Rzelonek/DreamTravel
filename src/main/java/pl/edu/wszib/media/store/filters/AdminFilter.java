package pl.edu.wszib.media.store.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.edu.wszib.media.store.model.User;
import pl.edu.wszib.media.store.session.SessionConstants;

import java.io.IOException;

public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession httpSession = req.getSession();

        // Check if the requested path is /trip/photo/*
        String requestURI = req.getRequestURI();
        if (requestURI.startsWith("/trip/photo/")) {
            // Allow all users to access /trip/photo/* without admin check
            chain.doFilter(request, response);
            return;
        }

        if (requestURI.startsWith("/trip/details/")) {
            // Allow all users to access /trip/photo/* without admin check
            chain.doFilter(request, response);
            return;
        }

        // Existing admin check logic
        if (httpSession == null ||
                !(httpSession.getAttribute(SessionConstants.USER_KEY) instanceof User u) ||
                u.getRole() != User.Role.ADMIN) {
            res.sendRedirect("/");
            return;
        }

        // If admin, continue to the requested resource
        chain.doFilter(request, response);
    }
}
