package com.github.diogenesssantos.facilittecnologia.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Diogenes santos
 * essa classe configura e sobe uma instância de um container docker rodando um banco de dados mysql,
 * para simulação de test de integração.
 */
public class TestIntegrationConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.33")
            .withDatabaseName("facilit_testdb")
            .withUsername("facilit")
            .withPassword("facilit123");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!mysql.isRunning()) {
            mysql.start();
        }

        Flyway flyway = Flyway.configure()
                .dataSource(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword())
                .locations("classpath:db/migration", "classpath:db/test-migration")
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();

        Map<String, Object> props = new HashMap<>();
        props.put("spring.datasource.url", mysql.getJdbcUrl());
        props.put("spring.datasource.username", mysql.getUsername());
        props.put("spring.datasource.password",  mysql.getPassword());
        props.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
        props.put("spring.flyway.enabled", "false");

        applicationContext.getEnvironment()
                .getPropertySources()
                .addFirst(new MapPropertySource("testcontainers", props));
    }
}
