package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResourceBox extends VBox {
    private static final String UNAVAILABLE_VALUE = "-";

    private Label valueLabel;
    private Label unitLabel;
    private ObservableList<Button> commandButtons;

    private Resource resource;

    public ResourceBox(Resource resource) {
        super();
        this.resource = resource;

        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
        this.setPadding(new Insets(2));

        this.getChildren().add(buildHeadBox());
        this.getChildren().add(buildValueBox());
    }

    private HBox buildHeadBox() {
        HBox headBox = new HBox();
        Label resourceNameLabel = new Label(this.resource.getResourceName());
        headBox.getChildren().addAll(resourceNameLabel);
        return headBox;
    }

    private HBox buildValueBox() {
        HBox valueBox = new HBox();
        valueBox.setSpacing(2);
        this.valueLabel = new Label(UNAVAILABLE_VALUE);
        this.unitLabel = new Label(resource.getDataUnit());
        valueBox.getChildren().addAll(this.valueLabel, this.unitLabel);
        return valueBox;
    }

    public void startResource() {
        new Thread(() -> {
            while (true) {
                long t1 = System.currentTimeMillis();

                ServiceManager.getInstance().dataService.findLastByResource();

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
