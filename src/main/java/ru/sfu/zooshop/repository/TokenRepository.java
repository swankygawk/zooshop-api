package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.TokenEntity;
import ru.sfu.zooshop.entity.UserEntity;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
  Optional<TokenEntity> findByValue(String value);
  Optional<TokenEntity> findByUser(UserEntity user);
}
