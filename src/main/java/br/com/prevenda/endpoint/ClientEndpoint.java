package br.com.prevenda.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prevenda.error.ResourceNotFoundException;
import br.com.prevenda.model.Client;
import br.com.prevenda.repository.ClientRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class ClientEndpoint {

	private final ClientRepository clientDAO;

	@Autowired
	public ClientEndpoint(ClientRepository studentDAO) {
		this.clientDAO = studentDAO;
	}

	@GetMapping(path = "admin/client")
	@ApiOperation(value = "Return a list with all client", response = Client[].class)
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(clientDAO.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "protected/client/{id}")
	public ResponseEntity<?> getClientById(@PathVariable("id") Long id, Authentication authentication) {
		System.out.println(authentication);
		verifyIfClientExists(id);
		Client client = clientDAO.findOne(id);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}

	@GetMapping(path = "protected/client/findByName/{name}")
	public ResponseEntity<?> findStudentsByName(@PathVariable String name) {
		return new ResponseEntity<>(clientDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}

	@PostMapping(path = "admin/client")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Client student) {
		return new ResponseEntity<>(clientDAO.save(student), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "admin/client/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		verifyIfClientExists(id);
		clientDAO.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "admin/client")
	public ResponseEntity<?> update(@RequestBody Client client) {
		verifyIfClientExists(client.getId());
		clientDAO.save(client);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void verifyIfClientExists(Long id) {
		if (clientDAO.findOne(id) == null)
			throw new ResourceNotFoundException("Client not found for ID: " + id);
	}
}
