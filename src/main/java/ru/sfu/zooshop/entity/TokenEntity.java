package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
  name = "tokens",
  indexes = {
    @Index(name = "ix_tokens_user_id", columnList = "user_id")
  }
)
//@NamedEntityGraph(
//  name = "graph_token_with_user",
//  attributeNodes = {
//    @NamedAttributeNode("user")
//  }
//)
public class TokenEntity extends BaseEntity {
  @OneToOne(
    targetEntity = UserEntity.class,
    fetch = EAGER
  )
  @JoinColumn(
    name = "user_id",
    unique = true,
    nullable = false
  )
  @OnDelete(action = CASCADE)
  private UserEntity user;

  @Column(
    unique = true,
    nullable = false
  )
  private String value;

  private String additionalData;

  public TokenEntity(UserEntity user) {
    this.user = user;
    this.value = UUID.randomUUID().toString();
  }

  public TokenEntity(UserEntity user, String additionalData) {
    this.user = user;
    this.value = UUID.randomUUID().toString();
    this.additionalData = additionalData;
  }
}
