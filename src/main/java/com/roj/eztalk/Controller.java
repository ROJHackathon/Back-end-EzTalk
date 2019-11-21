package com.roj.eztalk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
public class Controller {
  @RequestMapping("/test-material")
  public Material testMaterial() {
      return new Material("Some Title", 
      "Some Description",
      "Some Language",
      "Some Provider",
      "Some url",
      "true/false");
  }
  

}
