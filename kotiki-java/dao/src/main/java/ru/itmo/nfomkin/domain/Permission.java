package ru.itmo.nfomkin.domain;

public enum Permission {
  OWNERS_WRITE("owners:write"),
  OWNERS_READ("owners:read"),
  CATS_WRITE("cats:write"),
  CATS_READ("cats:read");

  private String permission;

  private Permission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }

}
