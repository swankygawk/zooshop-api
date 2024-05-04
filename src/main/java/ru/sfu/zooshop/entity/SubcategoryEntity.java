package ru.sfu.zooshop.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "subcategories",
  indexes = {
    @Index(name = "ix_subcategories_slug", columnList = "slug")
  }
)
public class SubcategoryEntity extends BaseEntity {
  @ManyToOne(
    optional = false
  )
  @JoinColumn(
    name = "parent_id",
    nullable = false
  )
  private CategoryEntity parent;

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

  public SubcategoryEntity(CategoryEntity parent, String name, String slug) {
    super();
    this.parent = parent;
    this.name = name;
    this.slug = slug;
  }
}
