package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.TypeTransport;
import com.example.springboot.first.app.repository.TypeTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeTransportService {

    @Autowired
    private TypeTransportRepository repository;

    public List<TypeTransport> getAllTypeTransports() {
        return repository.findAll();
    }

    public TypeTransport getTypeTransportById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public TypeTransport saveTypeTransport(TypeTransport typeTransport) {
        return repository.save(typeTransport);
    }

    public void deleteTypeTransport(Integer id) {
        repository.deleteById(id);
    }
}
