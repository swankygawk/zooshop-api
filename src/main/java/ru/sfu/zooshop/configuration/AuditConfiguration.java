package ru.sfu.zooshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static java.time.OffsetDateTime.now;
import static java.util.Optional.of;

//@RequiredArgsConstructor
@Configuration
@EnableJpaAuditing(
  modifyOnCreate = false,
//  auditorAwareRef = "auditorAware",
  dateTimeProviderRef = "dateTimeProvider"
)
public class AuditConfiguration {
//  private final UserService userService; // TODO get rid of RequestContext and use SecurityContext instead (with system as authenticated user if nobody is authenticated)?
//
//  @Bean
//  public AuditorAware<UserEntity> auditorAware() {
////    return () -> of(userService.getSystem());
//    return () -> {
//      var u = SecurityContextHolder.getContext().getAuthentication();
//      if (u.isAuthenticated() && !Objects.equals(u.getPrincipal(), "anonymousUser")) {
//        return Optional.ofNullable(userService.getUserByEmail((String) u.getPrincipal()));
//      }
//      return Optional.ofNullable(userService.getSystem());
//    };
//  }
//
  @Bean
  public DateTimeProvider dateTimeProvider() {
    return () -> of(now());
  }
}
