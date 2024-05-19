package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.request.user.review.ReviewRequest;
import ru.sfu.zooshop.dto.request.user.review.ReviewUpdateRequest;
import ru.sfu.zooshop.dto.response.user.review.AllReviewsResponse;
import ru.sfu.zooshop.dto.response.user.review.BasicReviewResponse;
import ru.sfu.zooshop.dto.response.user.review.RichReviewResponse;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.entity.ReviewEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.UserMapper;
import ru.sfu.zooshop.repository.ReviewRepository;
import ru.sfu.zooshop.service.ProductService;
import ru.sfu.zooshop.service.ReviewService;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;
  private final ProductService productService;
  private final UserMapper userMapper;

  private void validate(Long userId, ProductEntity product) {
    if (reviewRepository.existsByCreatedByIdAndProduct(userId, product)) throw new ApiException(CONFLICT, "You have already reviewed this product");
  }

  private ReviewEntity getReviewById(Long reviewId, Long userId) {
    return reviewRepository.findByIdAndCreatedByIdAndProductHiddenFalse(reviewId, userId)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Review with ID " + reviewId + " not found"));
  }

  @Override
  public AllReviewsResponse getUserReviews(Long userId, Pageable pageable) {
    Page<BasicReviewResponse> reviews = reviewRepository.findAllByCreatedByIdAndProductHiddenFalse(userId, pageable)
      .map(userMapper::reviewEntityToBasicReviewResponse);
    return new AllReviewsResponse(reviews);
  }

  @Override
  public RichReviewResponse getReview(Long userId, Long reviewId) {
    ReviewEntity review = getReviewById(reviewId, userId);
    return userMapper.reviewEntityToRichReviewResponse(review);
  }

  @Override
  public Long createReview(Long userId, ReviewRequest request) {
    ProductEntity product = productService.findProductById(request.getProductId());
    validate(userId, product);
    ReviewEntity review = reviewRepository.save(new ReviewEntity(
      product,
      request.getRating(),
      request.getComment()
    ));
    return review.getId();
  }

  @Override
  public void updateReview(Long userId, Long reviewId, ReviewUpdateRequest request) {
    ReviewEntity review = getReviewById(reviewId, userId);
    review.setRating(request.getRating());
    review.setComment(request.getComment());
    reviewRepository.save(review);
  }

  @Override
  public void deleteReview(Long userId, Long reviewId) {
    ReviewEntity review = getReviewById(reviewId, userId);
    reviewRepository.delete(review);
  }
}
