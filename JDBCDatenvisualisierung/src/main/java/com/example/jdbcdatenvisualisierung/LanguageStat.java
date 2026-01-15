package com.example.jdbcdatenvisualisierung;

public class LanguageStat {

    private final String language;
    private final double percentage;
    private final boolean official;

    public LanguageStat(String language, double percentage, boolean official) {
        this.language = language;
        this.percentage = percentage;
        this.official = official;
    }

    public String getLanguage() {
        return language;
    }

    public double getPercentage() {
        return percentage;
    }

    public boolean isOfficial() {
        return official;
    }
}
