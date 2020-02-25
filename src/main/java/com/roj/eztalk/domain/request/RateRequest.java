package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class RateRequest {
   private Long token;
   private Integer rate; 
}