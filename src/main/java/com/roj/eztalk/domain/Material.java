package com.roj.eztalk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
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
  // this Material class stores the data generated for/in eztlak
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
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "material")
  private List<Rating> ratings = new ArrayList<>();

  public void addRating(Rating rating){
    this.ratings.add(rating);
  }
}