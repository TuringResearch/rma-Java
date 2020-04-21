package br.pro.turing.masiot.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.TimeStringConverter;

import java.util.*;

/**
 * Interface gráfica para teste de plotagem de um gráfico genérico considerando a velocidade e
 * quantidade de series.
 * Este teste pode ser útil para verificar a performance de um gráfico de linha ao trabalhar em
 * condições que
 * combinam a velocidade de plotagem com a quantidade de series. Este teste também mostra a forma
 * correta de se
 * trabalhar com um gráfico que realiza plotagem ao vivo. Foi verificado que a forma correta de
 * executar uma plotagem
 * sem erros é adicionando o bloco de código responsável pela adição e remoção de pontos dentro
 * da Thread do JavaFx.
 */
public class PlotChartPerformanceTest8 extends Application {

    /**
     * Principal.
     *
     * @param args Argumento correto: <Delay de plotagem> <Quantidade de series>
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Argumento correto: <Tempo em minutos> <Quantidade de series> "
                    + "<frequência de plotagem>");
            System.exit(0);
        }
        System.out.print("CONFIGURAÇÃO: ");
        timeInSeconds = Integer.parseInt(args[0]) * 60;
        System.out.print(args[0] + " minutos, ");
        seriesQuantity = Integer.parseInt(args[1]);
        System.out.print(args[1] + " séries, ");
        plotFrequency = Double.parseDouble(args[2]);

        System.out.println(args[2] + " Hz.");
        System.out.println("Versão Java: " + System.getProperty("java.version"));
        System.out.println("---------------------------------");
        Application.launch(args);
    }

    /**
     * Largura da janela.
     */
    private static final int WIDTH = 1200;

    /**
     * Altura da janela.
     */
    private static final int HEIGHT = 700;

    /**
     * Nome do eixo X.
     */
    private static final String X_AXIS_NAME = "Tempo";

    /**
     * Nome do eixo Y.
     */
    private static final String Y_AXIS_NAME = "Medida";

    /**
     * Nome do gráfico.
     */
    private static final String CHART_NAME = "Teste de performance de plotagem de medidas no "
            + "gráfico em Java 8";

    /**
     * Prefixo usado no nome das series.
     */
    private static final String SERIES_PREFIX_NAME = "Linha ";

    /**
     * Fomato de data.
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Um segundo.
     */
    private static final int ONE_SECOND = 1000;

    /**
     * Um minuto
     */
    private static final int ONE_MINUTE = 60 * ONE_SECOND;

    /**
     * Valor do tickUnit do gráfico.
     */
    private static final int TICK_UNIT = ONE_MINUTE / 6;

    /**
     * Gráfico de linha.
     */
    private static LineChart chart;

    /**
     * Eixo X.
     */
    private static NumberAxis xAxis;

    /**
     * Eixo Y.
     */
    private static NumberAxis yAxis;

    private static int timeInSeconds;

    /**
     * Delay que define a velocidade de plotagem do gráfico. Por exemplo, um delay de 100ms
     * permite a inserção de
     * 1000 / 100 = 10 dados em cada série por segundo.
     */
    private static double plotFrequency;

    /**
     * Quantidade de séries que serão adicionadas no gráfico.
     */
    private static int seriesQuantity;

    /**
     * Cria um eixo do gráfico de acordo com as configurações definidas no parâmetro.
     *
     * @param name        Nome do eixo.
     * @param animated    Caso seja true, significa que uma mudança no eixo será realizada com
     *                    animação.
     * @param autoRanging Caso seja true, significa que o eixo terá ajuste automático de intervalos.
     * @return Eixo do gráfico.
     */
    public static NumberAxis createNumberAxis(String name, boolean animated, boolean autoRanging) {
        NumberAxis axis = new NumberAxis();
        axis.setLabel(name);
        axis.setAnimated(animated);
        axis.setAutoRanging(autoRanging);
        return axis;
    }

    /**
     * Cria um gráfico de linha de medida por tempo, onde o tickUnit do eixo X vale
     * {@link #TICK_UNIT} e os labels
     * desse eixo são exibidos com rotação de -30º. O gráfico não possui animação e ativa a
     * funcionalidade que
     * permite sugerir ao sistema que o corpo do gráfico é estavel o suficiente para se
     * beneficiar do armazenamento
     * em cache.
     *
     * @param xAxis Eixo X.
     * @param yAxis Eixo Y.
     * @param title Título do gráfico.
     * @return Grafico de linha.
     */
    public static LineChart createChart(NumberAxis xAxis, NumberAxis yAxis, String title) {
        xAxis.setTickLabelFormatter(new PlotChartPerformanceTest8.TimeConverter());
        xAxis.setTickLabelRotation(-30);
        xAxis.setTickUnit(1000000000);
        LineChart lineChart = new LineChart(xAxis, yAxis);
        if (title != null && !title.isEmpty()) {
            lineChart.setTitle(title);
        }
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart.setCacheShape(true);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setVerticalGridLinesVisible(false);

        return lineChart;
    }

    /**
     * Este método é responsável por inicializar o temporizador que ativará eventos periódicos
     * para atualizar o
     * gráfico com novos pontos e remover pontos antigos.
     */
    private static void plotChart() {

        Task<List<XYChart.Series>> task = new Task<List<XYChart.Series>>() {
            long lowerBound;

            long upperBound;

            /**
             * Gera um Lista contendo valores do tipo {@code Double}, estes valores correspondem
             * a dados que simulam o
             * comportamento de um sensor.
             *
             * @param noiseSeedValue Seed de geração do ruído.
             * @param noiseValue     Intensidade do ruído.
             * @param sampleMean     Amplitude média.
             * @param sampleDelta    Delta de amostras. Podendo simular multiplos sensores.
             * @param amplitude      Base da amostra. Podendo simular sensores reais
             * @param sineFrequency  Frequência da onda senoidal.
             * @return Array com dados simulados.
             */
            public ObservableList<double[]> buildSamplesList(long noiseSeedValue, double noiseValue, double sampleMean,
                                                             double sampleDelta, double amplitude,
                                                             double sineFrequency, long currentTime,
                                                             double plotMillisPeriod) {
                ObservableList<double[]> samples = FXCollections.observableArrayList();
                double sample = sampleMean;
                Random bRandom = new Random(noiseSeedValue);
                for (int i = 0; i < timeInSeconds; i++) {
                    for (int j = 0; j < plotFrequency; j++) {
                        double a = (new Random(i).nextDouble()) * noiseValue;
                        if (bRandom.nextBoolean()) {
                            sample = sample + a;
                        } else {
                            sample = sample - a;
                        }
                        int degree = (i % 360);
                        double[] value =
                                new double[]{currentTime,
                                        Math.cos(Math.toRadians(degree * sineFrequency)) * sample + (
                                                amplitude + sampleDelta)};
                        samples.add(value);

                        currentTime += plotMillisPeriod;
                    }
                }
                return samples;
            }

            Map<String, ObservableList<double[]>> createData() {
                System.out.print("Iniciando a criação de pontos na memória... ");
                long t1 = System.currentTimeMillis();

                upperBound = System.currentTimeMillis();
                lowerBound = upperBound - (timeInSeconds * ONE_SECOND);
                lowerBound = ((lowerBound / ONE_SECOND) - 1) * ONE_SECOND;
                double plotMillisPeriod = ONE_SECOND / plotFrequency;
                Map<String, ObservableList<double[]>> coordinateMap = new TreeMap<>();
                for (int x = 0; x < seriesQuantity; x++) {
                    int finalX = x;
                    coordinateMap.computeIfAbsent(SERIES_PREFIX_NAME + x,
                            k -> buildSamplesList(0, 0.05, 5, finalX, 10, 1, lowerBound,
                                    plotMillisPeriod));
                }

                long t2 = System.currentTimeMillis();

                int points = 0;
                for (String s : coordinateMap.keySet()) {
                    points += coordinateMap.get(s).size();
                }
                System.out.println(points + " pontos criados em " + ((t2 - t1) / ONE_SECOND) + " "
                        + "segundos.");

                return coordinateMap;
            }

            List<XYChart.Series> createSeries(Map<String, ObservableList<double[]>> coordinateMap) {
                System.out.print("Criando series em... ");
                long t1 = System.currentTimeMillis();
                List<XYChart.Series> seriesList = new ArrayList<>();
                for (String name : coordinateMap.keySet()) {
                    XYChart.Series series = new XYChart.Series();
                    series.setName(name);

                    ObservableList<XYChart.Data> dataObservableList = FXCollections
                            .observableArrayList();
                    for (double[] value : coordinateMap.get(name)) {
                        dataObservableList.add(new XYChart.Data<>(value[0], value[1]));
                    }
                    series.setData(dataObservableList);
                    seriesList.add(series);
                }
                long t2 = System.currentTimeMillis();
                System.out.println(((t2 - t1) / 1000) + " segundos.");
                return seriesList;
            }

            @Override
            protected List<XYChart.Series> call() {
                Map<String, ObservableList<double[]>> coordinateMap = createData();
                return createSeries(coordinateMap);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.print("Plotando...");
                long t1 = System.currentTimeMillis();
                xAxis.setLowerBound(lowerBound);
                xAxis.setUpperBound(upperBound);

                chart.getData().clear();
                chart.getData().setAll(getValue());

                long t2 = System.currentTimeMillis();
                System.out.println(" concluído em " + ((t2 - t1) / ONE_SECOND) + " segundos.");
            }
        };

        new Thread(task).start();
    }

    /**
     * Inicializa a interface gráfica.
     *
     * @param primaryStage Palco primário onde o painel principal será colocado.
     */
    @Override
    public void start(Stage primaryStage) {
        // Cenário
        xAxis = createNumberAxis(X_AXIS_NAME, false, false);
        yAxis = createNumberAxis(Y_AXIS_NAME, false, true);
        chart = createChart(xAxis, yAxis, CHART_NAME);
        BorderPane pane = new BorderPane(chart);
        pane.setPadding(new Insets(10));

        double sceneWidth = 1080;
        double sceneHeight = 720;
        Scene scene = new Scene(pane, sceneWidth, sceneHeight);
        String styleSheetPath = getClass().getResource(
                "/style.css").toExternalForm();
        scene.getStylesheets().add(styleSheetPath);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Geração de dados.
        plotChart();
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
