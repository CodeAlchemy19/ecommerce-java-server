package com.example.server.Filter;


import com.example.server.Services.AdminService;
import com.example.server.Utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterAdmin extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    // @Autowired
    // private AccountService accountService;
    @Autowired
    private AdminService adminService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        final String authHeader=request.getHeader("Authorization");


        String name=null;
        String token=null;
           String path = request.getRequestURI();
if (path.equals("/admin/signin") || path.equals("/auth/signin")) {
    filterChain.doFilter(request, response); // ✅ do NOT apply filter
    return;
}
        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            name = jwtUtil.extractUserName(token);
            if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = adminService.loadByUsername(name);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
     filterChain.doFilter(request, response);
        
    }


}
