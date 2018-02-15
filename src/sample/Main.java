package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {


    private Connection conn;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        conn = controller.getConn();
        primaryStage.setTitle("Pomiary ze stacji pogodowej");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setMinWidth(1050);
        primaryStage.setMinHeight(850);
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Odłączono od bazy danych");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
