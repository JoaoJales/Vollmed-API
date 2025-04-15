package med.voll.api.domain.consultation.validations.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.DataPostConsultation;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPacienteAtivo implements ValidatorConsultationService {
    @Autowired
    private PatientRepository repository;

    public void validar (DataPostConsultation data){
        if (!repository.existsByIdAndAtivoTrue(data.idPaciente())){
            throw new ValidacaoException("O Paciente não existe no sistema");
        }
    }
}
