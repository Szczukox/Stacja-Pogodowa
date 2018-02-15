package sample;

import dataBaseConnection.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import pomiary.Pomiary;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable{

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

    private int[] lista_opady;
    private int[] lista_oswietlenie;
    private int[] lista_wilgotnosc;
    private float[] lista_temperatura;
    private int[] lista_cisnienie;

    int deszczyk=0;
    int oswietlenie=10;
    int wilgotnosc=40;
    int temperatura=25;
    int cisnienie=1000;

    public Controller() {
    }

    public void czy_pada(){
        if(deszczyk==1){
            deszcz.setText("Nie pada");
        }
        else
            deszcz.setText("Pada");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //czy_pada();
        //osw.setText(oswietlenie+"lx");
        //wil.setText(wilgotnosc+"%");
        //temp.setText(temperatura+"°C");
        //cis.setText(cisnienie+"hPa");
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

        deszcz.setText(String.valueOf(lista_opady[0]));
        cis.setText(String.valueOf(lista_cisnienie[0]));
        osw.setText(String.valueOf(lista_cisnienie[0]));
        temp.setText(String.valueOf(lista_temperatura[0]));
        wil.setText(String.valueOf(lista_wilgotnosc[0]));
    }

    public Connection getConn() {
        return this.conn;
    }

    public void setLista_opady(int[] lista_opady) {
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
