package com.nextgenpaper.NextGenPaper.service.translation;

import com.nextgenpaper.NextGenPaper.dto.translate.TranslateLanguage;
import com.nextgenpaper.NextGenPaper.dto.translate.TranslateRequest;

public interface ITranslationService {
    public String translatePaper(TranslateRequest request);
    public TranslateLanguage[] getSupportedLanguages();
}
