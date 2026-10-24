package com.example.QuoraApp.models;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "questions")
public class Question {
    @Id
    private String id;

    @NotBlank(message = " Title is required")
    @Size(min = 10, max = 100,message = "Title must be between 10 and 100")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 100,message = "Content must be between 10 and 100")
    private String content;
    @NotBlank(message = " Author Id is required")
    private String authorId;
    @NotBlank(message = " category is required")
    private String category;
    private Integer views;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt ;
}
