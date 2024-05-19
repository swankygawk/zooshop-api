package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "products",
  indexes = {
    @Index(name = "ix_products_category_id", columnList = "category_id"),
    @Index(name = "ix_products_subcategory_id", columnList = "subcategory_id")
  }
)
public class ProductEntity extends BaseEntity {
  @ManyToOne(
    fetch = LAZY,
    optional = false
  )
  @JoinColumn(
    name = "category_id",
    nullable = false
  )
  private CategoryEntity category;

  @ManyToOne(
    fetch = LAZY,
    optional = false
  )
  @JoinColumn(
    name = "subcategory_id",
    nullable = false
  )
  private SubcategoryEntity subcategory;

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

  @Column(columnDefinition = "text")
  @ColumnDefault("null")
  private String description;

  @OneToMany(
    fetch = EAGER,
    mappedBy = "product"
  )
  @OrderBy("position")
  private List<ProductPictureEntity> pictures;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer quantityInStock;

  @Formula("quantity_in_stock > 0")
  private boolean inStock;

  @Formula("(SELECT AVG(r.rating) FROM reviews r WHERE r.product_id = id)")
  private Float averageRating;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer popularity;

  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean hidden;

  public ProductEntity(
    CategoryEntity category,
    SubcategoryEntity subcategory,
    String name,
    String slug,
    String description,
    BigDecimal price
  ) {
    super();
    this.category = category;
    this.subcategory = subcategory;
    this.name = name;
    this.slug = slug;
    this.description = description;
    this.price = price;
    this.quantityInStock = 0;
    this.popularity = 0;
    this.hidden = subcategory.isHidden();
  }
}
