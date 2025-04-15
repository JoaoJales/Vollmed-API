package med.voll.api.domain.consultation.validations.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.ConsultationRepository;
import med.voll.api.domain.consultation.DataCancelConsultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidationCancelamentoComAntecedencia implements ValidatorCancelConsultation{
    @Autowired
    private ConsultationRepository repository;

    @Override
    public void validar(DataCancelConsultation data) {
        var consultation = repository.findById(data.idConsultation()).get();

        if (LocalDateTime.now().isAfter(consultation.getData().minusHours(24))){
            throw new ValidacaoException("Cancelamento inválido - É necessário cancelar com 24h de antecedência");
        }


        //Outra forma de verificar:

//        var consulta = repository.getReferenceById(data.idConsultation());
//        var agora = LocalDateTime.now();
//        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();
//
//        if (diferencaEmHoras < 24) {
//            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
//        }
    }
}
