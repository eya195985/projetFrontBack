package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.TypeHotel;
import com.example.springboot.first.app.repository.TypeHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeHotelService {

    @Autowired
    private TypeHotelRepository typeHotelRepository;

    public List<TypeHotel> getAllHotelTypes() {
        return typeHotelRepository.findAll();
    }
}
