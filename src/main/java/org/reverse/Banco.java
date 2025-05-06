package org.reverse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Banco {
    public static void criarPessoa(String nomePessoa, int saldo) {
        Connection conn = Singleton.getConn();
        String sql = "INSERT INTO pessoa (nomePessoa, saldo) VALUES (?, ?)";

        try (PreparedStatement declaracao = conn.prepareStatement(sql)) {
            declaracao.setString(1, nomePessoa);
            declaracao.setInt(2, saldo);

            int inserido = declaracao.executeUpdate();
            if (inserido > 0) {
                System.out.println("Pessoa criada!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deposito(int idPessoa, int valor) {
        Connection conn = Singleton.getConn();
        String sql = "UPDATE pessoa SET saldo = saldo + ? WHERE idPessoa = ?";

        try (PreparedStatement declaracao = conn.prepareStatement(sql)) {
            declaracao.setInt(1, valor);
            declaracao.setInt(2, idPessoa);

            int inserido = declaracao.executeUpdate();
            if (inserido > 0) {
                System.out.println("Deposito realizado");
            } else {
                System.out.println("Pessoa não encontrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
