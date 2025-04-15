package med.voll.api.domain.doctor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Usada para testar algo da camada de persistência (Interfaces Repositorys)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Usada para indicar que não usaremos um banco de dados em memória para testes e sim o próprio bd da aplicação (MySQL).
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Test
    void getRandomDoctor() {

    }
}