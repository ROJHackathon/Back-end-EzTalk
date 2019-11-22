package com.roj.eztalk.exception;

class UserNotFoundException extends RuntimeException {

  UserNotFoundException(Long id) {
    super("Could not find user " + id.toString());
  }
}