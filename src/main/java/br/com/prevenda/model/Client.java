package br.com.prevenda.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Client extends AbstractEntity {
	@NotEmpty(message = "O campo nome do estudante é obrigatório")
	private String name;
	@NotEmpty
	@Email(message = "Digite um email válido")
	private String email;

	public Client() {
	}

	public Client(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Client(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Client{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
