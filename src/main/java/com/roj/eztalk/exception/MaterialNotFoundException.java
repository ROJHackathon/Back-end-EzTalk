package com.roj.eztalk.exception;

class MaterialNotFoundException extends RuntimeException {

  MaterialNotFoundException(Long id) {
    super("Could not find user " + id.toString());
  }
}