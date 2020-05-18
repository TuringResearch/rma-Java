package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Command;
import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import br.pro.turing.masiot.ui.UiApplication;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.List;

public class ResourceBox extends VBox {

    private static final String UNAVAILABLE_VALUE = "-";

    private Label valueLabel;

    private Label unitLabel;

    private Device device;

    private Resource resource;

    public ResourceBox(Device device, Resource resource) {
        super();
        this.device = device;
        this.resource = resource;

        this.getStyleClass().addAll("resource-box");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
        this.setPadding(new Insets(2));

        this.getChildren().add(buildHeadBox());
        this.getChildren().add(buildValueBox());
        this.getChildren().add(buildCommandButtonPane());
    }

    private HBox buildHeadBox() {
        HBox headBox = new HBox();
        headBox.setAlignment(Pos.CENTER);

        Label resourceNameLabel = new Label(this.resource.getResourceName());
        resourceNameLabel.getStyleClass().add("h6-label");

        headBox.getChildren().addAll(resourceNameLabel);
        return headBox;
    }

    private HBox buildValueBox() {
        HBox valueBox = new HBox();
        valueBox.setAlignment(Pos.CENTER);
        valueBox.setSpacing(2);

        this.valueLabel = new Label(UNAVAILABLE_VALUE);
        this.valueLabel.getStyleClass().add("h3-label");

        this.unitLabel = new Label(resource.getDataUnit());
        this.unitLabel.getStyleClass().add("h5-label");
        valueBox.getChildren().addAll(this.valueLabel, this.unitLabel);
        startResource();
        return valueBox;
    }

    private FlowPane buildCommandButtonPane() {
        FlowPane commandButtonPane = new FlowPane();
        commandButtonPane.setOrientation(Orientation.HORIZONTAL);
        commandButtonPane.setAlignment(Pos.TOP_LEFT);
        commandButtonPane.setHgap(2);
        commandButtonPane.setVgap(2);
        commandButtonPane.setMaxWidth(150);
        for (Command command : this.resource.getCommandList()) {
            Button commandButton = new Button(command.getCommand());
            commandButton.getStyleClass().add("command-button");
            commandButtonPane.getChildren().add(commandButton);
            commandButton.setOnAction(event -> {
                UiApplication.RML_BRIDGE.createAction(LocalDateTime.now(), this.device, this.resource, command);
            });
        }
        return commandButtonPane;
    }

    public void startResource() {
        new Thread(() -> {
            while (true) {
                long t1 = System.currentTimeMillis();

                final List<Data> dataList = ServiceManager.getInstance().dataService.findByResourceAndGte(this.device,
                        this.resource, LocalDateTime.now().plusSeconds(-5));

                if (!dataList.isEmpty()) {
                    dataList.sort((o1, o2) -> o1.getInstant().compareTo(o2.getInstant()));
                    Data data = dataList.get(dataList.size() - 1);

                    Platform.runLater(() -> this.valueLabel.setText(data.getValue()));
                }
                long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < 1000 ? 1000 - duration : 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
