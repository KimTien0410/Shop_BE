package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestReview;
import com.fashion.Shop_BE.dto.response.ReviewResponse;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.entity.Review;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.ReviewMapper;
import com.fashion.Shop_BE.repository.ReviewRepository;
import com.fashion.Shop_BE.service.ProductService;
import com.fashion.Shop_BE.service.ReviewService;
import com.fashion.Shop_BE.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ReviewMapper reviewMapper;
    private final ProductService productService;

    public ReviewResponse toReviewResponse(Review review){
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .productId(review.getProduct().getProductId())
                .userId(review.getUser().getUserId())
                .email(review.getUser().getEmail())
                .avatar(review.getUser().getUserAvatar())
                .reviewRating(review.getReviewRating())
                .reviewComment(review.getReviewComment())
                .build();
    }

    @Transactional
    @Override
    public ReviewResponse addReview(RequestReview requestReview) {
        User user = userService.getUserAuthenticated();
        Review review = reviewMapper.toReview(requestReview);
        Product product = productService.getProductById(requestReview.getProductId());
        review.setReviewComment(requestReview.getReviewComment());
        review.setProduct(product);
        review.setUser(user);
        reviewRepository.save(review);
        return toReviewResponse(review);
    }
    @Transactional
    @Override
    public ReviewResponse updateReview(Long reviewId, RequestReview requestReview) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new AppException(ErrorCode.REVIEW_NOT_FOUND));
        reviewMapper.updateReview(review, requestReview);
        review.setProduct(productService.getProductById(requestReview.getProductId()));
        reviewRepository.save(review);
        return toReviewResponse(review);
    }


    @Transactional
    @Override
    public void deleteReview(Long reviewId) {
        Review review= reviewRepository.findById(reviewId)
                .orElseThrow(()->new AppException(ErrorCode.REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
    }



    @Override
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
//        Product product = productService.getProductById(productId);
        List<Review> reviews = reviewRepository.findByProduct_ProductId(productId);
        return reviews.stream().map(this::toReviewResponse).toList();
    }

    @Override
    public List<ReviewResponse> getReviewsByUserId() {
        User user = userService.getUserAuthenticated();
        if (user != null) {
            List<Review> reviews = reviewRepository.findByUser_UserId(user.getUserId());
            return reviews.stream().map(this::toReviewResponse).toList();
        }
        return List.of();
    }

    @Override
    public double getAverageRatingByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProduct_ProductId(productId);
        if (reviews.isEmpty()) {
            return 0;
        }
        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getReviewRating();
        }
        return totalRating / reviews.size();
    }
}
