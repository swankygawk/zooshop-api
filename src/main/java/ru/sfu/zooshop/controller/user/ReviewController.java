package ru.sfu.zooshop.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshop.dto.request.user.review.ReviewRequest;
import ru.sfu.zooshop.dto.request.user.review.ReviewUpdateRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.user.review.AllReviewsResponse;
import ru.sfu.zooshop.dto.response.user.review.RichReviewResponse;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.ReviewService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/review")
@Validated
public class ReviewController {
  private final ReviewService reviewService;

  private URI getUri(Long id) {
    return URI.create("http://localhost:8080/api/v1/user/review/" + id);
  }

  @GetMapping
//  @PreAuthorize("hasAuthority('REVIEW:READ_OWN')")
  public ResponseEntity<Response> getAll(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    Pageable pageable
  ) {
    AllReviewsResponse reviews = reviewService.getUserReviews(user.getId(), pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Reviews retrieved",
      reviews
    ));
  }

  @GetMapping("/{id}")
//  @PreAuthorize("hasAuthority('REVIEW:READ_OWN')")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @PathVariable("id") @NotNull(message = "Review ID must not be null") @PositiveOrZero(message = "Review ID must be greater or equal to 0") Long id
  ) {
    RichReviewResponse review = reviewService.getReview(user.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Review retrieved",
      review
    ));
  }

  @PostMapping
//  @PreAuthorize("hasAuthority('REVIEW:CREATE_OWN')")
  public ResponseEntity<Response> create(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid ReviewRequest review
  ) {
    Long id = reviewService.createReview(user.getId(), review);
    return ResponseEntity.created(getUri(id)).body(getResponse(
      request,
      CREATED,
      "Review created",
      null
    ));
  }

  @PatchMapping("/{id}")
//  @PreAuthorize("hasAuthority('REVIEW:UPDATE_OWN')")
  public ResponseEntity<Response> update(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @PathVariable("id") @NotNull(message = "Review ID must not be null") @PositiveOrZero(message = "Review ID must be greater or equal to 0") Long id,
    @RequestBody @Valid ReviewUpdateRequest review
  ) {
    reviewService.updateReview(user.getId(), id, review);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Review updated",
      null
    ));
  }

  @DeleteMapping("/{id}")
//  @PreAuthorize("hasAuthority('REVIEW:DELETE_OWN')")
  public ResponseEntity<Response> delete(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @PathVariable("id") @NotNull(message = "Review ID must not be null") @PositiveOrZero(message = "Review ID must be greater or equal to 0") Long id
  ) {
    reviewService.deleteReview(user.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Review deleted",
      null
    ));
  }
}
