package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestReview;
import com.fashion.Shop_BE.dto.response.ReviewResponse;
import com.fashion.Shop_BE.entity.Review;

import java.util.List;

public interface ReviewService {
    public ReviewResponse addReview(RequestReview requestReview);

    public ReviewResponse updateReview(Long reviewId, RequestReview requestReview);

   public  void deleteReview(Long reviewId);

   public List<ReviewResponse> getReviewsByProductId(Long productId);

   public List<ReviewResponse> getReviewsByUserId();

    double getAverageRatingByProductId(Long productId);
}
