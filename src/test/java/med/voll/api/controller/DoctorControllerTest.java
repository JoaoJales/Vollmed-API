package med.voll.api.controller;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.DataAddress;
import med.voll.api.domain.doctor.*;
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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DataRegisterDoctor> dataRegisterDoctorJson;

    @Autowired
    private JacksonTester<DataDetailingDoctor> dataDetailingDoctorJson;

    @MockitoBean
    private DoctorRepository doctorRepository;


    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estao invalidas")
    @WithMockUser
    void register_cenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 201 quando informações estiverem corretas")
    @WithMockUser
    void register_cenario2() throws Exception {
        var dadosRegistroMedico = dadosRegistroMedico();
        var dadosDetalhamentoMedico = dadosDetalhamentoMedico(dadosRegistroMedico);

        when(doctorRepository.save(any())).thenReturn(new Doctor(dadosRegistroMedico));

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataRegisterDoctorJson.write(dadosRegistroMedico).getJson()))
                .andReturn().getResponse();


        var jsonEsperado = dataDetailingDoctorJson.write(dadosDetalhamentoMedico).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DataDetailingDoctor dadosDetalhamentoMedico(DataRegisterDoctor dataRegisterDoctor){
        return new DataDetailingDoctor(
                null,
                dataRegisterDoctor.nome(),
                dataRegisterDoctor.email(),
                dataRegisterDoctor.telefone(),
                dataRegisterDoctor.crm(),
                dataRegisterDoctor.especialidade(),
                new Address(dataRegisterDoctor.endereco())
        );
    }

    private DataRegisterDoctor dadosRegistroMedico(){
            return new DataRegisterDoctor(
                    "Fulano",
                    "fulano@voll.med",
                    "61999999999",
                    "123456",
                    Specialty.CARDIOLOGIA,
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