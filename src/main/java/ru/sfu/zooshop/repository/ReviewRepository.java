package ru.sfu.zooshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.entity.ReviewEntity;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
  Page<ReviewEntity> findAllByProductAndCommentNotNull(ProductEntity product, Pageable pageable);
  Page<ReviewEntity> findAllByCreatedByIdAndProductHiddenFalse(Long userId, Pageable pageable);
  Optional<ReviewEntity> findByIdAndCreatedByIdAndProductHiddenFalse(Long reviewId, Long userId);
  boolean existsByCreatedByIdAndProduct(Long userId, ProductEntity product);
}
