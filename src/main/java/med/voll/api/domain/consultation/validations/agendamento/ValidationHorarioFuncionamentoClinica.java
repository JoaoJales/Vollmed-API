package med.voll.api.domain.consultation.validations.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.DataPostConsultation;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidationHorarioFuncionamentoClinica implements ValidatorConsultationService {

    public void validar (DataPostConsultation data){
        var dateConsultation = data.date();
        var sunday = dateConsultation.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeClinicOpens = dateConsultation.getHour() < 7;
        var afterClinicClose = dateConsultation.getHour() > 18;

        if (sunday || beforeClinicOpens || afterClinicClose){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
