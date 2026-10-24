package com.example.QuoraApp.adapter;

import com.example.QuoraApp.dto.AnswerResponseDTO;
import com.example.QuoraApp.dto.QuestionResponseDTO;
import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.models.Question;

public class QuestionAdapter {
    public static QuestionResponseDTO toQuestionResponseDTO(Question question){
    return QuestionResponseDTO.builder()
            .id(question.getId())
            .title(question.getTitle())
            .content(question.getContent())
            .createdAt(question.getCreatedAt())
            .build();
    }
    public static AnswerResponseDTO toResponseDTO(Answer answer) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .build();
    }
}
