package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.RecoveryCodeEntity;
import ru.sfu.zooshop.entity.UserEntity;

import java.util.Set;

@Repository
public interface RecoveryCodeRepository extends JpaRepository<RecoveryCodeEntity, Long> {
  Set<RecoveryCodeEntity> findAllByUser(UserEntity user);
  void deleteAllByUser(UserEntity user);
}
