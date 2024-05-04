package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfu.zooshop.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  Optional<CategoryEntity> findBySlug(String slug);
  boolean existsByName(String name);
}
