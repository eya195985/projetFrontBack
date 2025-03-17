package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.ServiceTransport;
import com.example.springboot.first.app.model.ServiceTransportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTransportRepository extends JpaRepository<ServiceTransport, ServiceTransportId> {
}
