package com.roj.eztalk.data;

import com.roj.eztalk.data.json.MaterialJson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialAdd {
  private Long id;
  private String title;
  private String description;
  private String language;
  private String provider;
  private String url;
  // additional
  private String coverUrl;
  private Integer love;

  public MaterialAdd(MaterialJson json, Material material){
    this.id = Long.parseLong(json.material_id);
    this.title = json.title;
    this.description = json.description;
    this.language = json.language;
    this.provider = json.provider;
    this.url = json.url;

    this.coverUrl = material.getCoverUrl();
    this.love = material.getLove();
  }
}