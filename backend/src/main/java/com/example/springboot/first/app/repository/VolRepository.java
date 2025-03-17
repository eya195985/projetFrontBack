package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolRepository extends JpaRepository<Vol, Long> {
}
