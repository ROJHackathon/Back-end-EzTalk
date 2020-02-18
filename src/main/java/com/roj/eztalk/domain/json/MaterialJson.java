package com.roj.eztalk.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialJson {
    public String material_id;
    public String url;
    public String title;
    public String description;
    public String language;
    public String type;
    public String mimetype;
}