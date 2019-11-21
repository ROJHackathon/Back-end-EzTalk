package com.roj.eztalk;

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

  public Material(
      String title,
      String description,
      String language,
      String provider,
      String url,
      String isFlashCard) {
    this.title = title;
    this.description = description;
    this.provider = provider;
    this.language = language;
    this.url = url;
    this.isFlashCard = isFlashCard;
  }
}