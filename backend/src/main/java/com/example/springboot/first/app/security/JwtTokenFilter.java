package com.example.springboot.first.app.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("--- Début du filtre JWT ---");

        // Récupérer le token JWT
        String token = extractToken(request);

        // Vérification si le token a été trouvé
        if (token != null) {
            System.out.println("Token extrait: " + token);
        } else {
            System.out.println("Aucun token trouvé dans l'en-tête Authorization.");
        }

        if (token != null) {
            try {
                // Valider le token et récupérer les informations
                Claims claims = jwtUtils.validateToken(token);
                if (claims != null) {
                    String username = claims.getSubject();
                    System.out.println("Utilisateur extrait du token: " + username);

                    // Charger les détails de l'utilisateur
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("UserDetails chargé avec succès");

                    // Authentifier l'utilisateur
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Authentification réussie pour: " + username);
                } else {
                    System.out.println("Échec de la validation du token");
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du traitement du token: " + e.getMessage());
            }
        } else {
            System.out.println("Le token est invalide ou absent, l'utilisateur n'est pas authentifié.");
        }

        System.out.println("--- Fin du filtre JWT ---");

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
