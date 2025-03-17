package com.example.springboot.first.app.service;


import com.example.springboot.first.app.model.LogoImage;
import com.example.springboot.first.app.repository.LogoImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LogoImageService {

    @Autowired
    private LogoImageRepository logoImageRepository;

    /**
     * Récupère une image de logo à partir de son UUID.
     *
     * @param logoUid L'UUID du logo à récupérer.
     * @return Un Optional contenant le LogoImage s'il est trouvé, sinon un Optional vide.
     */
    public Optional<LogoImage> getLogoImageByUid(UUID logoUid) {
        return logoImageRepository.findById(logoUid);
    }
}
