package com.example.springboot.first.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    // Clé secrète sécurisée pour la signature du token
    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Durée de validité du token (24 heures)
    private final int jwtExpirationMs = 86400000;

    /**
     * Génère un token JWT contenant l'ID de l'utilisateur, le rôle et l'email.
     *
     * @param authentication Objet d'authentification Spring Security.
     * @param utilisateurId  ID de l'utilisateur.
     * @param email          Email de l'utilisateur.
     * @return Token JWT généré.
     */
    public String generateToken(Authentication authentication, Long utilisateurId, String email) {
        // Récupérer les rôles de l'utilisateur
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Dates de création et d'expiration du token
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // Logs pour le débogage
        System.out.println("Génération du token JWT pour l'utilisateur : " + email);
        System.out.println("Rôles inclus dans le token : " + roles);
        System.out.println("ID de l'utilisateur inclus dans le token : " + utilisateurId);

        // Générer le token JWT avec l'ID utilisateur, le rôle et l'email
        return Jwts.builder()
                .setSubject(email) // Sujet du token (email de l'utilisateur)
                .claim("roles", roles) // Ajouter les rôles dans le token
                .claim("utilisateurId", utilisateurId) // Ajouter l'ID de l'utilisateur dans le token
                .claim("email", email) // Ajouter l'email dans le token
                .setIssuedAt(now) // Date de création
                .setExpiration(expiryDate) // Date d'expiration
                .signWith(jwtSecret, SignatureAlgorithm.HS512) // Signature avec la clé secrète
                .compact(); // Convertir en chaîne de caractères
    }


    /** -----------deuxiéme methode de generation de token ------------------*/

    public String generateToken(Long utilisateurId, String email) {
        // Dates de création et d'expiration du token
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // Logs pour le débogage
        System.out.println("Génération du token JWT pour l'utilisateur : " + email);
        System.out.println("ID de l'utilisateur inclus dans le token : " + utilisateurId);

        // Générer le token JWT avec l'ID utilisateur et l'email
        return Jwts.builder()
                .setSubject(email) // Sujet du token (email de l'utilisateur)
                .claim("utilisateurId", utilisateurId) // Ajouter l'ID de l'utilisateur dans le token
                .claim("email", email) // Ajouter l'email dans le token
                .setIssuedAt(now) // Date de création
                .setExpiration(expiryDate) // Date d'expiration
                .signWith(jwtSecret, SignatureAlgorithm.HS512) // Signature avec la clé secrète
                .compact(); // Convertir en chaîne de caractères
    }


    /**
     * Valide et extrait les informations du token JWT.
     *
     * @param token Token JWT à valider.
     * @return Claims (informations contenues dans le token).
     */
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret) // Clé secrète pour valider la signature
                .build()
                .parseClaimsJws(token) // Valider et parser le token
                .getBody(); // Récupérer les informations (claims)
    }


    /**
     * Extrait l'ID de l'utilisateur à partir du token JWT.
     *
     * @param token Token JWT.
     * @return ID de l'utilisateur.
     */
    public Long getUtilisateurIdFromToken(String token) {
        Claims claims = validateToken(token); // Récupérer les claims du token
        if (claims != null && claims.containsKey("utilisateurId")) {
            Object utilisateurIdObj = claims.get("utilisateurId");
            if (utilisateurIdObj instanceof Integer) {
                return ((Integer) utilisateurIdObj).longValue(); // Convertir Integer en Long
            } else if (utilisateurIdObj instanceof Long) {
                return (Long) utilisateurIdObj; // Retourner directement si c'est déjà un Long
            } else {
                throw new IllegalArgumentException("L'ID utilisateur dans le token n'est pas un nombre valide");
            }
        }
        return null; // Retourner null si l'ID utilisateur n'est pas trouvé ou invalide
    }

    /**
     * Extrait l'email de l'utilisateur à partir du token JWT.
     *
     * @param token Token JWT.
     * @return Email de l'utilisateur.
     */
    public String getEmailFromToken(String token) {
        Claims claims = validateToken(token); // Récupérer les claims du token
        return claims != null ? claims.getSubject() : null; // Le sujet du token est l'email
    }

    /**
     * Extrait les rôles de l'utilisateur à partir du token JWT.
     *
     * @param token Token JWT.
     * @return Rôles de l'utilisateur.
     */
    public String getRolesFromToken(String token) {
        Claims claims = validateToken(token); // Récupérer les claims du token
        return claims != null ? (String) claims.get("roles") : null; // Récupérer les rôles
    }
}