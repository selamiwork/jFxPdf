package com.est.app.pdfview;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPdfView {
    private File file;
    private int index = 0;
    private float scale = 1.0f;
    private BooleanProperty processing = new SimpleBooleanProperty(false);
    private IntegerProperty progress = new SimpleIntegerProperty(0);

    public AbstractPdfView(String path) {
        this(new File(path));
    }

    public AbstractPdfView(File file) {
        this.file = file;
    }

    public abstract void load(File file);
    public abstract int getNumberOfPages();
    public abstract Image toImage(int page, float scale);
    public abstract List<Image> getPages();
    public abstract List<Image> getPages(float scale);

    public void load(){
        load(file);
    }

    public void load(String path){
        load(new File(path));
    }

    public Image previous(){
        index = Math.max(0, index - 1);
        return toImage(index);
    }

    public Image next(){
        return toImage(++index);
    }

    public Image toImage(int page) {
        return toImage(page, scale);
    }

    public Image toImage(float scale) {
        return toImage(index, scale);
    }

    public Image toImageFit(double width, double height) {
        return toImageFit(index, width, height);
    }

    public Image toImageFit(int page, double width, double height) {
        Image image = toImage(page, 1.0f);
        float scale = image.getHeight() > image.getWidth() ? (float) ((height) / image.getHeight()) : (float) ((width) / image.getWidth());
        return toImage(page, scale == 0 ? 1.0f : scale);
    }

    public Image toImageFitHeight(double height) {
        return toImageFitHeight(index, height);
    }

    public Image toImageFitHeight(int page, double height) {
        Image image = toImage(page, 1.0f);
        float scale = (float) ((height) / image.getHeight());
        return toImage(page, scale == 0 ? 1.0f : scale);
    }

    public ImageView toImageViewFitHeight(double height) {
        return new ImageView(toImageFitHeight(index, height));
    }

    public ImageView toImageViewFitHeight(int page, double height) {
        return new ImageView(toImageFitHeight(page, height));
    }

    public ImageView toImageViewFit(double width, double height) {
        return new ImageView(toImageFit(width, height));
    }

    public ImageView toImageViewFit(int page, double width, double height) {
        return new ImageView(toImageFit(page, width, height));
    }

    public ImageView toImageView(int page) {
        return new ImageView(toImage(page, getScale()));
    }

    public ImageView toImageView(int page, float scale){
        return new ImageView(toImage(page, scale));
    }

    public ImageView toImageView(float scale){
        return new ImageView(toImage(getIndex(), scale));
    }

    public List<ImageView> getPagesAsImageView(float scale){
        return getPagesAsImageView(scale, 0);
    }

    public List<ImageView> getPagesAsImageView(float scale, double width){
        List<Image> pages = getPages(scale);
        List<ImageView> pageList = new ArrayList<>();
        for(Image image : pages){
            ImageView imageView = new ImageView(image);
            if(width > 0){
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(width);
            }
            pageList.add(imageView);
        }
        return pageList;
    }


    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isProcessing() {
        return processing.get();
    }

    public BooleanProperty processingProperty() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing.set(processing);
    }

    public int getProgress() {
        return progress.get();
    }

    public IntegerProperty progressProperty() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress.set(progress);
    }
}
