package med.voll.api.domain.consultation.validations.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.DataPostConsultation;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationsMedicoAtivo implements ValidatorConsultationService {
    @Autowired
    private DoctorRepository repository;

    public void validar (DataPostConsultation data){
        if (data.idMedico() == null){
            return;
        }

        if (!repository.existsByIdAndAtivoTrue(data.idMedico())){
            throw new ValidacaoException("O Médico não existe no sistema");
        }
    }
}
