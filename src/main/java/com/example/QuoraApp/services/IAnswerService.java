package com.example.QuoraApp.services;

import com.example.QuoraApp.dto.AnswerRequestDTO;
import com.example.QuoraApp.dto.AnswerResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    public Mono<AnswerResponseDTO> getAnswerById(String id);
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);
    public Mono<AnswerResponseDTO> updateAnswer(@PathVariable String id, @RequestBody AnswerRequestDTO answerRequestDTO);

    public Mono<Void> deleteAnswer(String id);
    public Flux<AnswerResponseDTO> getAllAnswers();
    public Flux<AnswerResponseDTO> getAnswersByQuestionId(@PathVariable String questionId);
    public Mono<AnswerResponseDTO> getAnswerCountByQuestionId(@PathVariable String questionId);
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtDesc(String questionId);
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtAsc(String questionId);
}
