package br.com.prevenda.javaclient;

import br.com.prevenda.model.Client;

public class JavaSpringClientTest {
	public static void main(String[] args) {

		Client studentPost = new Client();
		studentPost.setName("John Wick 2");
		studentPost.setEmail("john@pencil.com");
		JavaClientDAO dao = new JavaClientDAO();
		dao.delete(29);

	}

}
