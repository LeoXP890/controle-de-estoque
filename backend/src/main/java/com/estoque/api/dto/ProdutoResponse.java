package com.estoque.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private Integer quantidade;
    private BigDecimal preco;
    private Long categoriaId;
    private String categoriaNome;
    private LocalDateTime dataCadastro;

    public ProdutoResponse(Long id, String nome, String descricao, Integer quantidade, BigDecimal preco,
                           Long categoriaId, String categoriaNome, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
