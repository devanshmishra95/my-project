package com.example.QuoraApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponseDTO {
    private String content;
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
