package com.carebridge.entity;
public class ICDEntry {

    private String code;
    private String detailedDescription;
    private String shortName;

    public ICDEntry(String code, String detailedDescription, String shortName) {
        this.code = code;
        this.detailedDescription = detailedDescription;
        this.shortName = shortName;
    }

    public String getCode() {   // 🔥 MUST
        return code;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public String getShortName() {
        return shortName;
    }
}