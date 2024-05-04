package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
//@NamedEntityGraph(
//  name = "graph_role_with_authorities",
//  attributeNodes = {
//    @NamedAttributeNode("authorities")
//  }
//)
public class RoleEntity extends BaseEntity {
  @Column(
    unique = true,
    nullable = false
  )
  private String name;

  @ManyToMany(fetch = EAGER)
  @JoinTable(
    name = "role_authorities",
    joinColumns = @JoinColumn(
      name = "role_id",
      referencedColumnName = "id",
      nullable = false
    ),
    inverseJoinColumns = @JoinColumn(
      name = "authority_id",
      referencedColumnName = "id",
      nullable = false
    )
  )
  private Set<AuthorityEntity> authorities;
}
