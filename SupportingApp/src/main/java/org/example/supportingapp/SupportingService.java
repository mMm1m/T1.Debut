package org.example.supportingapp;

public class SupportingService {
    private SupportingRepository supportingRepository;
    /*SupportingService(SupportingRepository repository){
        this.supportingRepository = repository;
    }*/
    SupportingService(){this.supportingRepository = new SupportingRepository();}

    public String getSupportingWord() throws SupportingError {
        return supportingRepository.getSupportingWord();
    }

    public String addSupportingWord(String phrase) {
        supportingRepository.addSupportingWord(phrase);
        return "Добавлена фраза: "+phrase;
    }
}
