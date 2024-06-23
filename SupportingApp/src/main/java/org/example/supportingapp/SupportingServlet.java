package org.example.supportingapp;

import java.io.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "SupportingServlet", value = "/help-service/v1/support")
public class SupportingServlet extends HttpServlet {
    private SupportingService supportingService = SupportingServiceFactory.getINSTANCE();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        try {
            response.getWriter().append(supportingService.getSupportingWord());
        } catch (SupportingError e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().append(supportingService.addSupportingWord(request.getParameter("phrase")));
    }

    public void destroy() {}
}