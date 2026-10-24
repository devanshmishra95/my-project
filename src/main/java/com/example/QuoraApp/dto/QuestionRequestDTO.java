package com.example.QuoraApp.dto;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class QuestionRequestDTO {
    private String title;
    private String content;
}
