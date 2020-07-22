package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DeviceBox extends VBox {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");

    private Label lastUpdateDateLabel;

    private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);

    private Device device;

    public DeviceBox(Device device) {
        this.device = device;
        this.getStyleClass().addAll("device-box");
        this.getChildren().add(buildHeadBox());
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(buildResourcePane());
    }

    private HBox buildHeadBox() {
        HBox headBox = new HBox();

        this.lastUpdateDateLabel = new Label();
        this.lastUpdateDateLabel.setText(this.device.getLocalLastUpdate() != null ?
                                         this.device.getLocalLastUpdate().format(DATE_TIME_FORMATTER) : "");

        Label label = new Label(this.device.getDeviceName());
        label.getStyleClass().add("h5-label");

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        headBox.getChildren().addAll(label, r1, this.lastUpdateDateLabel);
        headBox.setAlignment(Pos.CENTER);

        initListeners();

        return headBox;
    }

    private FlowPane buildResourcePane() {
        FlowPane resourcePane = new FlowPane();
        resourcePane.setOrientation(Orientation.HORIZONTAL);
        resourcePane.setAlignment(Pos.TOP_LEFT);
        resourcePane.setHgap(5);
        resourcePane.setVgap(5);

        for (Resource resource : this.device.getResourceList()) {
            ResourceBox resourceBox = new ResourceBox(this.device, resource, this.connected);
            resourcePane.getChildren().add(resourceBox);
        }
        return resourcePane;
    }

    private void initListeners() {
        new Thread(() -> {
            while (true) {
                long t1 = System.currentTimeMillis();
                this.device = ServiceManager.getInstance().deviceService.findById(this.device.getDeviceName());
                Platform.runLater(() -> this.lastUpdateDateLabel.setText(this.device.getLocalLastUpdate() != null ?
                                                                         this.device.getLocalLastUpdate()
                                                                                 .format(DATE_TIME_FORMATTER) : ""));
                if (this.device.getLocalLastUpdate().until(LocalDateTime.now(), ChronoUnit.MILLIS) > (device.getCycleDelayInMillis() * 1.2)) {
                    this.connected.set(false);
                } else {
                    this.connected.set(true);
                }

                long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < 5000 ? 5000 - duration : 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
