package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.Region;
import com.example.springboot.first.app.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getRegionsByPays(Long idPays) {
        return regionRepository.findRegionsByPays(idPays);
    }
}