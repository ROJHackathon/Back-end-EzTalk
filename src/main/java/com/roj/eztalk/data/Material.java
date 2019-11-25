package com.roj.eztalk.data;

import lombok.Data;

@Data
public class Material {  
  private String id; 
  private String 
  title,
  description,
  language,
  provider,
  url,
  isFlashCard,
  coverUrl;
  private Integer like;
  
  public Material() {}
  public Material(
      String id,
      String title,
      String description,
      String language,
      String provider,
      String url,
      String isFlashCard,
      Integer like,
      String coverUrl) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.provider = provider;
    this.language = language;
    this.url = url;
    this.isFlashCard = isFlashCard;
    this.like = like;
    this.coverUrl = coverUrl;
  }
}