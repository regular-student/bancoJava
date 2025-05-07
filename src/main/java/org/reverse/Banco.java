package org.reverse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

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

    public static void saque(int idPessoa, int valor) {
        if (valor < 0) return;

        Connection conn = Singleton.getConn();
        String sql = "UPDATE pessoa SET saldo = saldo - ? WHERE idPessoa = ?";

        try {
            conn.setAutoCommit(false);

            // Verifica se há saldo suficiente
            String verificaSaldo = "SELECT saldo FROM pessoa WHERE idPessoa = ?";
            try (PreparedStatement declaracaoSaldo = conn.prepareStatement(verificaSaldo)) {
                declaracaoSaldo.setInt(1, idPessoa);
                ResultSet rs = declaracaoSaldo.executeQuery();
                if (!rs.next()) {
                    System.out.println("Pessoa não encontrada.");
                    return;
                }

                int saldoAtual = rs.getInt("saldo");
                if (saldoAtual < valor) {
                    System.out.println("Saldo insuficiente.");
                    return;
                }
            }

            try (PreparedStatement declaracao = conn.prepareStatement(sql)) {
                declaracao.setInt(1, valor);
                declaracao.setInt(2, idPessoa);

                int inserido = declaracao.executeUpdate();
                if (inserido > 0) {
                    System.out.println("Saque realizado");
                } else {
                    System.out.println("Pessoa não encontrada");
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarPessoas() {
        Connection conn = Singleton.getConn();
        String sql = "SELECT * FROM pessoa";

        try (PreparedStatement declaracao = conn.prepareStatement(sql)) {
            ResultSet rs = declaracao.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idPessoa");
                int saldo = rs.getInt("saldo");
                String nome = rs.getString("nomePessoa");
                System.out.println("ID - " + id + "\nNome: " + nome + " Saldo: " + saldo + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarPessoa(int idPessoa) {
        Connection conn = Singleton.getConn();
        String sql = "ALTER TABLE pessoa DROP COLUMN ?";

        try (PreparedStatement declaracao = conn.prepareStatement(sql)) {
            declaracao.setInt(1, idPessoa);

            int inserido = declaracao.executeUpdate();
            if (inserido > 0) {
                System.out.println("Conta deletada");
            } else {
                System.out.println("Pessoa não encontrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
