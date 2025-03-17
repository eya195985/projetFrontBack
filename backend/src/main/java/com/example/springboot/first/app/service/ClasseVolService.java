package com.example.springboot.first.app.service;
import com.example.springboot.first.app.model.ClasseVol;
import com.example.springboot.first.app.repository.ClasseVolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseVolService {

    @Autowired
    private ClasseVolRepository classeVolRepository;

    public List<ClasseVol> getAllClassesVol() {
        return classeVolRepository.findAll();
    }

    public ClasseVol saveClasseVol(ClasseVol classeVol) {
        return classeVolRepository.save(classeVol);
    }
}