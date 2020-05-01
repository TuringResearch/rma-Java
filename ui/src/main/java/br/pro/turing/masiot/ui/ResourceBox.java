package br.pro.turing.masiot.ui;

import br.pro.turing.masiot.core.model.Resource;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResourceBox extends VBox {
    private Label titleLabel;
    private Label valueLabel;
    private Label unitLabel;

    public ResourceBox(Resource resource) {
        getStyleClass().add("dashboard-box");
        titleLabel = new Label(resource.getName());
        titleLabel.setId("dashboard-title-label");
        getChildren().add(titleLabel);

        valueLabel = new Label();
        valueLabel.setId("dashboard-value-label");

        unitLabel = new Label();
        unitLabel.setId("dashboard-unit-label");

        HBox measureBox = new HBox(valueLabel, unitLabel);
        measureBox.setAlignment(Pos.BASELINE_CENTER);
        getChildren().add(measureBox);
    }

    public void setValue(String value, String unit) {
        valueLabel.setText(value);
        unitLabel.setText(unit);
    }
}