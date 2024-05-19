package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "reviews",
  uniqueConstraints = {
    @UniqueConstraint(name = "unique_user_product_review", columnNames = {"created_by", "product_id"})
  }
)
public class ReviewEntity extends BaseEntity {
  @ManyToOne(
    fetch = LAZY,
    optional = false
  )
  @JoinColumn(
    name = "product_id",
    nullable = false
  )
  private ProductEntity product;

  @Column(nullable = false)
  private Short rating;

  @ColumnDefault("null")
  private String comment;
}
