package com.est.app.fxml;

import com.est.app.fxml.pdf.PdfViewController;
import com.est.app.pdfview.PdfBoxView;
import com.est.app.services.FxmlService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.prefs.Preferences;

public class MainController implements Initializable {
    private static Preferences preferences = Preferences.userRoot();
    public static Preferences getPreferences() { try { preferences.sync(); } catch (Exception e) { } return preferences; }

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane root;

    @FXML
    private ImageView imageviewBackground;

    private TabPane tabPane = new TabPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            Stage primaryStage = (Stage) root.getScene().getWindow();
            primaryStage.getIcons().add(new Image("/icons/icons8_pdf_100px.png"));
            primaryStage.setOnCloseRequest(event -> {
                //event.consume();
                //logger.debug("Shutting down...");
                primaryStage.hide();
                exit();
            });
        });

        double width = root.getPrefWidth();
        double height = root.getPrefHeight();
        stackPane.heightProperty().addListener((observable, oldValue, newValue) -> root.setScaleY((double) newValue / height));
        stackPane.widthProperty().addListener((observable, oldValue, newValue) ->  root.setScaleX((double) newValue / width));

        tabPane.getSelectionModel().clearSelection();
        tabPane.setSide(Side.TOP);
        stackPane.getChildren().add(tabPane);

        PdfViewController.openProperty().addListener((observable, oldValue, newValue) -> open());

        //Create the menu bar.
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(open, new SeparatorMenuItem(), exit);
        menuBar.getMenus().add(fileMenu);
        borderPane.setTop(menuBar);

        open.setOnAction(event -> open());
        exit.setOnAction(event -> exit());


        //load("D:\\tmp\\cc1352r.pdf");
    }

    private void load(String path){
        load(new File(path));
    }

    private void load(File file){
        try {
            PdfBoxView pdfView = new PdfBoxView(file);
            PdfViewController.setPdfViewActive(pdfView);
            Parent parent = FxmlService.load("fxml/pdf/pdfview.fxml");
            Tab tab = new Tab("   " + pdfView.getFile().getName() + "   ", parent);
            tab.setClosable(true);
            Platform.runLater(() -> {tabPane.getTabs().add(tab); tabPane.getSelectionModel().select(tab);});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open(){
        File file = FxmlService.selectPdfFile(getDefaultPdfFile(), stackPane.getScene().getWindow());
        if(file == null){
            return;
        }
        setDefaultPdfFile(file);
        load(file);
    }

    private void exit(){
        System.exit(0);
    }

    public String getDefaultPdfFile() {
        String imagefile = getPreferences().get("jfxPdf.file", System.getProperty("user.home"));
        if(!new File(imagefile).exists())
            return System.getProperty("user.home");
        return imagefile;
    }

    public void setDefaultPdfFile(File file) {
        if(file != null && file.exists()){
            getPreferences().put("jfxPdf.file", file.getAbsolutePath());
        }
    }

}
