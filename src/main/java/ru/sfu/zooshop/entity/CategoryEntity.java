package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(
  name = "categories",
  indexes = {
    @Index(name = "ix_categories_slug", columnList = "slug")
  }
)
public class CategoryEntity extends BaseEntity {
  @Column(
    unique = true,
    nullable = false
  )
  private String name;

  @Column(
    unique = true,
    nullable = false
  )
  private String slug;

  @ColumnDefault("null")
  private String pictureUrl;

  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean hidden;

  @OneToMany(
    fetch = LAZY,
    mappedBy = "parent"
  )
  private List<SubcategoryEntity> children;

  @OneToMany(
    fetch = LAZY,
    mappedBy = "category"
  )
  private List<ProductEntity> products;

  public CategoryEntity(String name, String slug) {
    super();
    this.name = name;
    this.slug = slug;
  }
}
