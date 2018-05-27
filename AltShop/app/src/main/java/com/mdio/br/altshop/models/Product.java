package com.mdio.br.altshop.models;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable {

    private String nome;
    private String descricao;
    private double preco;
    private String imagem;
    private Map<String, Object> loja;

    public Product() {

    }

    public Product(double price, String name, String description, String photo, Map<String, Object> loja) {
        this.preco = price;
        this.nome = name;
        this.descricao = description;
        this.imagem = photo;
        this.loja = loja;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public double getPreco() {
        return preco;
    }

    public Map<String, Object> getLoja() {
        return loja;
    }
}
