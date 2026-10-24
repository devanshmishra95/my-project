package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.AnswerAdapter;
import com.example.QuoraApp.dto.AnswerRequestDTO;
import com.example.QuoraApp.dto.AnswerResponseDTO;
import com.example.QuoraApp.models.Answer;
import com.example.QuoraApp.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) {
        Answer answer = Answer.builder()
                .questionId(answerRequestDTO.getQuestionId())
                .content(answerRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return answerRepository.save(answer)
                .map(AnswerAdapter::toAnswerResponseDTO);
    }

    public Mono<AnswerResponseDTO> getAnswerById(String id) {
        return answerRepository.findById(id)
                .map(AnswerAdapter::toAnswerResponseDTO);
    }

    public Mono<AnswerResponseDTO> updateAnswer(@PathVariable String id, @RequestBody AnswerRequestDTO answerRequestDTO) {
        return answerRepository.findById(id)
                .flatMap(existingAnswer -> {
                    existingAnswer.setContent(answerRequestDTO.getContent());
                    return answerRepository.save(existingAnswer);
                })
                .map(AnswerAdapter::toAnswerResponseDTO);
    }

    public Mono<Void> deleteAnswer(String id) {
        return answerRepository.findById(id)
                .flatMap(answer -> answerRepository.deleteById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)));
    }

    public Flux<AnswerResponseDTO> getAllAnswers() {
        return answerRepository.findAll()
                .map(AnswerAdapter::toAnswerResponseDTO);
    }

    public Flux<AnswerResponseDTO> getAnswersByQuestionId(String questionId) {
        return answerRepository.findByQuestionId(questionId)
                .map(AnswerAdapter::toAnswerResponseDTO);
    }
    public Mono<AnswerResponseDTO> getAnswerCountByQuestionId(String questionId) {
        return answerRepository.findAnswerCountByQuestionId(questionId)
                .map(AnswerAdapter::toAnswerResponseDTO);
    }



    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtDesc(@PathVariable String questionId){
        return answerRepository.findByQuestionIdOrderByCreatedAtDesc(questionId)
                .map(AnswerAdapter::toAnswerResponseDTO);
    }

    @Override
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtAsc(String questionId) {
        return answerRepository.findByQuestionIdOrderByCreatedAtAsc(questionId)
                .map(AnswerAdapter::toAnswerResponseDTO);
    }
}