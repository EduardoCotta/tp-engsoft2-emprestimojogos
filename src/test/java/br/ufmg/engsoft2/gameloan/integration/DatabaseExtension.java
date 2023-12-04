package br.ufmg.engsoft2.gameloan.integration;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DatabaseExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        BaseRepositoryIntegrationTest.postgres.start();
        System.setProperty("database.url", BaseRepositoryIntegrationTest.postgres.getJdbcUrl());
        System.setProperty("database.user", BaseRepositoryIntegrationTest.postgres.getUsername());
        System.setProperty("database.password", BaseRepositoryIntegrationTest.postgres.getPassword());
        System.setProperty("database.name", BaseRepositoryIntegrationTest.postgres.getDatabaseName());
    }

    @Override
    public void close() throws Throwable {
        BaseRepositoryIntegrationTest.postgres.stop();
    }
}
