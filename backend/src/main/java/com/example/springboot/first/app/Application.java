package com.example.springboot.first.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CqlSession cqlSession; // Injecté depuis CassandraConfig

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) {
		// Vérification de la connexion à la base relationnelle
		try {
			Query query = entityManager.createNativeQuery("SELECT 1");
			query.getSingleResult();
			log.info("✅ Connexion réussie à la base de données relationnelle!");
		} catch (Exception e) {
			log.error("❌ Erreur de connexion à la base de données relationnelle: {}", e.getMessage(), e);
		}

		// Vérification de la connexion à Cassandra
		try {
			if (cqlSession != null && !cqlSession.isClosed()) {
				log.info("✅ Connexion réussie à Cassandra!");
			} else {
				log.error("❌ Erreur de connexion à Cassandra: La session est fermée.");
			}
		} catch (Exception e) {
			log.error("❌ Erreur de connexion à Cassandra: {}", e.getMessage(), e);
		}

		log.info("🚀 Hello Eya, voilà ton Spring Boot!");
	}
}
