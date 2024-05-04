package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.entity.TokenEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.repository.TokenRepository;
import ru.sfu.zooshop.service.TokenService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class TokenServiceImpl implements TokenService {
  private final TokenRepository tokenRepository;

  @Override
  public TokenEntity getOrCreate(UserEntity user) {
    Optional<TokenEntity> token = tokenRepository.findByUser(user);
    return token.orElseGet(() -> tokenRepository.save(new TokenEntity(user)));
  }

  @Override
  public TokenEntity getOrCreate(UserEntity user, String additionalData) {
    Optional<TokenEntity> token = tokenRepository.findByUser(user);
    return token.orElseGet(() -> tokenRepository.save(new TokenEntity(user, additionalData)));
  }

  @Override
  public TokenEntity getTokenByValue(String value) {
    return tokenRepository.findByValue(value)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Token with value " + value + " does not exist"));
  }

  @Override
  public void deleteToken(TokenEntity token) {
    tokenRepository.delete(token);
  }
}
