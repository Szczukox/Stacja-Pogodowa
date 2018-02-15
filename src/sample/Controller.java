package sample;

import dataBaseConnection.JDBC;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pomiary.Pomiary;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable{

    @FXML
    Pane mainPane;

    @FXML
    Label deszcz;
    @FXML
    Label osw;
    @FXML
    Label wil;
    @FXML
    Label temp;
    @FXML
    Label cis;

    @FXML
    BubbleChart opadyBubbleChart;
    @FXML
    LineChart oswietlenieLineChart;
    @FXML
    LineChart wilgotnoscLineChart;
    @FXML
    LineChart temperaturaLineChart;
    @FXML
    LineChart cisnienieLineChart;

    private Connection conn;
    private Timeline timeline;

    private String[] lista_opady;
    private int[] lista_oswietlenie;
    private int[] lista_wilgotnosc;
    private float[] lista_temperatura;
    private int[] lista_cisnienie;

    public Controller() {
        timeline = new Timeline();
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.millis(1000),
                        (ActionEvent actionEvent) -> aktualizuj()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        JDBC jdbc = null;
        try {
            jdbc = new JDBC();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn = jdbc.getConn();

        Pomiary pomiary = new Pomiary(conn);
        this.lista_opady = pomiary.getPomiaryDeszczu();
        this.lista_cisnienie = pomiary.getPomiaryCisnienia();
        this.lista_oswietlenie = pomiary.getPomiaryOswietlenia();
        this.lista_temperatura = pomiary.getPomiaryTemperatury();
        this.lista_wilgotnosc = pomiary.getPomiaryWilgotnosci();

        deszcz.setText(String.valueOf(lista_opady[lista_opady.length - 1]));
        cis.setText(String.valueOf(lista_cisnienie[lista_cisnienie.length - 1]) + " hPa");
        osw.setText(String.valueOf(lista_oswietlenie[lista_oswietlenie.length - 1]) + " lx");
        temp.setText(String.valueOf(lista_temperatura[lista_temperatura.length - 1]) + " °C");
        wil.setText(String.valueOf(lista_wilgotnosc[lista_wilgotnosc.length - 1]) + " %");

        final NumberAxis opadyXAxis = new NumberAxis(0, 30, 5);
        final CategoryAxis opadyYAxis = new CategoryAxis();
        opadyXAxis.setLabel("Czas [s]");
        final LineChart<Number,String> opadyLineChart = new LineChart<Number,String>(opadyXAxis,opadyYAxis);
        opadyLineChart.setTitle("Opady");
        opadyLineChart.setCreateSymbols(false);
        XYChart.Series opadySeries = new XYChart.Series();
        for (int i = 0; i < lista_opady.length; i++) {
            opadySeries.getData().add(new XYChart.Data(i, String.valueOf(lista_opady[i])));
        }
        opadyLineChart.getData().add(opadySeries);
        mainPane.getChildren().add(opadyLineChart);
        opadyLineChart.setLegendVisible(false);
        opadyLineChart.setPrefSize(this.opadyBubbleChart.getPrefWidth(), this.opadyBubbleChart.getPrefHeight());
        opadyLineChart.setLayoutX(this.opadyBubbleChart.getLayoutX());
        opadyLineChart.setLayoutY(this.opadyBubbleChart.getLayoutY());
        this.opadyBubbleChart.setVisible(false);

        final NumberAxis cisnienieXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis cisnienieYAxis = new NumberAxis();
        cisnienieXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> cisnienieLineChart = new LineChart<Number,Number>(cisnienieXAxis,cisnienieYAxis);
        cisnienieLineChart.setTitle("Ciśnienie");
        cisnienieLineChart.setCreateSymbols(false);
        XYChart.Series cisnienieSeries = new XYChart.Series();
        for (int i = 0; i < lista_cisnienie.length; i++) {
            cisnienieSeries.getData().add(new XYChart.Data(i, lista_cisnienie[i]));
        }
        cisnienieLineChart.getData().add(cisnienieSeries);
        mainPane.getChildren().add(cisnienieLineChart);
        cisnienieLineChart.setLegendVisible(false);
        cisnienieLineChart.setPrefSize(this.cisnienieLineChart.getPrefWidth(), this.cisnienieLineChart.getPrefHeight());
        cisnienieLineChart.setLayoutX(this.cisnienieLineChart.getLayoutX());
        cisnienieLineChart.setLayoutY(this.cisnienieLineChart.getLayoutY());
        this.cisnienieLineChart.setVisible(false);

        final NumberAxis oswietlenieXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis oswietlenieYAxis = new NumberAxis();
        oswietlenieXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> oswietlenieLineChart = new LineChart<Number,Number>(oswietlenieXAxis,oswietlenieYAxis);
        oswietlenieLineChart.setTitle("Oświetlenie");
        oswietlenieLineChart.setCreateSymbols(false);
        XYChart.Series oswietlenieSeries = new XYChart.Series();
        for (int i = 0; i < lista_oswietlenie.length; i++) {
            oswietlenieSeries.getData().add(new XYChart.Data(i, lista_oswietlenie[i]));
        }
        oswietlenieLineChart.getData().add(oswietlenieSeries);
        mainPane.getChildren().add(oswietlenieLineChart);
        oswietlenieLineChart.setLegendVisible(false);
        oswietlenieLineChart.setPrefSize(this.oswietlenieLineChart.getPrefWidth(), this.oswietlenieLineChart.getPrefHeight());
        oswietlenieLineChart.setLayoutX(this.oswietlenieLineChart.getLayoutX());
        oswietlenieLineChart.setLayoutY(this.oswietlenieLineChart.getLayoutY());
        this.oswietlenieLineChart.setVisible(false);

        final NumberAxis temperaturaXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis temperaturaYAxis = new NumberAxis();
        temperaturaXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> temperaturaLineChart = new LineChart<Number,Number>(temperaturaXAxis,temperaturaYAxis);
        temperaturaLineChart.setTitle("Temperatura");
        temperaturaLineChart.setCreateSymbols(false);
        XYChart.Series temperaturaSeries = new XYChart.Series();
        for (int i = 0; i < lista_temperatura.length; i++) {
            temperaturaSeries.getData().add(new XYChart.Data(i, lista_temperatura[i]));
        }
        temperaturaLineChart.getData().add(temperaturaSeries);
        mainPane.getChildren().add(temperaturaLineChart);
        temperaturaLineChart.setLegendVisible(false);
        temperaturaLineChart.setPrefSize(this.temperaturaLineChart.getPrefWidth(), this.temperaturaLineChart.getPrefHeight());
        temperaturaLineChart.setLayoutX(this.temperaturaLineChart.getLayoutX());
        temperaturaLineChart.setLayoutY(this.temperaturaLineChart.getLayoutY());
        this.temperaturaLineChart.setVisible(false);

        final NumberAxis wilgotnoscXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis wilgotnoscYAxis = new NumberAxis();
        wilgotnoscXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> wilgotnoscLineChart = new LineChart<Number,Number>(wilgotnoscXAxis,wilgotnoscYAxis);
        wilgotnoscLineChart.setTitle("Wilgotność");
        wilgotnoscLineChart.setCreateSymbols(false);
        XYChart.Series wilgotnoscSeries = new XYChart.Series();
        for (int i = 0; i < lista_wilgotnosc.length; i++) {
            wilgotnoscSeries.getData().add(new XYChart.Data(i, lista_wilgotnosc[i]));
        }
        wilgotnoscLineChart.getData().add(wilgotnoscSeries);
        mainPane.getChildren().add(wilgotnoscLineChart);
        wilgotnoscLineChart.setLegendVisible(false);
        wilgotnoscLineChart.setPrefSize(this.wilgotnoscLineChart.getPrefWidth(), this.wilgotnoscLineChart.getPrefHeight());
        wilgotnoscLineChart.setLayoutX(this.wilgotnoscLineChart.getLayoutX());
        wilgotnoscLineChart.setLayoutY(this.wilgotnoscLineChart.getLayoutY());
        this.wilgotnoscLineChart.setVisible(false);
    }

    public void aktualizuj() {

    }

    public Connection getConn() {
        return this.conn;
    }

    public void setLista_opady(String[] lista_opady) {
        this.lista_opady = lista_opady;
    }

    public void setLista_oswietlenie(int[] lista_oswietlenie) {
        this.lista_oswietlenie = lista_oswietlenie;
    }

    public void setLista_wilgotnosc(int[] lista_wilgotnosc) {
        this.lista_wilgotnosc = lista_wilgotnosc;
    }

    public void setLista_temperatura(float[] lista_temperatura) {
        this.lista_temperatura = lista_temperatura;
    }

    public void setLista_cisnienie(int[] lista_cisnienie) {
        this.lista_cisnienie = lista_cisnienie;
    }
}
