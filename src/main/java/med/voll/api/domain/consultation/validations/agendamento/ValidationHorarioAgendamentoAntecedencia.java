package med.voll.api.domain.consultation.validations.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.DataPostConsultation;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidationHorarioAgendamentoAntecedencia implements ValidatorConsultationService {

    public void validar (DataPostConsultation data){
        var dateConsultation = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, dateConsultation).toMinutes();


        if (differenceInMinutes < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
