package com.roj.eztalk.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Material {
  // @Id
  // @GeneratedValue
  // private Long id;
  // private String title;
  // private String description;
  // private String language;
  // private String provider;
  // private String url;
  // private String isFlashCard;
  // private String coverUrl;
  // private Integer like;
  public Material(Long id, String coverUrl, int love) {
    this.id = id;
    this.coverUrl = coverUrl;
    this.love = love;
  }

  @Id
  private Long id;
  private String coverUrl;
  private int love;


  @OneToMany(mappedBy = "material")
  private List<Comment> comments;
}