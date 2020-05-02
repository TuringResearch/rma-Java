package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DeviceBox extends VBox {

    private ConnectionStateBox connectionStateBox;

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
        this.connectionStateBox = new ConnectionStateBox(this.device);
        Label label = new Label(this.device.getDeviceName());

        this.getChildren().addAll(this.connectionStateBox, label);
        return headBox;
    }

    private FlowPane buildResourcePane() {
        for (Resource resource : this.device.getResourceList()) {

        }
        return null;
    }
}
