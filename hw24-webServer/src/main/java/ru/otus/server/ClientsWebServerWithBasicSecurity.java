package ru.otus.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.security.ConstraintMapping;
import org.eclipse.jetty.ee10.servlet.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.Constraint;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import ru.otus.services.DBServiceClient;
import ru.otus.services.TemplateProcessor;

public class ClientsWebServerWithBasicSecurity extends ClientsWebServerSimple {
    private static final String ROLE_NAME_USER = "user";
    private static final String ROLE_NAME_ADMIN = "admin";

    private final LoginService loginService;

    public ClientsWebServerWithBasicSecurity(
            int port, LoginService loginService, DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        super(port, dbServiceClient, templateProcessor);
        this.loginService = loginService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        Constraint constraint = Constraint.from(ROLE_NAME_USER, ROLE_NAME_ADMIN);

        List<ConstraintMapping> constraintMappings = new ArrayList<>();
        Arrays.stream(paths).forEachOrdered(path -> {
            ConstraintMapping mapping = new ConstraintMapping();
            mapping.setPathSpec(path);
            mapping.setConstraint(constraint);
            constraintMappings.add(mapping);
        });

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        // как декодировать строку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        security.setLoginService(loginService);
        security.setConstraintMappings(constraintMappings);
        security.setHandler(new Handler.Wrapper(servletContextHandler));

        return security;
    }
}
