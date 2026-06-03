package com.challenge.utils.constants;

public enum Resources {
  USERS("/users");

  private final String value;

  Resources(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
