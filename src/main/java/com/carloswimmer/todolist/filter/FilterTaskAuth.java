package com.carloswimmer.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.carloswimmer.todolist.user.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (!request.getServletPath().equals("/tasks/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the Authorization header
        var auth = request.getHeader("Authorization");
        var authEncoded = auth.substring("Basic".length()).trim();
        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        String authString = new String(authDecoded);

        // Get the username and password from the Authorization header
        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        // Validate user exists
        var user = userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password");
            return;
        }

        // Validate password
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!passwordVerify.verified) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password");
            return;
        }

        filterChain.doFilter(request, response);

    }

}
