package com.roj.eztalk;

import com.roj.eztalk.data.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roj.eztalk.fake.FakeController;

@RestController
@RequestMapping("/api/feed")
public class Controller {
  @RequestMapping("/test-material")
  public Material testMaterial() {
    return new Material(Long.valueOf(1), "Some Title", "Some Description", "Some Language", "Some Provider", "Some url", "true/false", 0, FakeController.generateCoverUrl());
  }

}