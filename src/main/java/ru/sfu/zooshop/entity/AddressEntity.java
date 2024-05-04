package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
  name = "addresses"
)
public class AddressEntity extends BaseEntity {
  @ManyToOne(
    fetch = LAZY,
    optional = false
  )
  @JoinColumn(
    name = "user_id",
    nullable = false,
    updatable = false
  )
  @OnDelete(action = CASCADE)
  private UserEntity user;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String house;

  private Integer flat;
}
