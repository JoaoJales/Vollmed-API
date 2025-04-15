package med.voll.api.domain.consultation.validations.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.ConsultationRepository;
import med.voll.api.domain.consultation.DataPostConsultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationAgendaMedico implements ValidatorConsultationService {
    @Autowired
    private ConsultationRepository repository;

    public void validar (DataPostConsultation data){
        if (repository.existsByDoctorIdAndDataAndReasonCancelIsNull(data.idMedico(), data.date())){
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
