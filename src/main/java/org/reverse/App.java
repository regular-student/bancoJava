package org.reverse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        Connection conn = Singleton.getConn();

        Statement declaracao = null;
        ResultSet resultado = null;

        try {
            declaracao = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS pessoa (" +
                    "idPessoa INTEGER PRIMARY KEY, " +
                    "nomePessoa TEXT, " +
                    "saldo INTEGER)";
            declaracao.executeUpdate(createTableSQL);

            Banco.criarPessoa( "João", 1000);
            Banco.criarPessoa( "Maria", 2000);
            Banco.criarPessoa( "Alan", 7000);

            String sql = "SELECT * FROM pessoa";
            resultado = declaracao.executeQuery(sql);

            while (resultado.next()) {
                // Exemplo: exibir os valores das colunas
                int id = resultado.getInt("idPessoa");
                int saldo = resultado.getInt("saldo");
                String nome = resultado.getString("nomePessoa");  // Corrigido para "nomePessoa"
                System.out.println("ID: " + id + ", Saldo: " + saldo + ", Nome: " + nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) resultado.close();
                if (declaracao != null) declaracao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}