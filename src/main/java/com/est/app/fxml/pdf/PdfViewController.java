package com.est.app.fxml.pdf;

import com.est.app.pdfview.AbstractPdfView;
import com.est.app.services.FxmlService;
import com.est.app.utils.EffectUtils;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class PdfViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane stackPaneContent;

    @FXML
    private TextField textfieldSearch;

    @FXML
    private ImageView imageviewLeft;

    @FXML
    private ImageView imageviewRight;

    @FXML
    private TextField textfieldPageNo;

    @FXML
    private Label labelPageCount;

    @FXML
    private ImageView imageviewRotate;

    @FXML
    private ImageView imageviewZoomOut;

    @FXML
    private ImageView imageviewZoomIn;

    @FXML
    private ImageView imageviewFit;

    @FXML
    private ListView<ImageView> listviewPages;

    @FXML
    private ImageView imageviewLoading;

    @FXML
    private Label labelProgress;

    @FXML
    private ImageView imageviewOpen;

    @FXML
    private ImageView imageviewPrint;

    private ImageView imageView;
    private static AbstractPdfView pdfViewActive;
    private static IntegerProperty open = new SimpleIntegerProperty(0);
    private AbstractPdfView pdfView;
    private double angle = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pdfView = pdfViewActive;
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(imageView != null){
                imageView.setTranslateX(Math.max(0, (newValue.doubleValue() - imageView.getImage().getWidth()) / 2));
            }
        });
        scrollPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            if(imageView != null){
                if(newValue.doubleValue() > imageView.getImage().getHeight()){
                    imageView.setImage(pdfView.toImageFitHeight(newValue.doubleValue() - 10));
                }
            }
        });

        EffectUtils.setEffect(imageviewOpen);
        EffectUtils.setEffect(imageviewLeft);
        EffectUtils.setEffect(imageviewRight);
        EffectUtils.setEffect(textfieldPageNo);
        EffectUtils.setEffect(imageviewRotate);
        EffectUtils.setEffect(imageviewZoomOut);
        EffectUtils.setEffect(imageviewZoomIn);
        EffectUtils.setEffect(imageviewFit);
        EffectUtils.setEffect(imageviewPrint);

        Tooltip.install(imageviewOpen, new Tooltip("Open PDF file"));
        Tooltip.install(imageviewLeft, new Tooltip("Previous Page"));
        Tooltip.install(imageviewRight, new Tooltip("Next Page"));
        Tooltip.install(imageviewRotate, new Tooltip("Rotate 90 °"));
        Tooltip.install(imageviewZoomOut, new Tooltip("Zoom Out"));
        Tooltip.install(imageviewZoomIn, new Tooltip("Zoom In"));
        Tooltip.install(imageviewFit, new Tooltip("Fit Page"));
        Tooltip.install(imageviewPrint, new Tooltip("Print Page"));

        imageviewOpen.setOnMouseClicked(event ->  Platform.runLater(() -> open.set(open.get() + 1)));
        //imageviewPrint.setOnMouseClicked(event ->  print(imageView));
        imageviewPrint.setDisable(true);
        imageviewLeft.setOnMouseClicked(event -> { imageView.setImage(pdfView.previous()); update(); });
        imageviewRight.setOnMouseClicked(event -> { imageView.setImage(pdfView.next()); update();});
        imageviewRotate.setOnMouseClicked(event -> EffectUtils.setToAngel(imageView, 500, angle += 90));
        imageviewZoomOut.setOnMouseClicked(event -> imageView.setImage(pdfView.toImage(pdfView.getScale() * 0.9f)));
        imageviewZoomIn.setOnMouseClicked(event -> imageView.setImage(pdfView.toImage(pdfView.getScale() * 1.1f)));
        imageviewFit.setOnMouseClicked(event -> imageView.setImage(pdfView.toImageFitHeight(scrollPane.getHeight() - 10)));
        textfieldPageNo.setOnAction(event -> { try { imageView.setImage(pdfView.toImage(Math.max(0, Integer.parseInt(textfieldPageNo.getText()) - 1))); } catch (NumberFormatException e) { } update(); });

        new Thread(() -> load()).start();
    }

    private void load(){
        pdfView.load();
        imageView = pdfView.toImageViewFitHeight(0, scrollPane.getHeight());
        imageView.setOnScroll(event -> {
            if(event.isControlDown()){
                imageView.setImage(pdfView.toImage(pdfView.getScale() * (event.getDeltaY() > 0 ? 1.05f : 0.95f)));
            }
        });
        imageView.setTranslateX(Math.max(0, (scrollPane.getWidth() - imageView.getImage().getWidth()) / 2));
        Platform.runLater(() -> {
            stackPaneContent.getChildren().add(imageView);
            EffectUtils.setRounding(imageviewLoading);
            imageviewLoading.visibleProperty().bind(pdfView.processingProperty());
            labelProgress.visibleProperty().bind(pdfView.processingProperty());
            //labelProgress.textProperty().bind(pdfView.progressProperty().asString("%% %d"));
            pdfView.progressProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> labelProgress.setText(String.format("%% %d", newValue.intValue()))));
        });
        update();

        double fitWidth = 200;
        float scale = (float) (fitWidth / imageView.getImage().getWidth());
        List<ImageView> pagesAsImageView = pdfView.getPagesAsImageView(scale, fitWidth);
        listviewPages.getItems().addAll(pagesAsImageView);
        listviewPages.setCellFactory(param -> new ThumbnailListCellController());
    }

    private void update(){
        Platform.runLater(() -> {
            labelPageCount.setText("/ " + pdfView.getNumberOfPages());
            textfieldPageNo.setText("" + (pdfView.getIndex() + 1));
        });
    }

    /**
     * Todo : Must be implement
     * @param node
     */
    private void print(Node node){
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    public static IntegerProperty openProperty() {
        return open;
    }

    public static void setPdfViewActive(AbstractPdfView pdfViewActive) {
        PdfViewController.pdfViewActive = pdfViewActive;
    }

    public class ThumbnailListCellController extends ListCell<ImageView> implements Initializable {
        @Override
        public void initialize(URL location, ResourceBundle resources) {}

        @Override
        protected void updateItem(ImageView item, boolean empty){
            super.updateItem(item, empty);
            if (item != null) {
                EffectUtils.setEffect(this);
                setOnMouseClicked(event -> {
                    try { imageView.setImage(pdfView.toImage(Math.max(0, listviewPages.getItems().indexOf(item)))); } catch (NumberFormatException e) { } update();
                });
            }
            setGraphic(item);
        }
    }
}
