package com.example.springboot.first.app.security;

import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Tentative de chargement de l'utilisateur avec l'email : " + email);

        // Charger l'utilisateur à partir de la base de données
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("Utilisateur non trouvé avec l'email : " + email);
                    return new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
                });

        // Récupérer le rôle de l'utilisateur
        String role = "ROLE_" + utilisateur.getRole().getRole().toUpperCase(); // Exemple : "ROLE_CLIENT"
        System.out.println("Rôle de l'utilisateur : " + role);

        // Vérification que le rôle est valide
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_USER";  // Rôle par défaut si l'utilisateur n'a pas un rôle reconnu
            System.out.println("Rôle invalide détecté, utilisation du rôle par défaut : " + role);
        }

        // Créer un ensemble d'autorités avec le rôle
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role));

        // Retourner un objet UserDetails avec l'email, mot de passe et le rôle
        return new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                authorities
        );
    }
}