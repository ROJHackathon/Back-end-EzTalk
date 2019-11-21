package com.roj.eztalk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {
  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();
  @RequestMapping("/Hello")
  public Hello hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Hello(counter.incrementAndGet(),
            String.format(template, name));
  }

}
