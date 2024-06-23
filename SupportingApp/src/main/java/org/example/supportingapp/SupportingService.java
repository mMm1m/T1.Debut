package org.example.supportingapp;

public interface SupportingService {
    public String getSupportingWord() throws SupportingError;
    public String addSupportingWord(String phrase);
}
