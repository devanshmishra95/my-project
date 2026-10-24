package com.example.QuoraApp.controller;

import com.example.QuoraApp.dto.AnswerRequestDTO;
import com.example.QuoraApp.dto.AnswerResponseDTO;
import com.example.QuoraApp.services.IAnswerService;
import com.example.QuoraApp.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    private IAnswerService answerService;


    @PostMapping
    public Mono<ResponseEntity<AnswerResponseDTO>> createAnswer(@Valid @RequestBody AnswerRequestDTO answerRequestDTO) {
        return answerService.createAnswer(answerRequestDTO)
                .map(answer -> ResponseEntity.status(HttpStatus.CREATED).body(answer))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AnswerResponseDTO>> getAnswerById(@PathVariable String id) {
        return answerService.getAnswerById(id)
                .map(answer -> ResponseEntity.ok(answer))
                .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AnswerResponseDTO>> updateAnswer(@PathVariable String id, @RequestBody AnswerRequestDTO answerRequestDTO) {
        return answerService.updateAnswer(id, answerRequestDTO)
                .map(answer -> ResponseEntity.ok(answer));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAnswer(@PathVariable String id) {
        return answerService.deleteAnswer(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Flux<AnswerResponseDTO> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/question/{questionId}")
    public Flux<AnswerResponseDTO> getAnswersByQuestionId(@PathVariable String questionId) {
        return answerService.getAnswersByQuestionId(questionId);
    }

    @GetMapping("/question/questionId/count")
    public Mono<ResponseEntity<AnswerResponseDTO>> getAnswerCountByQuestionId(@PathVariable String questionId) {
        return answerService.getAnswerCountByQuestionId(questionId)
                .map(count -> ResponseEntity.ok(count))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/question/{questionId}/latest")
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdLatestFirst(@PathVariable String questionId) {
        return answerService.getAnswersByQuestionIdOrderByCreatedAtDesc(questionId);
    }
    @GetMapping("/question/{questionId}/oldest")
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOldestFirst(@PathVariable String questionId) {
        return answerService.getAnswersByQuestionIdOrderByCreatedAtAsc(questionId);
    }
}
