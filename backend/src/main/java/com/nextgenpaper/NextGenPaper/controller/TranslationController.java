package com.nextgenpaper.NextGenPaper.controller;

import com.nextgenpaper.NextGenPaper.dto.translate.TranslateLanguage;
import com.nextgenpaper.NextGenPaper.dto.translate.TranslateRequest;
import com.nextgenpaper.NextGenPaper.service.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/translate")
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping("/get-supported-languages")
    public ResponseEntity<TranslateLanguage[]> getSupportedLanguages() {
        TranslateLanguage[] supportedLanguages = translationService.getSupportedLanguages();
        return ResponseEntity.ok(supportedLanguages);
    }

    @PostMapping("")
    public ResponseEntity<String> translate(@RequestBody TranslateRequest request) {
        String paper = translationService.translatePaper(request);
        return ResponseEntity.ok(paper);
    }

}
