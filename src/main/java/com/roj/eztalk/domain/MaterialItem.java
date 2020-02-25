package com.roj.eztalk.domain;

import com.roj.eztalk.domain.json.MaterialJson;
import com.roj.eztalk.domain.json.WeightedMaterialJson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialItem {
  // this MaterialAdd class stores part of the data retrieved from X5GON and those generated by eztalk
  private Long id;
  private String title;
  private String description;
  private String language;
  // private String provider;
  private String url;
  // additional
  private String coverUrl;
  private Integer love;
  private String type;
  private String mimetype;

  public MaterialItem(MaterialJson json, Material material){
    this.id = Long.parseLong(json.material_id);
    this.title = json.title;
    this.description = json.description;
    this.language = json.language;
    this.url = json.url;
    this.type = json.type;
    this.mimetype = json.mimetype;

    this.coverUrl = material.getCoverUrl();
    this.love = material.getLove();
  }
  public MaterialItem(WeightedMaterialJson json, Material material){
    this.id = Long.parseLong(json.material_id);
    this.title = json.title;
    this.description = json.description;
    this.language = json.language;
    this.url = json.url;
    this.type = json.type;
    this.mimetype = json.mimetype;

    this.coverUrl = material.getCoverUrl();
    this.love = material.getLove();
  }
}