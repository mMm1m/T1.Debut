package org.example.supportingapp;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import jakarta.servlet.ServletRegistration;
import org.example.supportingapp.configuration.LoggableMethod;

@LoggableMethod
@WebServlet(name = "SupportingServlet", value = "/help-service/v1/support")
public class SupportingServlet extends HttpServlet implements SupportManager {
    ApplicationContext context = new ApplicationContext();

    private SupportingService supportingService=context.getInstance(SupportingService.class);
    public SupportingServlet(SupportingService service) throws InvocationTargetException, IllegalAccessException {
        this.supportingService = service;
    }
    public SupportingServlet() throws InvocationTargetException, IllegalAccessException {}

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public String provideSupporting() throws SupportingError {
        return "Dear person , %s".formatted(supportingService.getSupportingWord());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        try {
            //response.getWriter().append(provideSupporting());
            response.getWriter().append(new LoggingSupportingServletProxy().provideSupporting());
        } catch (SupportingError e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().append(supportingService.addSupportingWord(request.getParameter("phrase")));
    }

    public void destroy() {}


    @LoggableMethod
    class LoggingSupportingServletProxy implements SupportManager{
        @Override
        public String provideSupporting() throws SupportingError {
            String formatted_ = SupportingServlet.this.provideSupporting();
            return "%s\n %s\n %s".formatted("Начало", formatted_, "Конец");
        }
    }
}