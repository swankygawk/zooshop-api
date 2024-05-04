package ru.sfu.zooshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;

import java.time.OffsetDateTime;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
  name = "users",
  indexes = {
    @Index(name = "ix_users_user_id", columnList = "user_id"),
    @Index(name = "ix_users_email", columnList = "email")
  }
)
//@NamedEntityGraph(
//  name = "graph_user_with_password_and_role",
//  attributeNodes = {
//    @NamedAttributeNode("credential"),
//    @NamedAttributeNode("role")
//  }
//)
public class UserEntity extends BaseEntity {
  @Column(
      unique = true,
      nullable = false,
      updatable = false
  )
  private String userId;

  @Column(
      unique = true,
      nullable = false
  )
  private String email;

  @OneToOne(
    targetEntity = CredentialEntity.class,
    fetch = EAGER
  )
  @OnDelete(action = CASCADE)
  @JoinColumn(
    name = "credential_id",
    unique = true,
    nullable = false,
    updatable = false
  )
  private CredentialEntity credential;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @ColumnDefault("null")
  private String phone;

  @ColumnDefault("null")
  private String profilePictureUrl;

  @Column(columnDefinition = "timestamptz")
  @ColumnDefault("current_timestamp")
  private OffsetDateTime lastSignIn;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer signInAttempts;

  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean enabled;

  @Column(nullable = false)
  @ColumnDefault("true")
  private boolean accountNonLocked;

  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean temporarilyLocked;

  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean mfaEnabled;

  @ColumnDefault("null")
  private String mfaSecret;

  @ManyToOne(
    fetch = EAGER,
    optional = false
  )
  @JoinColumn(
    name = "role_id",
    nullable = false
  )
  private RoleEntity role;

  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

  public String getUsername() {
    return this.getEmail();
  }

  public String getPassword() {
    return this.getCredential().getPassword();
  }

  public Set<AuthorityEntity> getAuthorities() {
    return this.getRole().getAuthorities();
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return this.getCredential().isCredentialsNonExpired();
  }
}
