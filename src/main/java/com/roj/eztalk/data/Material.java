package com.roj.eztalk.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Material {  
  private @Id @GeneratedValue Long id; 
  private String 
  title,
  description,
  language,
  provider,
  url,
  isFlashCard;
  private Integer like;
  public Material() {}
  public Material(
      String title,
      String description,
      String language,
      String provider,
      String url,
      String isFlashCard,
      Integer like) {
    this.title = title;
    this.description = description;
    this.provider = provider;
    this.language = language;
    this.url = url;
    this.isFlashCard = isFlashCard;
    this.like = like;
  }
}