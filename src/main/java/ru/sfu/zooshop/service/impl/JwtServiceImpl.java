package ru.sfu.zooshop.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.enumeration.JwtType;
import ru.sfu.zooshop.service.CookieService;
import ru.sfu.zooshop.service.JwtService;
import ru.sfu.zooshop.service.UserService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static io.jsonwebtoken.Jwts.SIG.HS512;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.time.OffsetDateTime.now;
import static java.util.Date.from;
import static java.util.Map.of;
import static java.util.UUID.randomUUID;
import static ru.sfu.zooshop.constant.Constant.*;
import static ru.sfu.zooshop.enumeration.JwtType.ACCESS;
import static ru.sfu.zooshop.enumeration.JwtType.REFRESH;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {
  private final UserService userService;
  private final CookieService cookieService;

  private static SecretKey secretKey() {
    return hmacShaKeyFor(BASE64.decode(JWT_SECRET));
  }

  private static JwtBuilder jwtBuilder() {
    return builder()
      .header().add(of("typ", "JWT"))
      .and()
      .issuer(ZOOSHOP)
      .audience().add(ZOOSHOP_USERS)
      .and()
      .id(randomUUID().toString())
      .issuedAt(from(now().toInstant()))
      .notBefore(from(now().toInstant()))
      .signWith(secretKey(), HS512);
  }

  private static Claims getClaims(String jwt) {
    return parser()
      .verifyWith(secretKey())
      .build()
      .parseSignedClaims(jwt)
      .getPayload();
  }

  private String createJwt(String userId, String role, JwtType type) {
    return jwtBuilder()
      .subject(userId)
      .claim(ROLE, role)
      .expiration(from(now().toInstant().plusSeconds(type.getExpiration())))
      .compact();
  }

  @Override
  public void setJwtCookies(HttpServletResponse response, String userId, String role) {
    String accessJwt = createJwt(userId, role, ACCESS);
    String refreshJwt = createJwt(userId, role, REFRESH);
    cookieService.setCookie(response, ACCESS.getCookieName(), accessJwt, ACCESS.getExpiration());
    cookieService.setCookie(response, REFRESH.getCookieName(), refreshJwt, REFRESH.getExpiration());
  }

  @Override
  public void unsetJwtCookies(HttpServletRequest request, HttpServletResponse response) {
    cookieService.unsetCookie(request, response, ACCESS.getCookieName());
    cookieService.unsetCookie(request, response, REFRESH.getCookieName());
  }

  @Override
  public Optional<String> getJwtFromRequestCookie(HttpServletRequest request, JwtType type) {
    return cookieService.getCookie(request, type.getCookieName()).map(Cookie::getValue);
  }

  @Override
  public boolean isJwtValid(String jwt) {
    Claims claims = getClaims(jwt);
    UserEntity user = userService.findUserByUserId(claims.getSubject());
    Date now = from(now().toInstant());

    return Objects.equals(claims.getIssuer(), ZOOSHOP)
      && Objects.equals(claims.getAudience(), ZOOSHOP_USERS)
      && Objects.equals(claims.get(ROLE, String.class), user.getRole().getName().toUpperCase())
      && claims.getNotBefore().before(now)
      && claims.getIssuedAt().before(now)
      && claims.getExpiration().after(now);
  }

  @Override
  public UserEntity getUserFromJwt(String jwt) {
    String userId = getClaims(jwt).getSubject();
    return userService.findUserByUserId(userId);
  }
}
