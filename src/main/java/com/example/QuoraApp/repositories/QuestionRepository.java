package com.example.QuoraApp.repositories;

import com.example.QuoraApp.models.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface QuestionRepository extends ReactiveMongoRepository<Question,String>  {
  // Flux<Question> findByAuthorId(String authorId); //flux is for more than one records,proocesses data as a stream
 //  Mono<Long> countByAuthorId(String authorId); //represents a single value
   @Query("{ '$or' : [{'title' : { $regex :  ?0, $options :  'i'}},{'content' : { $regex :  ?0, $options :  'i'}}]}")
   Flux<Question> findByTitleOrContentContainingIgnoreCase(String searchTerm, PageRequest pageable);
   Flux<Question> findByCreatedAtGreaterThanOrderByCreatedAtAsc(LocalDateTime cursor, Pageable pageable);
   Flux<Question> findTop10ByOrderByCreatedAtAsc();
}

//@Query("{ '$or' : [{'title' : { $regex :  ?0, $options :  'i'}},{'content' : { $regex :  ?0, $options :  'i'}}]}")
//Flux<Question> findByTitleOrContentContainingIgnoreCase(String searchTerm, PageRequest pageable);
//Ye MongoDB query $or ke through title ya content me se kisi ek field me diye gaye search term ko regex ke saath (case-insensitive) match karke questions return karti hai./
//Give me all questions where either the title or the content contains the given search term, ignoring case, and return the results as a reactive stream with pagination.”