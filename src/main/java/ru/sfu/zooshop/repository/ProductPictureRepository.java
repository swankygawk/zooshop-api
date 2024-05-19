package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.entity.ProductPictureEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPictureEntity, Long> {
  Optional<ProductPictureEntity> findByIdAndProduct(Long id, ProductEntity product);
  List<ProductPictureEntity> findAllByProductAndPositionGreaterThanOrderByPositionAsc(ProductEntity product, Integer position);
  Integer countByProduct(ProductEntity product);
}
