package com.example.authdemo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter = this runs ONCE per request (not multiple times)

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain    // Chain of filters — call next filter when done
    ) throws ServletException, IOException {

        // Step 1: Read the Authorization header
        // It looks like: "Bearer eyJhbGciOi..."
        final String authHeader = request.getHeader("Authorization");

        // Step 2: If no header or doesn't start with "Bearer ", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // Pass to next filter
            return;
        }

        // Step 3: Extract the actual token (remove "Bearer " prefix)
        final String jwt = authHeader.substring(7);

        // Step 4: Extract email from token
        final String userEmail = jwtService.extractUsername(jwt);

        // Step 5: If email found AND user not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step 6: Load user from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // Step 7: Validate token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Step 8: Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                          // No credentials needed (already verified)
                                userDetails.getAuthorities()   // User's roles
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Step 9: Tell Spring Security "this user is authenticated"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 10: Continue to the next filter / controller
        filterChain.doFilter(request, response);
    }
}