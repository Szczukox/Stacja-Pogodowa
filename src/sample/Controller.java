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
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pomiary.Pomiary;

import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
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
    Label czasLabel;

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

    private final int MAX_DATA_POINTS = 30;
    private int sequence = 4;

    private Connection conn;
    private Timeline timeline;

    private String[] lista_opady;
    private int[] lista_oswietlenie;
    private int[] lista_wilgotnosc;
    private float[] lista_temperatura;
    private int[] lista_cisnienie;

    private XYChart.Series opadySeries;
    private XYChart.Series oswietlenieSeries;
    private XYChart.Series wilgotnoscSeries;
    private XYChart.Series temperaturaSeries;
    private XYChart.Series cisnienieSeries;

    private NumberAxis opadyXAxis;
    private NumberAxis oswietlenieXAxis;
    private NumberAxis wilgotnoscXAxis;
    private NumberAxis temperaturaXAxis;
    private NumberAxis cisnienieXAxis;

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



        opadyXAxis = new NumberAxis(0, 30, 5);
        final CategoryAxis opadyYAxis = new CategoryAxis();
        opadyXAxis.setLabel("Czas [s]");
        final ScatterChart<Number,String> opadyLineChart = new ScatterChart<Number,String>(opadyXAxis,opadyYAxis);
        opadyLineChart.setTitle("Opady");
        opadyLineChart.setAnimated(false);
        opadySeries = new XYChart.Series();
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



        cisnienieXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis cisnienieYAxis = new NumberAxis();
        cisnienieXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> cisnienieLineChart = new LineChart<Number, Number>(cisnienieXAxis,cisnienieYAxis);
        cisnienieLineChart.setTitle("Ciśnienie");
        cisnienieLineChart.setCreateSymbols(false);
        cisnienieLineChart.setAnimated(false);
        cisnienieSeries = new XYChart.Series();
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



        oswietlenieXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis oswietlenieYAxis = new NumberAxis();
        oswietlenieXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> oswietlenieLineChart = new LineChart<Number,Number>(oswietlenieXAxis,oswietlenieYAxis);
        oswietlenieLineChart.setTitle("Oświetlenie");
        oswietlenieLineChart.setCreateSymbols(false);
        oswietlenieLineChart.setAnimated(false);
        oswietlenieSeries = new XYChart.Series();
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



        temperaturaXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis temperaturaYAxis = new NumberAxis();
        temperaturaXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> temperaturaLineChart = new LineChart<Number,Number>(temperaturaXAxis,temperaturaYAxis);
        temperaturaLineChart.setTitle("Temperatura");
        temperaturaLineChart.setCreateSymbols(false);
        temperaturaLineChart.setAnimated(false);
        temperaturaSeries = new XYChart.Series();
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



        wilgotnoscXAxis = new NumberAxis(0, 30, 5);
        final NumberAxis wilgotnoscYAxis = new NumberAxis();
        wilgotnoscXAxis.setLabel("Czas [s]");
        final LineChart<Number,Number> wilgotnoscLineChart = new LineChart<Number,Number>(wilgotnoscXAxis,wilgotnoscYAxis);
        wilgotnoscLineChart.setTitle("Wilgotność");
        wilgotnoscLineChart.setCreateSymbols(false);
        wilgotnoscLineChart.setAnimated(false);
        wilgotnoscSeries = new XYChart.Series();
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

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        czasLabel.setText(dateFormat.format(date));
    }

    private void aktualizuj() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        czasLabel.setText(dateFormat.format(date));

        String opady = null;
        int czyPada = getNextValue("opady");
        if (czyPada == 1) {
            opady = "Nie pada";
        } else if (czyPada == 0) {
            opady = "Pada";
        }
        deszcz.setText(opady);
        int cisnienie = getNextValue("cisnienie");
        cis.setText(String.valueOf(cisnienie) + " hPa");
        int oswietlenie = getNextValue("oswietlenie");
        osw.setText(String.valueOf(oswietlenie) + " lx");
        int temperatura = getNextValue("temperatura");
        temp.setText(String.valueOf(temperatura) + " °C");
        int wilgotnosc = getNextValue("wilgotnosc");
        wil.setText(String.valueOf(wilgotnosc) + " %");
        opadySeries.getData().add(new XYChart.Data<Number, String>(++sequence, opady));
        cisnienieSeries.getData().add(new XYChart.Data<Number, Number>(sequence, cisnienie));
        oswietlenieSeries.getData().add(new XYChart.Data<Number, Number>(sequence, oswietlenie));
        temperaturaSeries.getData().add(new XYChart.Data<Number, Number>(sequence, temperatura));
        wilgotnoscSeries.getData().add(new XYChart.Data<Number, Number>(sequence, wilgotnosc));

        if (sequence > MAX_DATA_POINTS - 1) {
            opadySeries.getData().remove(0);
            opadyXAxis.setLowerBound(cisnienieXAxis.getLowerBound() + 1);
            opadyXAxis.setUpperBound(cisnienieXAxis.getUpperBound() + 1);

            cisnienieSeries.getData().remove(0);
            cisnienieXAxis.setLowerBound(cisnienieXAxis.getLowerBound() + 1);
            cisnienieXAxis.setUpperBound(cisnienieXAxis.getUpperBound() + 1);

            oswietlenieSeries.getData().remove(0);
            oswietlenieXAxis.setLowerBound(oswietlenieXAxis.getLowerBound() + 1);
            oswietlenieXAxis.setUpperBound(oswietlenieXAxis.getUpperBound() + 1);

            temperaturaSeries.getData().remove(0);
            temperaturaXAxis.setLowerBound(temperaturaXAxis.getLowerBound() + 1);
            temperaturaXAxis.setUpperBound(temperaturaXAxis.getUpperBound() + 1);

            wilgotnoscSeries.getData().remove(0);
            wilgotnoscXAxis.setLowerBound(wilgotnoscXAxis.getLowerBound() + 1);
            wilgotnoscXAxis.setUpperBound(wilgotnoscXAxis.getUpperBound() + 1);
        }
    }

    private int getNextValue(String pomiar){
        Random rand = new Random();
        if (pomiar == "opady") {
            return rand.nextInt(2);
        } else if (pomiar == "cisnienie") {
            return rand.nextInt(200) + 900;
        } else if (pomiar == "oswietlenie") {
            return rand.nextInt(500) + 500;
        } else if (pomiar == "temperatura") {
            return rand.nextInt(35) - 10;
        } else if (pomiar == "wilgotnosc") {
            return rand.nextInt(90) + 5;
        }
        return 0;
    }

    public Connection getConn() {
        return this.conn;
    }

    public Timeline getTimeline() {
        return this.timeline;
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
