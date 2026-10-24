package com.example.QuoraApp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponseDTO {
    private String targetId;
    private String id;
    private String targetType;
    private LocalDateTime createdAt;

}
