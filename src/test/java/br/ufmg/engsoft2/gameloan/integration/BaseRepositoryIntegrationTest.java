package br.ufmg.engsoft2.gameloan.integration;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseRepositoryIntegrationTest {

    public static PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>(
                "postgres:15-alpine"
        )
                .withInitScript("test-database.sql");

    }
}
