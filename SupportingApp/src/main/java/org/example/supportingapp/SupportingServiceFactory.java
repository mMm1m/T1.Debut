package org.example.supportingapp;

public class SupportingServiceFactory {
    // избежание проблем с многопоточностью синглтона
    private static final SupportingServiceImpl INSTANCE = new SupportingServiceImpl();
    public static SupportingService getINSTANCE(){
        return INSTANCE;
    }
}
