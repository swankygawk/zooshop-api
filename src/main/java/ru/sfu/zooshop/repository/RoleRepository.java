package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  boolean existsByNameIgnoreCase(String name);
  Optional<RoleEntity> findByNameIgnoreCase(String name);
}
