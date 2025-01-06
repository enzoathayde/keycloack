package com.example.keycloack.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.core.convert.converter.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JWTConverter implements Converter<Jwt, AbstractAuthenticationToken>{
  
  @Override
  public AbstractAuthenticationToken convert(Jwt source) {
   
    Map<String, Collection<String>> realmAccess = source.getClaim("realm_access");
    Collection<String> roles = realmAccess.get("roles");

    var grants = roles
    .stream()
    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
    return new JwtAuthenticationToken(source, grants);
  }
  
}
