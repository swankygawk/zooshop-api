package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.sfu.zooshop.enumeration.AuthorityAction;
import ru.sfu.zooshop.enumeration.AuthorityResource;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
  name = "authorities",
  uniqueConstraints = {
    @UniqueConstraint(
      name = "unique_authority_resource_action",
      columnNames = {
        "resource",
        "action"
      }
    )
  }
)
public class AuthorityEntity extends BaseEntity {
  @Enumerated(value = STRING)
  @Column(nullable = false)
  private AuthorityResource resource;

  @Enumerated(value = STRING)
  @Column(nullable = false)
  private AuthorityAction action;
}
