package br.com.devdojo;

import br.com.prevenda.model.Client;
import br.com.prevenda.repository.ClientRepository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Suane for DevDojo on 7/10/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    @Autowired
    private ClientRepository studentRepository;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Client student = new Client("William", "william@devdojo.com.br");
        this.studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("William");
        assertThat(student.getEmail()).isEqualTo("william@devdojo.com.br");
    }

    @Test
    public void deleteShouldRemoveData() {
        Client student = new Client("William", "william@devdojo.com.br");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        assertThat(studentRepository.findOne(student.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData() {
        Client student = new Client("William", "william@devdojo.com.br");
        this.studentRepository.save(student);
        student.setName("William222");
        student.setEmail("william222@devdojo.com.br");
        this.studentRepository.save(student);
        student = this.studentRepository.findOne(student.getId());
        assertThat(student.getName()).isEqualTo("William222");
        assertThat(student.getEmail()).isEqualTo("william222@devdojo.com.br");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Client student = new Client("William", "william@devdojo.com.br");
        Client student2 = new Client("william", "william222@devdojo.com.br");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Client> studentList = studentRepository.findByNameIgnoreCaseContaining("william");
        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo nome do estudante é obrigatório");
        this.studentRepository.save(new Client());
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        Client student = new Client();
        student.setName("William");
        this.studentRepository.save(student);
    }
    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Digite um email válido");
        Client student = new Client();
        student.setName("William");
        student.setEmail("William");
        this.studentRepository.save(student);
    }

}
