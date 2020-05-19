package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

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
        label.getStyleClass().add("h5-label");

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        headBox.getChildren().addAll(this.connectionStateBox, r1, label, r2);
        headBox.setAlignment(Pos.CENTER);
        return headBox;
    }

    private FlowPane buildResourcePane() {
        FlowPane resourcePane = new FlowPane();
        resourcePane.setOrientation(Orientation.HORIZONTAL);
        resourcePane.setAlignment(Pos.TOP_LEFT);
        resourcePane.setHgap(5);
        resourcePane.setVgap(5);
        resourcePane.setMaxWidth(325);

        for (Resource resource : this.device.getResourceList()) {
            ResourceBox resourceBox = new ResourceBox(this.device, resource, this.connectionStateBox);
            resourcePane.getChildren().add(resourceBox);
        }
        return resourcePane;
    }
}
