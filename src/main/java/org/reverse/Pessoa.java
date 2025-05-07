package org.reverse;

public class Pessoa {
    private int idPessoa;
    private String nome;
    private int Saldo;

    Pessoa (int id, String nome, int saldo) {
        this.idPessoa = id;
        this.nome = nome;
        this.Saldo = saldo;
    }

    public String getNome() {
        return nome;
    }


    public int getIdPessoa() {
        return idPessoa;
    }

    public int getSaldo() {
        return Saldo;
    }
}
