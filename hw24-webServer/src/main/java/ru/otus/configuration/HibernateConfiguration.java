package ru.otus.configuration;

import org.hibernate.cfg.Configuration;
import ru.otus.configuration.model.HibertaneConfig;

public class HibernateConfiguration {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private HibernateConfiguration() {
        throw new IllegalStateException("Utility class");
    }

    public static Configuration getConfiguration() {
        return new Configuration().configure(HIBERNATE_CFG_FILE);
    }

    public static HibertaneConfig getConfigurationProperties() {
        var configuration = getConfiguration();

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        return HibertaneConfig.builder()
                .dbUrl(dbUrl)
                .dbUserName(dbUserName)
                .dbPassword(dbPassword)
                .build();
    }
}
