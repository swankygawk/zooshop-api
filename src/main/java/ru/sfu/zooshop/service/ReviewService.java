package ru.sfu.zooshop.service;

import org.springframework.data.domain.Pageable;
import ru.sfu.zooshop.dto.request.user.review.ReviewRequest;
import ru.sfu.zooshop.dto.request.user.review.ReviewUpdateRequest;
import ru.sfu.zooshop.dto.response.user.review.AllReviewsResponse;
import ru.sfu.zooshop.dto.response.user.review.RichReviewResponse;

public interface ReviewService {
  AllReviewsResponse getUserReviews(Long userId, Pageable pageable);
  RichReviewResponse getReview(Long userId, Long reviewId);
  Long createReview(Long userId, ReviewRequest request);
  void updateReview(Long userId, Long reviewId, ReviewUpdateRequest request);
  void deleteReview(Long userId, Long reviewId);
}
