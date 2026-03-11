package com.estoque.api.service;

import com.estoque.api.dto.CategoriaRequest;
import com.estoque.api.dto.CategoriaResponse;
import com.estoque.api.model.Categoria;
import com.estoque.api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoriaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public CategoriaResponse criar(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        return toResponse(categoriaRepository.save(categoria));
    }

    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Categoria categoria = buscarEntidade(id);
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        return toResponse(categoriaRepository.save(categoria));
    }

    public void excluir(Long id) {
        Categoria categoria = buscarEntidade(id);
        categoriaRepository.delete(categoria);
    }

    public Categoria buscarEntidade(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + id));
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }
}
