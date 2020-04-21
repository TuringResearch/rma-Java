package br.pro.turing.masiot.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UiApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final DashboardBox dashboardBox = new DashboardBox("Temperatura");
        dashboardBox.setValue("25", "°C");
        Scene scene = new Scene(dashboardBox);
        scene.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
