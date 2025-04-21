package med.voll.api.domain.doctor;

import med.voll.api.domain.address.DataAddress;
import med.voll.api.domain.consultation.Consultation;
import med.voll.api.domain.consultation.Reason;
import med.voll.api.domain.patient.DataRegisterPatient;
import med.voll.api.domain.patient.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest //Usada para testar algo da camada de persistência (Interfaces Repositorys)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Usada para indicar que não usaremos um banco de dados em memória para testes e sim o próprio bd da aplicação (MySQL).
@ActiveProfiles("test")
class DoctorRepositoryTest {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
    void getRandomDoctorCenario1() {
        //Geralmente os métod0s de teste são dividos em 3 blocos


        //Given ou Arrange (Cadastro de informações)
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Joãozinho da Silva", "joao.silva@voll.med", "123456", Specialty.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Carol Rocha Lima", "carol.lima@gmail.com", "345.987.012-078");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10, null);

        //When ou act (Executa o ação que será testada)
        var medicoLivre = doctorRepository.getRandomDoctor(Specialty.CARDIOLOGIA, proximaSegundaAs10);

        //Then ou assert (Verifica se a ação executada está com o resultado esperado)
        assertThat(medicoLivre).isNull();

    }

    @Test
    @DisplayName("Deveria devolver o médico quando ele estiver disponível na data")//Cenário em que o médico está cadastrado e livre
    void getRandomDoctorCenario2() {
        //Given ou Arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Joãozinho da Silva", "joao.silva@voll.med", "123456", Specialty.CARDIOLOGIA);

        //When ou act
        var medicoLivre = doctorRepository.getRandomDoctor(Specialty.CARDIOLOGIA, proximaSegundaAs10);

        //Then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver o médico quando ele possuir uma consula que foi cancelada na data")
    void getRandomDoctorCenario3() {
        //Given ou Arrange (Cadastro de informações)
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Joãozinho da Silva", "joao.silva@voll.med", "123456", Specialty.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Carol Rocha Lima", "carol.lima@gmail.com", "345.987.012-078");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10, "outros");

        //When ou act
        var medicoLivre = doctorRepository.getRandomDoctor(Specialty.CARDIOLOGIA, proximaSegundaAs10);

        //Then ou assert
        assertThat(medicoLivre).isEqualTo(medico);

    }

    private void cadastrarConsulta(Doctor medico, Patient paciente, LocalDateTime date, String reasonCancel){
        if (reasonCancel != null){
            testEntityManager.persist(new Consultation(null, medico, paciente, date, Reason.fromString(reasonCancel)));
        }else {
            testEntityManager.persist(new Consultation(null, medico, paciente, date, null));
        }

    }

    private Doctor cadastrarMedico(String nome, String email, String crm, Specialty specialty){
        var medico = new Doctor(dadosMedico(nome, email, crm, specialty));
        testEntityManager.persist(medico);
        return medico;
    }

    private Patient cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Patient(dadosPaciente(nome, email, cpf));
        testEntityManager.persist(paciente);
        return paciente;
    }

    private DataRegisterDoctor dadosMedico(String nome, String email, String crm, Specialty specialty){
        return new DataRegisterDoctor(
                nome,
                email,
                "61999999999",
                crm,
                specialty,
                dadosEndereco()
        );
    }

    private DataRegisterPatient dadosPaciente (String nome, String email, String cpf){
        return new DataRegisterPatient(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DataAddress dadosEndereco(){
        return new DataAddress(
                "rua xpto",
                "Asa Sul",
                "43123980",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}