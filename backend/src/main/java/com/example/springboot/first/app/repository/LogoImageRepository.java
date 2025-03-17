package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.LogoImage;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface LogoImageRepository extends CassandraRepository<LogoImage, UUID> {
}
