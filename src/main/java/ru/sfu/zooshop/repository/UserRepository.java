package ru.sfu.zooshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.RoleEntity;
import ru.sfu.zooshop.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  boolean existsByEmailIgnoreCase(String email);
  Optional<UserEntity> findByEmailIgnoreCase(String email);
  Optional<UserEntity> findByUserId(String userId);
  Page<UserEntity> findAllByIdNotIn(Collection<Long> ids, Pageable pageable);
  List<UserEntity> findAllByRole(RoleEntity role);
}
