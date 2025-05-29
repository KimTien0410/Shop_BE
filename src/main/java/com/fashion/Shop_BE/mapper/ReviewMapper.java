package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestReview;
import com.fashion.Shop_BE.dto.response.ReviewResponse;
import com.fashion.Shop_BE.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toReview(RequestReview requestReview);
    Review updateReview(@MappingTarget Review review, RequestReview requestReview);
    ReviewResponse toReviewResponse(Review review);
}
