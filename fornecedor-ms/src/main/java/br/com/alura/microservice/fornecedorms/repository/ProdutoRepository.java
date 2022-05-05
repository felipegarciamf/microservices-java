package br.com.alura.microservice.fornecedorms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.alura.microservice.fornecedorms.model.Produto;


public interface ProdutoRepository extends CrudRepository<Produto, Long>{

	List<Produto> findByEstado(String estado);
	
	List<Produto> findByIdIn(List<Long> ids);
}
