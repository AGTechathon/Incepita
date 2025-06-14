package com.nextgenpaper.NextGenPaper.service.pdfbox;

import com.nextgenpaper.NextGenPaper.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface IPdfService {
    public String pdfToText(MultipartFile file);

    public byte[] generatePDF(ArrayList<Question> questions);
}
