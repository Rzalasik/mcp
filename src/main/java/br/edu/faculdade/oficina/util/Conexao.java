package br.edu.faculdade.oficina.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/oficina_mecanica";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
