package com.roj.eztalk.data;

import lombok.Data;

@Data
public class SetPreference {
    private String token, preference;
    
    public SetPreference() {}
    public SetPreference(String token, String preference) {
        this.token = token;
        this.preference = preference;
    }
    public String getToken() {
        return token;
    }
    public String getPreference() {
        return preference;
    }
}