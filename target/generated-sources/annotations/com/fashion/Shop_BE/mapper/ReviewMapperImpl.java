package com.fashion.Shop_BE.mapper;

import com.fashion.Shop_BE.dto.request.RequestReview;
import com.fashion.Shop_BE.dto.response.ReviewResponse;
import com.fashion.Shop_BE.entity.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T17:14:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review toReview(RequestReview requestReview) {
        if ( requestReview == null ) {
            return null;
        }

        Review.ReviewBuilder review = Review.builder();

        review.reviewRating( requestReview.getReviewRating() );
        review.reviewComment( requestReview.getReviewComment() );

        return review.build();
    }

    @Override
    public Review updateReview(Review review, RequestReview requestReview) {
        if ( requestReview == null ) {
            return review;
        }

        review.setReviewRating( requestReview.getReviewRating() );
        review.setReviewComment( requestReview.getReviewComment() );

        return review;
    }

    @Override
    public ReviewResponse toReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.reviewId( review.getReviewId() );
        reviewResponse.reviewRating( review.getReviewRating() );
        reviewResponse.reviewComment( review.getReviewComment() );

        return reviewResponse.build();
    }
}
