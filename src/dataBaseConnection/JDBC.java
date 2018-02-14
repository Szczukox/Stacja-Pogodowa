package dataBaseConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {

    private Connection conn;

    public JDBC() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/stacja_pogodowa", "root", "");
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, "Nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }
    }

    public Connection getConn() {
        return this.conn;
    }

}
