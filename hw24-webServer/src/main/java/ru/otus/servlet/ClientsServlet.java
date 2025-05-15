package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.http.HttpStatus;
import ru.otus.exceptions.Errors;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.services.DBServiceClient;
import ru.otus.services.TemplateProcessor;
import ru.otus.utils.CommonConstants;

@SuppressWarnings({"java:S1989", "java:S1948"})
public class ClientsServlet extends HttpServlet {

    private final DBServiceClient dbServiceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientsServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(CommonConstants.TEMPLATE_ATTR_CLIENTS, dbServiceClient.findAll());
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CommonConstants.CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        List<Phone> phoneList = Arrays.stream(
                        getParam(req, CommonConstants.PARAM_CLIENT_PHONE).split(CommonConstants.COMMA))
                .map(Phone::new)
                .toList();

        try {
            dbServiceClient.saveClient(new Client(
                    getParam(req, CommonConstants.PARAM_CLIENT_NAME),
                    new Address(getParam(req, CommonConstants.PARAM_CLIENT_ADDRESS)),
                    phoneList));
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR_500, Errors.CLIENT_DOESNT_ADD);
        }
        response.setStatus(HttpStatus.OK_200);
        response.sendRedirect(CommonConstants.PATH_CLIENTS);
    }

    private String getParam(HttpServletRequest req, String method) {
        return req.getParameter(method);
    }
}
