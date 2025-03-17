package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.UserLogo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserLogoRepository extends CassandraRepository<UserLogo, UUID> {
}