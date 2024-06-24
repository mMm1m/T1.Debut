package org.example.supportingapp;

public class SupportingServiceImpl implements SupportingService {
    private SupportingRepository supportingRepository;

    public SupportingServiceImpl(){
        this.supportingRepository = new SupportingRepository();
    }

    public String getSupportingWord() throws SupportingError {
        return supportingRepository.getSupportingWord();
    }

    public String addSupportingWord(String phrase) {
        supportingRepository.addSupportingWord(phrase);
        return "Добавлена фраза: "+phrase;
    }
}
