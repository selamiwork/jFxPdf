package com.est.app.pdfview;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfBoxView extends AbstractPdfView {
    private PDDocument document;
    private PDFRenderer renderer;
    private List<Image> pages = new ArrayList<>();

    public PdfBoxView(String path) {
        super(path);
    }

    public PdfBoxView(File file) {
        super(file);
    }

    @Override
    public void load(File file){
        if(!file.exists()){
            return;
        }
        setFile(file);
        try {
            document = PDDocument.load(file);
            renderer = new PDFRenderer(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfPages() {
        return document.getNumberOfPages();
    }

    @Override
    public Image toImage(int page, float scale) {
        try {
            int index = Math.min(page, getNumberOfPages() - 1);
            setIndex(index);
            setScale(scale);
            return SwingFXUtils.toFXImage(renderer.renderImage(index, scale), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Image> getPages(float scale) {
        List<Image> pages = new ArrayList<>();
        setProcessing(true);
        for(int i=0; i<getNumberOfPages(); i++){
            try { pages.add(SwingFXUtils.toFXImage(renderer.renderImage(i, scale), null)); } catch (Exception e) {}
            setProgress((int)(((i +1) * 100.0) / getNumberOfPages()));
        }
        setProcessing(false);
        return pages;
    }

    @Override
    public List<Image> getPages() {
        if(pages.isEmpty()){
            pages = getPages(getScale());
        }
        return pages;
    }
}
