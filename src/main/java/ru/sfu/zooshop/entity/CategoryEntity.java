package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "parent")
  private List<SubcategoryEntity> children = new ArrayList<>();

  public CategoryEntity(String name, String slug) {
    super();
    this.name = name;
    this.slug = slug;
  }
}
