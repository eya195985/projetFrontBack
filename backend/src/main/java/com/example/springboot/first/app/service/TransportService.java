package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.Transport;
import com.example.springboot.first.app.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
}
