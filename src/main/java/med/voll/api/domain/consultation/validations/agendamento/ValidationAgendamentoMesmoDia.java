package med.voll.api.domain.consultation.validations.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.ConsultationRepository;
import med.voll.api.domain.consultation.DataPostConsultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationAgendamentoMesmoDia implements ValidatorConsultationService {
    @Autowired
    private ConsultationRepository repository;

    public void validar (DataPostConsultation data){
        var primeiroHorario = data.date().withHour(7);
        var ultimoHorario = data.date().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPatientIdAndDataBetween(data.idPaciente(), primeiroHorario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
