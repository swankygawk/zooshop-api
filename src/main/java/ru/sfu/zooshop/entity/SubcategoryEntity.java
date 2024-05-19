package ru.sfu.zooshop.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "subcategories",
  indexes = {
    @Index(name = "ix_subcategories_slug", columnList = "slug")
  },
  uniqueConstraints = {
    @UniqueConstraint(name = "unique_parent_id_name", columnNames = {"parent_id", "name"}),
    @UniqueConstraint(name = "unique_parent_id_slug", columnNames = {"parent_id", "slug"})
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
    nullable = false
  )
  private String name;

  @Column(
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
    mappedBy = "subcategory"
  )
  private List<ProductEntity> products;

  public SubcategoryEntity(CategoryEntity parent, String name, String slug) {
    super();
    this.parent = parent;
    this.name = name;
    this.slug = slug;
    this.hidden = parent.isHidden();
  }
}
