package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  List<CategoryEntity> findAllByHiddenFalse();
  Optional<CategoryEntity> findBySlug(String slug);
  Optional<CategoryEntity> findBySlugAndHiddenFalse(String slug);
  boolean existsByName(String name);
}
