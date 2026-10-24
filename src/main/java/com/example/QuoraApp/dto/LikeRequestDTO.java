package com.example.QuoraApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeRequestDTO {
    private String targetId;
    private String targetType;
    private Boolean isLike;
}
