package com.example.QuoraApp.consumers;

import com.example.QuoraApp.config.KafkaConfig;
import com.example.QuoraApp.events.ViewCountEvent;
import com.example.QuoraApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {
    private final QuestionRepository questionRepository;
    @KafkaListener(
            topics = KafkaConfig.TOPIC_NAME,
            groupId = "view-count-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
        questionRepository.findById(viewCountEvent.getTargetId())
                .flatMap(question -> {
                    System.out.println("Incrementing view count for question: " + question.getId());
                    Integer views = question.getViews();
                    question.setViews(views == null ? 0 : views + 1);
                    return questionRepository.save(question);
                })
                .subscribe(updatedQuestion -> {
                    System.out.println("View count incremented for question: " + updatedQuestion.getId());
                }, error -> {
                    System.out.println("Error incrementing view count for question: " + error.getMessage());
                });
    }
                }

//Kafka listener event receive karta hai, repository se question fetch karta hai, view count update karke database me save
// karta hai, aur subscribe() ke through reactive flow ko execute karta hai.


//flatMap isliye use kiya kyunki andar se bhi Mono return ho raha hai;
//map use karte to Mono<Mono<T>> ban jaata (galat).
