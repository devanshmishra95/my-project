package com.example.QuoraApp.services;

import com.example.QuoraApp.adapter.QuestionAdapter;
import com.example.QuoraApp.dto.QuestionRequestDTO;
import com.example.QuoraApp.dto.QuestionResponseDTO;
import com.example.QuoraApp.events.ViewCountEvent;
import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.producers.KafkaEventProducer;
import com.example.QuoraApp.repositories.QuestionDocumentRepository;
import com.example.QuoraApp.repositories.QuestionRepository;
import com.example.QuoraApp.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable; // ✅ correct one


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository;
    private final KafkaEventProducer kafkaEventProducer;
    private final IQuestionIndexService questionIndexService;
    private final QuestionDocumentRepository questionDocumentRepository;



    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = Question.builder()
                .title(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return questionRepository.save(question)
                .map(savedQuestion ->
                { questionIndexService.createQuestionIndex(savedQuestion);
        return QuestionAdapter.toQuestionResponseDTO(savedQuestion);
                })
                        /// yha .map use hoga kyuki reactive piepline h i.e non b+-blocking ....isiliye normal mapping jki upar ki wo nhi kr skte
                .doOnSuccess(response -> System.out.println("Question created successfully" + response))
                .doOnError(error -> System.out.println("Error creating question" + error.getMessage()));
    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page ) {
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset,page))
                .map(QuestionAdapter :: toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error searching questions : " + error))
                .doOnComplete(() -> System.out.println("Questions searches successfully"));
    }
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor,int size) {
        Pageable pageable = PageRequest.of(0,size);
        if (CursorUtils.isValidCursor(cursor)) {
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);        //LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor)Cursor ko string se real date-time me convert kar raha hai
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp,pageable)//select * from table where created_at> cursor limit 10 Order by created_at
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions : " + error))
                    .doOnComplete(() -> System.out.println("Questions fetches successfully"));
        } else {
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions : " + error))
                    .doOnComplete(() -> System.out.println("Questions fetches successfully"));
        }
    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id) {
        return questionRepository.findById(id)
          .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error fetching question: " + error))
                .doOnSuccess(response -> {
                    System.out.println("Question fetched successfully: " + response);
                    ViewCountEvent viewCountEvent = new ViewCountEvent(id, "question", LocalDateTime.now());
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                });

    }

    @Override
    public List<QuestionElasticDocument> searchQuestionByElasticSearch(String query) {
        return questionDocumentRepository.findByTitleContainingOrContentContaining(query,query);
    }

}