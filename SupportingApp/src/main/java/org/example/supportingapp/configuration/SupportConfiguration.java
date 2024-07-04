package org.example.supportingapp.configuration;

import org.example.supportingapp.SupportManager;
import org.example.supportingapp.SupportingService;
import org.example.supportingapp.SupportingServiceImpl;
import org.example.supportingapp.SupportingServlet;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class SupportConfiguration {
    @Instance
    public SupportingService supportingService() {
        return new SupportingServiceImpl();
    }
    @Instance
    public SupportManager supportingServlet() throws InvocationTargetException, IllegalAccessException {
        return new SupportingServlet(supportingService());
    }
}
