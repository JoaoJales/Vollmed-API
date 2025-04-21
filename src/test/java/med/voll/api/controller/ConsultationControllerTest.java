package med.voll.api.controller;

import med.voll.api.domain.consultation.ConsultationService;
import med.voll.api.domain.consultation.DataDetailingConsultation;
import med.voll.api.domain.consultation.DataPostConsultation;
import med.voll.api.domain.doctor.Specialty;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest//Indica que será usado o contexto completo do Spring para realizar a simulação;
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultationControllerTest {
    @Autowired
    private MockMvc mvc; //Fazer um Mock (Simula requisições http)
    // -> Usada para realizar testes únitarios (Testes que isolam uma parte do código para verificar seu comportamento de forma isolada)
    //Não utiliza uma chamada real de um servidor e sim um Mock

    @Autowired
    private JacksonTester<DataPostConsultation> dataPostConsultationJSON; //Simula o Json de Entrada
    // Classes do Spring que possui generics (<>) que recebe o tipo do objeto que ele irá trabalhar
    @Autowired
    private JacksonTester<DataDetailingConsultation> dataDetailingConsultationJSON; //Simula o Json de saída

    @MockitoBean
    private ConsultationService consultationService;

    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estiverem inválidas")
    @WithMockUser //Fazer um Mock de usuário -> (Simula um usuário logado para não existir interferência do Spring Security)
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200 quando informações estiverem válidas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Specialty.CARDIOLOGIA;
        var dataDetailing = new DataDetailingConsultation(null, 2L, 5L, data);


        //Mokito: biblioteca de Mock que o Spring utiliza
        when(consultationService.agendar(any())).thenReturn(dataDetailing);

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON) //Indica o envio de infomações por JSON
                                .content(dataPostConsultationJSON.write(
                                        new DataPostConsultation(2L, 5L, data, especialidade)
                                ).getJson())
                ) //É possível digitar manualmente o JSON porém não recomendado //É utlizado um class utilitaria para gerar um JSON
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dataDetailingConsultationJSON.write(
                dataDetailing
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    void cancelar_cenario1() {
    }
}