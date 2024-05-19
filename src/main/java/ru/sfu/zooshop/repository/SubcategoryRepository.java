package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {
  boolean existsByParentAndName(CategoryEntity parent, String name);
  Optional<SubcategoryEntity> findByParentSlugAndSlug(String slug, String slug1);
  Optional<SubcategoryEntity> findByParentSlugAndSlugAndHiddenFalse(String categorySlug, String subcategorySlug);
  List<SubcategoryEntity> findAllByParentAndHiddenFalse(CategoryEntity parent);
}
