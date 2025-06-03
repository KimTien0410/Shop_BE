package com.fashion.Shop_BE.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Long productId;
    private Long userId;
    private String email;
    private String avatar;
    private int reviewRating;
    private String reviewComment;


}
