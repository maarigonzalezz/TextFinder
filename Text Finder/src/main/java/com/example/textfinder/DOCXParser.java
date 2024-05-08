package com.example.textfinder;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DOCXParser {
    private File DOCX;

    private String parsedText;

    private int WordCounter;

    //Constructor de DOCXParser que inicializa el parser con un archivo DOCX.
    public DOCXParser(File DOCXFile) {
        this.DOCX = DOCXFile;
        String DOCXPath = this.DOCX.getPath();
        this.parsedText = parse();  // Llama a parse para extraer el texto del documento
        this.WordCounter = countWords(this.parsedText);  // Cuenta las palabras en el texto extraído
    }


    //Extrae el texto de un documento DOCX utilizando XWPFWordExtractor.
    public String parse() {
        try (FileInputStream fis = new FileInputStream(this.DOCX);  // Abre el archivo DOCX
             XWPFDocument document = new XWPFDocument(fis);  // Carga el documento DOCX
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {  // Extrae el texto del documento
            return extractor.getText();  // Devuelve el texto extraído
        } catch (IOException e) {
            e.printStackTrace();  // Imprime la pila de errores si ocurre uno
            return null;
        }
    }

    //Cuenta el número de palabras en un texto dado.
    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;  // Si no hay texto, retorna 0
        }
        String[] words = text.split("\\s+");  // Divide el texto en palabras por espacios
        return words.length;  // Retorna la cantidad de palabras
    }

    // Getter para obtener el texto parseado
    public String getParsedText() {
        return parsedText;
    }

    // Getter para obtener el contador de palabras
    public int getWordCounter() {
        return WordCounter;
    }
}


