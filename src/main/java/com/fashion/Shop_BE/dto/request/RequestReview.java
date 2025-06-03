package com.fashion.Shop_BE.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestReview {
    private Long productId;
    private int reviewRating;
    private String reviewComment;
}
