package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {

    Flux<Answer> findByQuestionId(String questionId);
    Mono<Answer> findAnswerCountByQuestionId(String questionId);
    Flux<Answer> findByQuestionIdOrderByCreatedAtDesc(String questionId);
    Flux<Answer> findByQuestionIdOrderByCreatedAtAsc(String questionId);
}
