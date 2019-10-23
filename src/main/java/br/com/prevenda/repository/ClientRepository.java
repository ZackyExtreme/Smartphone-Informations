package br.com.prevenda.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.prevenda.model.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
	List<Client> findByNameIgnoreCaseContaining(String name);
}
