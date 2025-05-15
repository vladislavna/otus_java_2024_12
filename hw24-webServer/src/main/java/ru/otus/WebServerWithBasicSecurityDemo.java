package ru.otus;

import org.eclipse.jetty.security.LoginService;
import ru.otus.configuration.HibernateConfiguration;
import ru.otus.configuration.model.HibertaneConfig;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.repository.DataTemplateHibernate;
import ru.otus.repository.HibernateUtils;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerWithBasicSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.impl.DbServiceClientImpl;
import ru.otus.services.impl.InMemoryLoginServiceImpl;
import ru.otus.services.impl.TemplateProcessorImpl;
import ru.otus.sessionmanager.TransactionManagerHibernate;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        HibertaneConfig configuration = HibernateConfiguration.getConfigurationProperties();

        new MigrationsExecutorFlyway(
                        configuration.getDbUrl(), configuration.getDbUserName(), configuration.getDbPassword())
                .executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(
                HibernateConfiguration.getConfiguration(), Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        UserDao userDao = new InMemoryUserDao();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        LoginService loginService = new InMemoryLoginServiceImpl(userDao);

        ClientsWebServer clientsWebServer = new ClientsWebServerWithBasicSecurity(
                WEB_SERVER_PORT, loginService, dbServiceClient, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
