package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestReview;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.ReviewResponse;
import com.fashion.Shop_BE.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping()
    public ApiResponse<ReviewResponse> addReview(@RequestBody RequestReview requestReview) {
        ReviewResponse reviewResponse = reviewService.addReview(requestReview);
        return ApiResponse.<ReviewResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm đánh giá thành công")
                .data(reviewResponse)
                .build();
    }
    @PutMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> updateReview(@PathVariable Long reviewId, @RequestBody RequestReview requestReview) {
        ReviewResponse reviewResponse = reviewService.updateReview(reviewId, requestReview);
        return ApiResponse.<ReviewResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật đánh giá thành công")
                .data(reviewResponse)
                .build();
    }
    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xóa đánh giá thành công")
                .build();
    }
    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewResponse> reviewResponses = reviewService.getReviewsByProductId(productId);
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách đánh giá thành công")
                .data(reviewResponses)
                .build();
    }
    @GetMapping("/user")
    public ApiResponse<List<ReviewResponse>> getReviewsByUserId() {
        List<ReviewResponse> reviewResponses = reviewService.getReviewsByUserId();
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách đánh giá của người dùng thành công")
                .data(reviewResponses)
                .build();
    }
    @GetMapping("/product/{productId}/average-rating")
    public ApiResponse<Double> getAverageRatingByProductId(@PathVariable Long productId) {
        double averageRating = reviewService.getAverageRatingByProductId(productId);
        return ApiResponse.<Double>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy đánh giá trung bình thành công")
                .data(averageRating)
                .build();
    }


}
