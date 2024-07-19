package com.example.xslfo.service;

import com.example.xslfo.model.Book;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GeneratePdfService {

    @PostConstruct
    public void runCode() {
        Book book1 = new Book();
        book1.setAuthor("John");
        book1.setTitle("John's book");

        Book book2 = new Book();
        book2.setAuthor("Tom");
        book2.setTitle("Tom's book");

        Book book3 = new Book();
        book3.setAuthor("Martin");
        book3.setTitle("Martin's book");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        try {
            generatePdf(bookList);
        } catch (Exception e) {
            log.error("Error generating PDF", e);
        }
    }

    private void generatePdf(List<Book> data) throws Exception {

        ClassPathResource xslTemplateResource = new ClassPathResource("files/book.xsl");
        if (!xslTemplateResource.exists()) {
            throw new FileNotFoundException("XSL template not found");
        }

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
        String xmlString = xmlMapper.writer().withRootName("data").writeValueAsString(data);

        System.out.println(xmlString);

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        File pdfFile = new File(desktopPath + File.separator + "output.pdf");

        try (OutputStream out = new FileOutputStream(pdfFile)) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslTemplateResource.getInputStream()));

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            Source src = new StreamSource(new StringReader(xmlString));
            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);
        }
    }
}
