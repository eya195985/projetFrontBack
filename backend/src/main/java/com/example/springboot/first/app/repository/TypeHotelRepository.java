package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.TypeHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeHotelRepository extends JpaRepository<TypeHotel, Integer> {
}
