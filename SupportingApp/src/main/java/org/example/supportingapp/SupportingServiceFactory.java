package org.example.supportingapp;

public class SupportingServiceFactory {
    private static volatile SupportingServiceImpl INSTANCE;
    public static SupportingService getINSTANCE(){
        if(INSTANCE == null) INSTANCE = new SupportingServiceImpl();
        synchronized (SupportingServiceImpl.class){
            if(INSTANCE == null) INSTANCE = new SupportingServiceImpl();
        }
        return INSTANCE;
    }
}
