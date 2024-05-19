package ru.sfu.zooshop.dto.response.user.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllReviewsResponse {
  Page<BasicReviewResponse> reviews;
}
