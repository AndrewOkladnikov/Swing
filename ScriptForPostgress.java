package ru.learning.swing.myLibrary;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptForPostgress {
    public static void main(String[] args) {
        String sql = null;
        try {
            sql = new String(Files.readAllBytes(Paths.get("src/main/java/ru/learning/swing/myLibrary/script.sql")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Connection db = DataSource.getConnection()) {
            Statement stmt = db.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}