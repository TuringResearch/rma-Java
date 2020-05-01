package br.pro.turing.masiot.ui;

import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UiApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Device device = ServiceManager.getInstance().deviceService.findByDeviceName("casaFabian");
        testResourceBox(primaryStage, device);
    }

    private void testResourceBox(Stage primaryStage, Device device) {
        FlowPane flowPane = new FlowPane(10, 10);
        for (Resource resource : device.getResourceList()) {
            final ResourceBox resourceBox = new ResourceBox(resource);
            flowPane.getChildren().add(resourceBox);
            initReading(resource, resourceBox);
        }
        Scene scene = new Scene(flowPane);
        scene.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initReading(Resource resource, ResourceBox resourceBox) {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };
        new Thread(() -> {
            while (true) {
//                ServiceManager.getInstance().dataService.
            }
        }).start();
    }
}
