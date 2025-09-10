package com.botmg3002.canteen.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.botmg3002.canteen.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserFilter extends OncePerRequestFilter {

    @Autowired
    private final UserRepository userRepository;

    public UserFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String userIdHeader = request.getHeader("X-USER-ID");
        if (userIdHeader == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "X-USER-ID header is required");
            return;
        }

        try {
            Long userId = Long.parseLong(userIdHeader);

            userRepository.findById(userId).ifPresentOrElse(user -> {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())));
                SecurityContextHolder.getContext().setAuthentication(auth);
                
            }, () -> {
                try {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            });

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid X-USER-ID");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/auth/");
    }

}
