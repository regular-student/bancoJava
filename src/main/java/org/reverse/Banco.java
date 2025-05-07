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
        String selectSql = "SELECT idPessoa, nomePessoa, saldo FROM pessoa WHERE idPessoa = ?";


        Pessoa pessoa;

        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setInt(1, idPessoa);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                // Criando o objeto Pessoa com os dados retornados
                int id = rs.getInt("idPessoa");
                String nome = rs.getString("nomePessoa");
                int saldo = rs.getInt("saldo");

                pessoa = new Pessoa(id, nome, saldo);
            } else {
                System.out.println("Pessoa não encontrada");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (pessoa.getSaldo() < valor) {
            System.out.println("Saldo insuficiente");
            return;
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
        String sql = "DELETE FROM pessoa WHERE idPessoa = ?";

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
