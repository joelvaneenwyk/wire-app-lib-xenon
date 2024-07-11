package com.wire.xenon;

import java.sql.Driver;
import java.sql.DriverManager;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class DatabaseTestBase {

    protected static Flyway flyway;
    protected static Jdbi jdbi;

    @BeforeAll
    public static void initiate() throws Exception {
        String databaseUrlPrefix = defaultIfNull(System.getenv("POSTGRES_URL"), "localhost:5432/postgres");
        String databaseUrl = "jdbc:postgresql://" + databaseUrlPrefix;
        assert(databaseUrl != null);

        String user = defaultIfNull(System.getenv("POSTGRES_USER"), "postgres");
        String password = defaultIfNull(System.getenv("POSTGRES_PASSWORD"), "postgres");

        Class<?> driverClass = Class.forName("org.postgresql.Driver");
        final Driver driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
        DriverManager.registerDriver(driver);

        jdbi = password != null
                ? Jdbi.create(databaseUrl, user, password)
                : Jdbi.create(databaseUrl);
        jdbi.installPlugin(new SqlObjectPlugin());

        flyway = Flyway.configure().dataSource(databaseUrl, user, password).baselineOnMigrate(true).load();

        flyway.migrate();
    }

    private static String defaultIfNull(String value, String defaultValue) {
        return value != null ? value : defaultValue;
    }

    @AfterAll
    public static void classCleanup() {
        try {
            flyway.clean();
        } catch (Exception e) {
            // ignore
        }
    }
}
