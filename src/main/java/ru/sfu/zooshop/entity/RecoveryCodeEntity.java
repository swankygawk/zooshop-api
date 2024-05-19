package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
  name = "recovery_codes",
  indexes = {
    @Index(name = "ix_recovery_codes_user_id", columnList = "user_id"),
  },
  uniqueConstraints = {
    @UniqueConstraint(name = "unique_user_recovery_code", columnNames = {"user_id", "recovery_code"})
  }
)
public class RecoveryCodeEntity extends BaseEntity {
  @ManyToOne(targetEntity = UserEntity.class)
  @OnDelete(action = CASCADE)
  @JoinColumn(
    name = "user_id",
    nullable = false,
    updatable = false
  )
  private UserEntity user;

  @Column(
    name = "recovery_code",
    columnDefinition = "text",
    nullable = false
  )
  private String recoveryCode;
}
