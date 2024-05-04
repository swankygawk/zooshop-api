package ru.sfu.zooshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;

import static java.time.OffsetDateTime.now;
import static ru.sfu.zooshop.constant.Constant.CREDENTIAL_EXPIRATION_DAYS;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credentials")
public class CredentialEntity extends BaseEntity {
  @Column(
    name = "password",
    columnDefinition = "text",
    nullable = false
  )
  private String password;

  public OffsetDateTime getExpirationTime() {
    return this.getUpdatedAt() == null
      ? this.getCreatedAt().plusDays(CREDENTIAL_EXPIRATION_DAYS)
      : this.getUpdatedAt().plusDays(CREDENTIAL_EXPIRATION_DAYS);
  }

  public boolean isCredentialsNonExpired() {
    return this.getUpdatedAt() == null
      ? (this.getCreatedAt().plusDays(CREDENTIAL_EXPIRATION_DAYS).isAfter(now()))
      : (this.getUpdatedAt().plusDays(CREDENTIAL_EXPIRATION_DAYS).isAfter(now()));
  }
}
