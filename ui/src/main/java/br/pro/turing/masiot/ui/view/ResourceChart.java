package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;
import javafx.util.converter.TimeStringConverter;

import javax.swing.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ResourceChart extends LineChart {

    private static final String TIME_FORMAT = "HH:mm:ss";

    private static final int ONE_SECOND = 1000;

    private static final int ONE_MINUTE = 60 * ONE_SECOND;

    private NumberAxis xAxis;

    private NumberAxis yAxis;

    private Timer timer;

    private Device device;

    private Resource resource;

    private XYChart.Series series;

    public ResourceChart(Device device, Resource resource) {
        super(new NumberAxis(), new NumberAxis());
        this.device = device;
        this.resource = resource;
        this.xAxis = (NumberAxis) getXAxis();
        this.yAxis = (NumberAxis) getYAxis();
        this.xAxis = createNumberAxis(this.xAxis, "Time", false, false);
        this.yAxis = createNumberAxis(this.yAxis, resource.getName() + "(" + resource.getDataUnit() + ")", false, true);

        this.xAxis.setTickLabelFormatter(new TimeConverter());
        this.xAxis.setTickLabelRotation(-30);
        this.setTitle(resource.getName() + "(" + resource.getDataUnit() + ") Chart");
        this.setAnimated(false);
        this.setCreateSymbols(false);
        this.setCacheShape(true);
        this.setAlternativeRowFillVisible(true);
        this.setVerticalGridLinesVisible(false);

        this.series = new XYChart.Series(resource.getResourceName(), FXCollections.observableArrayList());
        this.getData().add(series);
    }

    public NumberAxis createNumberAxis(NumberAxis axis, String name, boolean animated, boolean autoRanging) {
        axis.setLabel(name);
        axis.setAnimated(animated);
        axis.setAutoRanging(autoRanging);
        return axis;
    }

    /**
     * Este método é responsável por inicializar o temporizador que ativará eventos periódicos
     * para atualizar o
     * gráfico com novos pontos e remover pontos antigos.
     */
    public void startPlotting() {
        this.timer = new Timer(ONE_SECOND, e -> {
            long initialTime = e.getWhen() - (20 * ONE_MINUTE);
            long finalTime = e.getWhen();
            Platform.runLater(() -> {
                this.xAxis.setLowerBound(initialTime);
                this.xAxis.setUpperBound(finalTime);
                this.xAxis.setTickUnit((finalTime - initialTime) / 10);
            });

            LocalDateTime initialLocalDateTime = Instant.ofEpochMilli(initialTime).atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            List<br.pro.turing.masiot.core.model.Data> dataList = ServiceManager.getInstance().dataService
                    .findByResourceAndGte(ResourceChart.this.device, ResourceChart.this.resource, initialLocalDateTime);
            List<XYChart.Data> chartDataList = FXCollections.observableArrayList();
            for (br.pro.turing.masiot.core.model.Data data : dataList) {
                double time = data.getInstant().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                chartDataList.add(new XYChart.Data<>(time, Double.parseDouble(data.getValue())));
            }
            Platform.runLater(() -> {
                this.series.getData().clear();
                this.series.getData().addAll(chartDataList);
            });
        });
        timer.start();
    }

    /**
     * Conversor da String que representa a unidade de tempo no eixo X do gráfico.
     */
    private static class TimeConverter extends StringConverter<Number> {

        /**
         * Conversor da String de tempo para o formato {@link #TIME_FORMAT}.
         */
        TimeStringConverter timeStringConverter = new TimeStringConverter(TIME_FORMAT);

        @Override
        public String toString(Number object) {
            return timeStringConverter.toString(new Date(object.longValue()));
        }

        @Override
        public Number fromString(String string) {
            return 1;
        }
    }
}
