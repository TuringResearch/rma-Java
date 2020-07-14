package br.pro.turing.masiot.ui;

import br.pro.turing.masiot.applicationlayer.RMLBridge;
import br.pro.turing.masiot.ui.view.DevicePane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UiApplication extends Application {

    public static final RMLBridge RML_BRIDGE = new RMLBridge();

    @Override
    public void start(Stage primaryStage) {
        RML_BRIDGE.connect("127.0.0.1", 5500);
        DevicePane devicePane = new DevicePane();
        Scene scene = new Scene(devicePane);
        scene.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
