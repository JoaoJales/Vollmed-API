package med.voll.api.controller;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.DataAddress;
import med.voll.api.domain.patient.DataDetailingPatient;
import med.voll.api.domain.patient.DataRegisterPatient;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PatientControllerTest {
    @MockitoBean
    private PatientRepository patientRepository;

    @Autowired
    private JacksonTester<DataRegisterPatient> dataRegisterPatientJson;

    @Autowired
    private JacksonTester<DataDetailingPatient> dataDetailingPatientJson;

    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("Deveria devolver erro http 400 quando informações estiverem invalidas")
    @WithMockUser
    void registerPatient_cenario1() throws Exception {
        var response = mvc
                .perform(post("/pacientes")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 201 quando informações estiverem corretas")
    @WithMockUser
    void registerPatient_cenario2() throws Exception {
        var dadosRegistroPaciente = dadosRegistroPaciente();
        var dadosDetalhamentoPaciente = dadosDetalhamentoPaciente(dadosRegistroPaciente);

        when(patientRepository.save(any())).thenReturn(new Patient(dadosRegistroPaciente));

        var response = mvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON)
                .content(dataRegisterPatientJson.write(dadosRegistroPaciente).getJson()))
                .andReturn().getResponse();

        var jsonEsperado = dataDetailingPatientJson.write(dadosDetalhamentoPaciente).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }







    private DataDetailingPatient dadosDetalhamentoPaciente(DataRegisterPatient dataRegisterPatient){
        return new DataDetailingPatient(
                null,
                dataRegisterPatient.nome(),
                dataRegisterPatient.email(),
                dataRegisterPatient.telefone(),
                dataRegisterPatient.cpf(),
                new Address(dataRegisterPatient.endereco())
        );
    }


    private DataRegisterPatient dadosRegistroPaciente(){
        return new DataRegisterPatient(
                "Fulano",
                "fulano@voll.med",
                "61999999999",
                "000.000.000-00",
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