package com.estoque.api.service;

import com.estoque.api.dto.ProdutoRequest;
import com.estoque.api.dto.ProdutoResponse;
import com.estoque.api.model.Categoria;
import com.estoque.api.model.Produto;
import com.estoque.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public List<ProdutoResponse> listar() {
        return produtoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public ProdutoResponse criar(ProdutoRequest request) {
        Categoria categoria = categoriaService.buscarEntidade(request.getCategoriaId());

        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setQuantidade(request.getQuantidade());
        produto.setPreco(request.getPreco());
        produto.setCategoria(categoria);

        return toResponse(produtoRepository.save(produto));
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscarEntidade(id);
        Categoria categoria = categoriaService.buscarEntidade(request.getCategoriaId());

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setQuantidade(request.getQuantidade());
        produto.setPreco(request.getPreco());
        produto.setCategoria(categoria);

        return toResponse(produtoRepository.save(produto));
    }

    public void excluir(Long id) {
        Produto produto = buscarEntidade(id);
        produtoRepository.delete(produto);
    }

    private Produto buscarEntidade(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    private ProdutoResponse toResponse(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getQuantidade(),
                p.getPreco(),
                p.getCategoria().getId(),
                p.getCategoria().getNome(),
                p.getDataCadastro()
        );
    }
}
