package pomiary;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pomiary {

    private final Connection conn;
    private ResultSet dane;
    private PreparedStatement zapytanie;
    private int liczbaWierszy;

    public Pomiary(Connection conn) {
        this.conn = conn;
        zapytanie = null;
        try {
            if (dane != null) {
                if (!(dane.isClosed())) {
                    dane.close();
                    zapytanie.close();
                }
            }
            String sql = "SELECT CZAS, TEMPERATURA, CISNIENIE, WILGOTNOSC, OSWIETLENIE, OPADY FROM POMIARY ORDER BY CZAS";
            zapytanie = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            dane = zapytanie.executeQuery();
            dane.beforeFirst();
            liczbaWierszy = 0;
            while (this.dane.next()) {
                this.liczbaWierszy++;
            }
            dane.beforeFirst();
        } catch (SQLException e) {
            Logger.getLogger(Pomiary.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int[] getPomiaryDeszczu() {
        int[] pomiaryDeszczu = new int[liczbaWierszy];
        int i = 0;
        try {
            dane.beforeFirst();
            while (dane.next()) {
                pomiaryDeszczu[i] = dane.getInt("OPADY");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pomiaryDeszczu;
    }

    public int[] getPomiaryOswietlenia() {
        int[] pomiaryOswietlenia = new int[liczbaWierszy];
        int i = 0;
        try {
            dane.beforeFirst();
            while (dane.next()) {
                pomiaryOswietlenia[i] = dane.getInt("OSWIETLENIE");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pomiaryOswietlenia;
    }

    public int[] getPomiaryWilgotnosci() {
        int[] pomiaryWilgotnosci = new int[liczbaWierszy];
        int i = 0;
        try {
            dane.beforeFirst();
            while (dane.next()) {
                pomiaryWilgotnosci[i] = dane.getInt("WILGOTNOSC");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pomiaryWilgotnosci;
    }

    public float[] getPomiaryTemperatury() {
        float[] pomiaryTemperatury = new float[liczbaWierszy];
        int i = 0;
        try {
            dane.beforeFirst();
            while (dane.next()) {
                pomiaryTemperatury[i] = dane.getFloat("TEMPERATURA");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pomiaryTemperatury;
    }

    public int[] getPomiaryCisnienia() {
        int[] pomiaryCisnienia = new int[liczbaWierszy];
        int i = 0;
        try {
            dane.beforeFirst();
            while (dane.next()) {
                pomiaryCisnienia[i] = dane.getInt("CISNIENIE");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pomiaryCisnienia;
    }

    public void close() {
        try {
            if (dane != null) {
                if (!(dane.isClosed())) {
                    dane.close();
                    zapytanie.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Pomiary.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
