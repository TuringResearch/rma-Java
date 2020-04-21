package br.pro.turing.masiot.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardBox extends VBox {
    private Label titleLabel;
    private Label valueLabel;
    private Label unitLabel;

    public DashboardBox(String title) {
        getStyleClass().add("dashboard-box");
        titleLabel = new Label(title);
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