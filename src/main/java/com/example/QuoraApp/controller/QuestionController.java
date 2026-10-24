package com.example.QuoraApp.controller;

import com.example.QuoraApp.dto.QuestionRequestDTO;
import com.example.QuoraApp.dto.QuestionResponseDTO;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.services.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final IQuestionService questionService;
    @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO){
        return questionService.createQuestion(questionRequestDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully"+ response))
                .doOnError(error -> System.out.println("Error creating question"+ error.getMessage()));

    }
    @GetMapping("/tag/{tag}")
    public Flux<QuestionResponseDTO> getQuestionsByTag(@PathVariable String tag,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
                                                       )
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return  questionService.searchQuestions(query, page, size);
    }
    @GetMapping
    public Flux<QuestionResponseDTO> getAllQuestions(
            @RequestParam(required = false) String cursor,  // GET /questions?cursor=2026-01-23T10:30:00&size=10
             @RequestParam(defaultValue = "10") int size
    ){
        return  questionService.getAllQuestions(cursor, size);

    }
    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id)
                .doOnError(error -> System.out.println("Error fetching question: " + error))
                .doOnSuccess(response -> System.out.println("Question fetched successfully: " + response));
    }
    @GetMapping("/elasticsearch")
    public List<QuestionElasticDocument> searchQuestionsByElasticSearch(@RequestParam String query) {
        return questionService.searchQuestionByElasticSearch(query);
    }


}
