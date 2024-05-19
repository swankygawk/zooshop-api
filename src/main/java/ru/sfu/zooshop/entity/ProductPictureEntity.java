package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_pictures")
public class ProductPictureEntity extends BaseEntity {
  @ManyToOne(
    optional = false
  )
  @JoinColumn(
    name = "product_id",
    nullable = false
  )
  private ProductEntity product;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private Integer position;
}
