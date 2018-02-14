package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

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

    int[] lista_opady;
    int[] lista_oswietlenie;
    int[] lista_wilgotnosc;
    int[] lista_temperatura;
    int[] lista_cisnienie;




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
        czy_pada();
        osw.setText(oswietlenie+"lx");
        wil.setText(wilgotnosc+"%");
        temp.setText(temperatura+"°C");
        cis.setText(cisnienie+"hPa");
    }
}
