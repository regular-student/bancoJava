package org.reverse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Connection conn = Singleton.getConn();
        Scanner leitor = new Scanner(System.in);

        Statement declaracao = null;
        ResultSet resultado = null;

        try {
            declaracao = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS pessoa (" +
                    "idPessoa INTEGER PRIMARY KEY, " +
                    "nomePessoa TEXT, " +
                    "saldo INTEGER)";
            declaracao.executeUpdate(createTableSQL);

            char c;
            do {
                System.out.println("Escolha qual operação fazer no banco");
                System.out.println("1 - Depositar");
                System.out.println("2 - Sacar");
                System.out.println("3 - Mostrar pessoas");
                System.out.println("4 - Criar pessoa");
                System.out.println("5 - Deletar conta\n");
                System.out.println("Ou digite N pra sair");
                c = leitor.next().charAt(0);

                int id, valor;


                switch (c) {
                    case '1' -> {
                        System.out.println("Escreva o ID");
                        id = leitor.nextInt();
                        System.out.println("Escolha o valor do depósito");
                        valor = leitor.nextInt();
                        Banco.deposito(id, valor);
                    }
                    case '2' -> {
                        System.out.println("Escreva o ID");
                        id = leitor.nextInt();
                        System.out.println("Escolha o valor do saque");
                        valor = leitor.nextInt();
                        Banco.saque(id, valor);
                    }
                    case '3' -> {
                        Utils.limparTela();
                        Banco.mostrarPessoas();
                        Utils.pausar(3000);
                    }
                    case '4' -> {
                        System.out.println("Escreva o nome");
                        String nome = leitor.next();
                        System.out.println("Digite o saldo da conta");
                        valor = leitor.nextInt();
                        Banco.criarPessoa(nome, valor);
                        Utils.pausar(3000);
                    }
                    case '5' -> {
                        Utils.limparTela();
                        Banco.mostrarPessoas();
                        System.out.println("Escreva o ID que deseja deletar");
                        id = leitor.nextInt();
                        Banco.deletarPessoa(id);
                        Utils.pausar(3000);
                    }
                    default -> System.out.println("[Error] - Comando errado");
                }
            } while(Character.toLowerCase(c) != 'n');

//            Banco.criarPessoa( "João", 1000);
//            Banco.criarPessoa( "Maria", 2000);
//            Banco.criarPessoa( "Alan", 7000);

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