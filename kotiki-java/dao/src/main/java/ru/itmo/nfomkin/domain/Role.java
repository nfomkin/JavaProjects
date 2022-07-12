package ru.itmo.nfomkin.domain;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
  ADMIN(Set.of(Permission.OWNERS_WRITE, Permission.OWNERS_READ)),
  USER(Set.of(Permission.CATS_WRITE, Permission.CATS_READ));

  private Set<Permission> permissions = new HashSet<>();

  Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities = permissions.stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }

  }
