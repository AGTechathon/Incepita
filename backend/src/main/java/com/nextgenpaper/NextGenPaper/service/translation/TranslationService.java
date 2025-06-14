package com.nextgenpaper.NextGenPaper.service.translation;

import com.nextgenpaper.NextGenPaper.dto.translate.TranslateLanguage;

import com.nextgenpaper.NextGenPaper.dto.translate.TranslateRequest;
import com.nextgenpaper.NextGenPaper.service.openrouter.OpenRouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TranslationService implements ITranslationService{

    private final OpenRouterService openRouterService;
    private final String URL = "https://libretranslate-nn58.onrender.com/";

//    Translate the paper from one language to other
    public String translatePaper(TranslateRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> body = new HashMap<>();
        body.put("source", request.getFrom());
        body.put("target", request.getTo());
        body.put("q", request.getQuestionPaper());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URL + "translate", entity, String.class);

        System.out.println(response.getBody());

        return response.getBody();
    }
//Get all the supported languages
    public TranslateLanguage[] getSupportedLanguages() {
        RestTemplate restTemplate = new RestTemplate();
        TranslateLanguage[] supportedLanguages =  restTemplate.getForObject(URL+"languages", TranslateLanguage[].class);
        return supportedLanguages;
    }
}
