package com.roj.eztalk.data.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialJson {
    public String material_id;
    public Double weight;
    public String url;
    public String title;
    public String description;
    // public String provider;
    public String language;
    public List<WikiJson> wikipedia;
    public String type;
}