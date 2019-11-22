package com.roj.eztalk.data;
import lombok.Data;

@Data
public class SearchEntry {
    private String text;
    public SearchEntry() {}
    public SearchEntry(String text) {
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}