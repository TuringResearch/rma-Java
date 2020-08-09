package br.pro.turing.rma.ui.view;

import br.pro.turing.rma.core.model.Command;
import br.pro.turing.rma.core.model.Data;
import br.pro.turing.rma.core.model.Device;
import br.pro.turing.rma.core.model.Resource;
import br.pro.turing.rma.core.service.ServiceManager;
import br.pro.turing.rma.ui.UiApplication;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResourceBox extends VBox {

    private static final String UNAVAILABLE_VALUE = "-";

    private Label valueLabel;

    private Label unitLabel;

    private Device device;

    private Resource resource;

    private ArrayList<Button> buttonList;

    public ResourceBox(Device device, Resource resource, SimpleBooleanProperty connected) {
        super();
        this.device = device;
        this.resource = resource;
        this.buttonList = new ArrayList<>();

        this.getStyleClass().addAll("resource-box");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
        this.setPadding(new Insets(2));

        this.getChildren().add(buildHeadBox());
        this.getChildren().add(buildValueBox());
        this.getChildren().add(buildCommandButtonPane());

        buttonList.forEach(bt -> bt.setDisable(!connected.getValue()));
        valueLabel.setDisable(!connected.getValue());
        unitLabel.setDisable(!connected.getValue());
        connected.addListener((observable, oldValue, newValue) -> {
            buttonList.forEach(bt -> bt.setDisable(!newValue));
            valueLabel.setDisable(!newValue);
            unitLabel.setDisable(!newValue);
            if (!newValue) {
                Platform.runLater(() -> {
                    valueLabel.setText(UNAVAILABLE_VALUE);
                    unitLabel.setText("");
                });
            }
        });
    }

    private HBox buildHeadBox() {
        HBox headBox = new HBox();
        headBox.setAlignment(Pos.CENTER);

        Label resourceNameLabel = new Label(this.resource.getResourceName());
        resourceNameLabel.getStyleClass().add("h6-label");

        FontAwesomeIconView chartIcon = new FontAwesomeIconView(FontAwesomeIcon.LINE_CHART);
        chartIcon.setSize("20px");
        chartIcon.setFill(Color.color(0.03, 0.45, 0.34));
        chartIcon.setOnMouseClicked(event -> {
            ResourceChart resourceChart = new ResourceChart(this.device, this.resource);
            Scene scene = new Scene(resourceChart, 1080, 720);
            String styleSheetPath = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(styleSheetPath);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            resourceChart.startPlotting();
        });

        Region r1 = new Region();
        r1.setStyle("-fx-min-width: 20px;");
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);

        headBox.getChildren().addAll(r1, r2, resourceNameLabel, r3, chartIcon);
        return headBox;
    }

    private HBox buildValueBox() {
        HBox valueBox = new HBox();
        valueBox.setAlignment(Pos.CENTER);
        valueBox.setSpacing(2);

        this.valueLabel = new Label(UNAVAILABLE_VALUE);
        this.valueLabel.getStyleClass().add("h3-label");

        this.unitLabel = new Label();
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
        for (Command command : this.resource.getCommandList()) {
            Button commandButton = new Button(command.getCommand());
            commandButton.getStyleClass().add("command-button");
            commandButton.prefWidthProperty().bind(commandButtonPane.widthProperty().divide(2).subtract(2));
            commandButtonPane.getChildren().add(commandButton);
            commandButton.setOnAction(event -> {
                UiApplication.RML_BRIDGE.createAction(LocalDateTime.now(), this.device, this.resource, command);
            });
            buttonList.add(commandButton);
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

                    Platform.runLater(() -> {
                        this.valueLabel.setText(data.getValue());
                        this.unitLabel.setText(resource.getDataUnit());
                    });
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
