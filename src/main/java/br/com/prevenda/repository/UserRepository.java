package br.com.prevenda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.prevenda.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByUsername(String username);
}
