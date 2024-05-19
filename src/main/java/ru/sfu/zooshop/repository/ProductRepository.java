package ru.sfu.zooshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  Page<ProductEntity> findAllByHiddenFalse(Pageable pageable);
  Page<ProductEntity> findAllByCategoryAndHiddenFalse(CategoryEntity category, Pageable pageable);
  Page<ProductEntity> findAllBySubcategoryAndHiddenFalse(SubcategoryEntity subcategory, Pageable pageable);
  Optional<ProductEntity> findBySubcategoryHiddenFalseAndIdAndHiddenFalse(Long id);
  Optional<ProductEntity> findBySubcategoryHiddenFalseAndSlugAndHiddenFalse(String slug);
  Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable pageable);
  Page<ProductEntity> findAllBySubcategory(SubcategoryEntity subcategory, Pageable pageable);
  Optional<ProductEntity> findBySlug(String slug);
  boolean existsByName(String name);
}
