package com.example.QuoraApp.services;

import com.example.QuoraApp.models.Question;
import com.example.QuoraApp.models.QuestionElasticDocument;
import com.example.QuoraApp.repositories.QuestionDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService {
    private final QuestionDocumentRepository questionDocumentRepository;

    public void createQuestionIndex(Question question) {

        QuestionElasticDocument document = QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();

        questionDocumentRepository.save(document);
    }
}
