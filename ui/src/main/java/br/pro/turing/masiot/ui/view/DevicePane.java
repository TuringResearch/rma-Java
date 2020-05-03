package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

public class DevicePane extends FlowPane {

    public DevicePane() {
        super();

        this.getStyleClass().add("device-pane");
        this.setOrientation(Orientation.HORIZONTAL);
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));

        for (Device device : ServiceManager.getInstance().deviceService.findAll()) {
            DeviceBox deviceBox = new DeviceBox(device);
            this.getChildren().add(deviceBox);
        }
    }

}