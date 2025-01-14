package dev.mobprog.techhub.models;

public class Language {
    private String Language;
    private String computerLanguage;

    public Language(String language, String computerLanguage) {
        Language = language;
        this.computerLanguage = computerLanguage;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getComputerLanguage() {
        return computerLanguage;
    }

    public void setComputerLanguage(String computerLanguage) {
        this.computerLanguage = computerLanguage;
    }
}
