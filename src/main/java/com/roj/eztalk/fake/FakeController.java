package com.roj.eztalk.fake;

import com.roj.eztalk.*;
import java.util.List;;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/fake/")
public class FakeController {
  @RequestMapping("/test-material")
  public Material testMaterial() {
      return new Material("Some Title", 
      "Some Description",
      "Some Language",
      "Some Provider",
      "Some url",
      "true/false");
  }
  @RequestMapping("/request-feed")
  public List<Material> fakeFeed() {
      return List.of(
          new Material("French I", "an intro to French", "French", "ROJFake", "www.fake.com", "false"),
          new Material("French II", "an intro to French", "French", "ROJFake", "www.fake.com", "false"),
          new Material("In a French Restaurant", "common used sentence in a French restaurant", "French", "ROJFake", "www.fake.com", "false"),
          new Material("French Travel Advice", "advice for tourists", "French", "ROJFake", "www.fake.com", "false"),
          new Material("French food", "flash-card of various french dished", "French", "ROJFake", "www.fake.com", "true")
      );
  }
  

}