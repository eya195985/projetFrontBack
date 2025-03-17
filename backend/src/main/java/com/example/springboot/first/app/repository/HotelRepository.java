package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
