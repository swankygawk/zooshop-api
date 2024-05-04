package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;
import ru.sfu.zooshop.security.RequestContext;

import java.time.OffsetDateTime;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@MappedSuperclass
@OptimisticLocking
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  @ToString.Include
  @Id
  @SequenceGenerator(
    name = "primary_key_seq",
    sequenceName = "primary_key_seq",
    initialValue = 58,
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = SEQUENCE,
    generator = "primary_key_seq"
  )
  @Column(
    name = "id",
    unique = true,
    nullable = false,
    updatable = false
  )
  protected Long id;

  @Version
  @Column(
    name = "optimistic_lock",
    nullable = false
  )
  @ColumnDefault("0")
  protected Long optimisticLock;

  @Column(
    name = "reference_id",
    unique = true,
    nullable = false,
    updatable = false
  )
  protected String referenceId = new AlternativeJdkIdGenerator().generateId().toString();

//  @CreatedBy
  @ManyToOne(fetch = LAZY)
  @JoinColumn(
    name = "created_by",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      name = "fk_created_by",
      value = CONSTRAINT
    ),
    nullable = false,
    updatable = false
  )
  @ColumnDefault("0")
  protected UserEntity createdBy;

//  @LastModifiedBy
  @ManyToOne(fetch = LAZY)
  @JoinColumn(
    name = "updated_by",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(
      name = "fk_updated_by",
      value = CONSTRAINT
    ),
    insertable = false
  )
  @ColumnDefault("null")
  protected UserEntity updatedBy;

  @CreatedDate
  @Column(
    name = "created_at",
    columnDefinition = "timestamptz",
    nullable = false,
    updatable = false
  )
  @ColumnDefault("current_timestamp")
  protected OffsetDateTime createdAt;

  @LastModifiedDate
  @Column(
    name = "updated_at",
    columnDefinition = "timestamptz",
    insertable = false
  )
  @ColumnDefault("null")
  protected OffsetDateTime updatedAt;

  @PrePersist
  protected void prePersist() {
    this.setCreatedBy(RequestContext.getUser());
  }

  @PreUpdate
  protected void preUpdate() {
    this.setUpdatedBy(RequestContext.getUser());
  }
}
