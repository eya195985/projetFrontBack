package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("SELECT r FROM Region r WHERE r.pays.id = :idPays")
    List<Region> findRegionsByPays(@Param("idPays") Long idPays);
}


