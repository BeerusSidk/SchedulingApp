package cs309.backend.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Scanner;

@Service
public class ReaderService {
    public LinkedHashSet<String> extractTextFromPdf(InputStream pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String text = pdfTextStripper.getText(document);
        document.close();
        return textFilter(text);
    }

    private LinkedHashSet<String> textFilter(String args) {
        LinkedHashSet<String> arr = new LinkedHashSet<>();
        Scanner scr = new Scanner(args);
        while (scr.hasNextLine()) {
            String line = scr.nextLine();
            if (line.startsWith("F") || line.startsWith("S")) {  // TODO: REGEX???????
                if(line.length() != 6) {
                    if (!line.contains(".")) {
                        line = line.substring(24);
                        arr.add(line);
                    }
                    else {
                        int index = line.indexOf(".");
                        line = line.substring(0, index - 2);
                        arr.add(line);
                    }
                }
            }
        }
        scr.close();
        return arr;
    }
}
