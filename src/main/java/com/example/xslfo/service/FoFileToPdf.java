package com.example.xslfo.service;

import lombok.SneakyThrows;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;

public class FoFileToPdf {

    @SneakyThrows
    public static void main(String[] args) {
        File xsltFile = new File("src/main/resources/files/doc.fo");
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        File desktopDir = new File(desktopPath);

        if (!desktopDir.exists()) {
            desktopDir.mkdirs();
        }

        File pdfFile = new File(desktopDir, "test.pdf");
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, new FileOutputStream(pdfFile));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new StreamSource(xsltFile), new SAXResult(fop.getDefaultHandler()));
        System.out.println("PDF created: " + pdfFile.getAbsolutePath());
    }
}
