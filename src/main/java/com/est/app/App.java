package com.est.app;

import com.est.app.services.FxmlService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author selami
 */
public class App extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {
        FxmlService.load(primaryStage, "fxml/main.fxml", null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
