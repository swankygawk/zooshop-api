package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfu.zooshop.entity.SubcategoryEntity;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {
  boolean existsByName(String name);
  Optional<SubcategoryEntity> findBySlug(String slug);
  List<SubcategoryEntity> findAllByParentId(Long parentId);
}
