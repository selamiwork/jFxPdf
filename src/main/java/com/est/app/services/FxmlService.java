package com.est.app.services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static java.util.ResourceBundle.getBundle;

public class FxmlService {
    private static Preferences preferences = Preferences.userRoot();
    public static Preferences getPreferences() { try { preferences.sync(); } catch (Exception e) { } return preferences; }
    public static String ApplicationName = "jFxPdf";

    public static FXMLLoader getLoader(String fxmlPath){
        //URL resource = getClass().getClassLoader().getResource(fxmlPath);
        URL resource = FxmlService.class.getClassLoader().getResource(fxmlPath);
        String bundleName = fxmlPath.replace(".fxml", "");
        if(bundleName.startsWith("/"))
            bundleName = bundleName.replaceFirst("/", "");
        ResourceBundle bundle = null;
        FXMLLoader fxmlLoader;
        try {
            bundle = getBundle(bundleName);
        } catch (MissingResourceException ex) {
        }
        if(bundle == null)
        {
            fxmlLoader = new FXMLLoader(resource);
        }else
        {
            fxmlLoader = new FXMLLoader(resource, bundle);
        }
        return  fxmlLoader;
    }

    public static Parent load(Stage stage, String fxmlPath, String title)
    {
        return load(stage, fxmlPath, title, null);
    }

    public static Parent load(Stage stage, String fxmlPath, String title, Object parameter){
        FXMLLoader fxmlLoader = getLoader(fxmlPath);
        //fxmlLoader.setControllerFactory(context::getBean);
        try {
            Parent root = fxmlLoader.load();
            if(parameter != null && fxmlLoader.getController() instanceof ParametrizedController)
            {
                ((ParametrizedController) fxmlLoader.getController()).setParameter(parameter);
            }

            //String css = FxmlService.class.getResource("/fxml/masterSkin.css").toExternalForm();
            //String css = FxmlService.class.getResource("/fxml/bootstrap.css").toExternalForm();
            //String css = FxmlService.class.getResource("/fxml/win7.css").toExternalForm();
            //String css = FxmlService.class.getResource("/fxml/buttonPlayImage.css").toExternalForm();
            String css = FxmlService.class.getResource("/fxml/app.css").toExternalForm();

            if (stage != null) {
                if (title != null) {
                    stage.setTitle(title);
                }
                Scene scene = new Scene(root);
                scene.getStylesheets().clear();
                scene.getStylesheets().add(css);
                //Image image = new Image("/images/icon_logo.png");
                //stage.getIcons().add(image);
                stage.setScene(scene);
                stage.show();
            }else
            {
                root.getStylesheets().clear();
                root.getStylesheets().add(css);
            }
            return root;
        } catch (Exception e){
            //logger.error("Error FXML", e);
            return null;
        }
    }



    public static Parent load(String fxmlPath){
        return load(null, fxmlPath, null, null);
    }

    public static Parent loadWithParameter(String fxmlPath, Object parameter){
        return load(null, fxmlPath, null, parameter);
    }

    public static Parent load(String fxmlPath, Object controller)
    {
        FXMLLoader fxmlLoader = getLoader(fxmlPath);
        if(controller != null)
            fxmlLoader.setController(controller);
        InputStream inputStream = null;
        try {
            inputStream = FxmlService.class.getResourceAsStream(fxmlPath);
            Parent root = fxmlLoader.load(inputStream);
            //new JMetro(JMetro.Style.LIGHT).applyTheme(root);
            //AquaFx.style();

            return root;
        } catch (Exception e) {
            //logger.error("cannot " + fxmlPath, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {}
            }
        }
        return null;
    }

    public static void alert(String header, String content, Window owner) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);
        if (owner == null) {
            alert.initModality(Modality.APPLICATION_MODAL);
        } else {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }

        alert.showAndWait();
    }

    public static boolean alertConfirmation(String header, String content, Window owner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);

        return getAlertResult(alert, owner, false);
    }

    public static boolean alertConfirmation(String header, Node parent, Window owner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(parent);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        return getAlertResult(alert, owner, false);
    }

    public static boolean alertConfirmation(String header, Node parent, double width, double height, Window owner, boolean resizable, boolean block) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(parent);
        //alert.setGraphic(parent);
        alert.setResizable(resizable);
        //alert.getDialogPane().setPrefSize(width, height);
        if(width > 0) alert.getDialogPane().setPrefWidth(width);
        if(height > 0) alert.getDialogPane().setPrefHeight(height);

        return getAlertResult(alert, owner, block);
    }

    public static boolean alertConfirmation(String title, String header, Node parent, double width, double height, Window owner, boolean block, boolean resizable) {
        Alert alert = new Alert(header == null ? Alert.AlertType.NONE : Alert.AlertType.CONFIRMATION);
        if(header == null){
            alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK, ButtonType.CANCEL);
            parent.setTranslateX(-10);
        }else{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(parent);
        //alert.setGraphic(parent);
        alert.setResizable(resizable);
        //alert.getDialogPane().setPrefSize(width, height);
        if(resizable){
            alert.getDialogPane().setMinWidth(width);
            alert.getDialogPane().setMinHeight(height);
        }else
        {
            alert.getDialogPane().setPrefWidth(width);
            alert.getDialogPane().setPrefHeight(height);
            alert.getDialogPane().setMaxWidth(width);
            alert.getDialogPane().setMaxHeight(height);
        }

        return getAlertResult(alert, owner, block);
    }

    public static void alertNode(String header,Parent node,  Window owner){
        Scene scene = new Scene(node);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle(header);
        if (owner == null) {
            stage.initModality(Modality.APPLICATION_MODAL);
        } else {
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
        }
        stage.showAndWait();
    }

    public static void alertNode(String header,Parent node, double width, double height, Window owner, boolean wait, boolean resizable){
        if(node == null) return;
        if(node.getScene() != null && node.getScene().getRoot() != null)
        {
            if(node.getScene().getWindow().isShowing())
                return;
            else
                node.getScene().setRoot(new Region());
        }
        Scene scene = new Scene(node);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.UTILITY);
        if(resizable){
            stage.setMinWidth(width);
            stage.setMinHeight(height);
        }else
        {
            stage.setMaxWidth(width);
            stage.setMaxHeight(height);
        }
        stage.setResizable(resizable);
        stage.setScene(scene);
        stage.setTitle(header);
        if(wait)
        {
            if (owner == null) {
                stage.initModality(Modality.APPLICATION_MODAL);
            } else {
                stage.initOwner(owner);
                stage.initModality(Modality.WINDOW_MODAL);
            }
            stage.showAndWait();
        }else
        {
            if (owner != null)
                stage.initOwner(owner);
            stage.initModality(Modality.NONE);
            stage.show();
        }
    }

    public static String alertInput(String header, String defaultInput,  Window owner) {
        TextInputDialog alert = new TextInputDialog(defaultInput == null ? "" : defaultInput);
        alert.setTitle("Input");
        alert.setHeaderText(header);
        // create a tile pane
        TilePane r = new TilePane();

        //alert.setGraphic(parent);
        if (owner == null) {
            alert.initModality(Modality.APPLICATION_MODAL);
        } else {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        Optional<String> result = alert.showAndWait();
        return result.orElse(null);
    }

    public static Object alertInput(String header, ObservableList<?> list, Window owner) {
        ChoiceDialog<?> alert = new ChoiceDialog<>();
        ComboBox comboBox = (ComboBox) alert.getDialogPane().lookup(".combo-box");
        comboBox.setItems(list);
        comboBox.requestFocus();

        alert.setTitle("Input");
        alert.setHeaderText(header);
        // create a tile pane
        TilePane r = new TilePane();

        //alert.setGraphic(parent);
        if (owner == null) {
            alert.initModality(Modality.APPLICATION_MODAL);
        } else {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        Optional<?> optional = alert.showAndWait();
        return optional.orElse(null);
    }


    public static boolean alertInputNoWait(String title, String header, Node parent, double width, double height, Window owner, boolean block, boolean resizable) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(header == null){
            alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK, ButtonType.CANCEL);
            parent.setTranslateX(-10);
        }

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(parent);
        //alert.setGraphic(parent);
        alert.setResizable(resizable);
        //alert.getDialogPane().setPrefSize(width, height);
        if(resizable){
            alert.getDialogPane().setMinWidth(width);
            alert.getDialogPane().setMinHeight(height);
        }else
        {
            alert.getDialogPane().setPrefWidth(width);
            alert.getDialogPane().setPrefHeight(height);
            alert.getDialogPane().setMaxWidth(width);
            alert.getDialogPane().setMaxHeight(height);
        }
        //if(width > 0) alert.getDialogPane().setPrefWidth(width);
        //if(height > 0) alert.getDialogPane().setPrefHeight(height);
        return getAlertResult(alert, owner, block);
    }

    private static boolean getAlertResult(Alert alert, Window owner, boolean block){
        ChangeListener<Number> changeListenerX = null, changeListenerY = null;

        if (owner == null) {
            alert.initModality(Modality.APPLICATION_MODAL);
        } else {
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
        }
        double x = getWindowX(alert.getTitle());
        double y = getWindowY(alert.getTitle());
        boolean inBounds = true;
        if(x != 0 && y != 0){
            ObservableList<Screen> screenSizes = Screen.getScreens();
            if(screenSizes.size() == 1){
                if(x >= screenSizes.get(0).getBounds().getMaxX() || y >= screenSizes.get(0).getBounds().getMaxY()){
                    inBounds = false;
                }
            }
            if(inBounds){
                alert.setX(x);
                alert.setY(y);
            }
        }
        changeListenerX = (observable, oldValue, newValue) -> setWindowX(alert.getTitle(), newValue.doubleValue());
        changeListenerY = (observable, oldValue, newValue) -> setWindowY(alert.getTitle(), newValue.doubleValue());
        alert.xProperty().addListener(changeListenerX);
        alert.yProperty().addListener(changeListenerY);
        if(!block){
            alert.initModality(Modality.NONE);
        }
        Optional<ButtonType> result = alert.showAndWait();

        alert.xProperty().removeListener(changeListenerX);
        alert.yProperty().removeListener(changeListenerY);

        return result.isPresent() ? result.get() == ButtonType.OK : false;
    }

    public static void alertWarning(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);

        //alert.initModality(Modality.APPLICATION_MODAL);
        alert.initModality(Modality.WINDOW_MODAL);

        alert.showAndWait();
    }

    public static void alertWarning(String header, String content, BooleanProperty close) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);

        //alert.initModality(Modality.APPLICATION_MODAL);
        alert.initModality(Modality.WINDOW_MODAL);
        ChangeListener<Boolean> changeListener = (observable, oldValue, newValue) -> alert.close();
        close.addListener(changeListener);
        alert.showAndWait();
        close.removeListener(changeListener);
    }

    public static void alertWarningWithoutWaiting(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initModality(Modality.NONE);
        alert.show();
    }

    public static void alertWarning(String header, String content, Runnable runnable) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);

        //alert.initModality(Modality.APPLICATION_MODAL);
        alert.initModality(Modality.WINDOW_MODAL);

        alert.showAndWait();
        runnable.run();
    }

    public static void alertInfo(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void alertInfoNoWait(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.initModality(Modality.NONE);

        alert.show();
    }

    public static void alertInfoNoWait(String title, String header, Node parent, double width, double height, Window owner, boolean block, boolean resizable) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(header == null){
            alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK, ButtonType.CANCEL);
            parent.setTranslateX(-10);
        }

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(parent);
        //alert.setGraphic(parent);
        alert.setResizable(resizable);
        //alert.getDialogPane().setPrefSize(width, height);
        if(resizable){
            alert.getDialogPane().setMinWidth(width);
            alert.getDialogPane().setMinHeight(height);
        }else
        {
            alert.getDialogPane().setPrefWidth(width);
            alert.getDialogPane().setPrefHeight(height);
            alert.getDialogPane().setMaxWidth(width);
            alert.getDialogPane().setMaxHeight(height);
        }
        //if(width > 0) alert.getDialogPane().setPrefWidth(width);
        //if(height > 0) alert.getDialogPane().setPrefHeight(height);

        getAlertResult(alert, owner, block);

    }

    public static File selectPdfFile(String defaultFile, Window owner){
        return selectFile(defaultFile, owner, "PDF File", "PDF files (*.pdf)", "*.pdf");
    }

    public static File selectFile(String defaultFile, Window owner, final String title, final String description, final String... extensions){
        File fileSelected = null;
        try {
            HBox hBox = new HBox();
            TextField textField = new TextField(defaultFile);
            Button button = new Button("Select File");
            textField.setMinWidth(500);
            button.setMinWidth(100);
            FlowPane flowPane = new FlowPane(textField, new Text("   "), button);
            flowPane.setMinWidth(650);
            flowPane.setMinHeight(100);
            button.setOnMouseClicked(event -> {
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extensions);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(title);
                fileChooser.getExtensionFilters().add(extFilter);
                File file = new File(defaultFile);
                fileChooser.setInitialDirectory(file.exists() ? file.getParentFile() : new File(System.getProperty("user.home")));
                fileChooser.setInitialFileName(file.getName());
                file = fileChooser.showOpenDialog(flowPane.getScene().getWindow());
                //File file = fileChooser.showSaveDialog(imageviewFilePath.getScene().getWindow());
                if(file != null){
                    textField.setText(file.getAbsolutePath());
                }
            });
            boolean result = alertConfirmation("File", flowPane, 650, 125, owner, false, true);
            if(result){
                fileSelected = new File(textField.getText());
            }
        } catch (Exception e) {}
        return fileSelected;
    }

    public synchronized static double getWindowX(String window) {
        return getPreferences()
                .getDouble(ApplicationName + ".system.window." + window + ".x", 0);
    }

    public synchronized static void setWindowX(String window, double x) {
        getPreferences().putDouble(ApplicationName + ".system.window." + window + ".x", x);
    }

    public synchronized static double getWindowY(String window) {
        return getPreferences()
                .getDouble(ApplicationName + ".system.window." + window + ".y", 0);
    }

    public synchronized static void setWindowY(String window, double y) {
        getPreferences().putDouble(ApplicationName + ".system.window." + window + ".y", y);
    }

    public static interface ParametrizedController{
        public void setParameter(Object object);
    }
}
