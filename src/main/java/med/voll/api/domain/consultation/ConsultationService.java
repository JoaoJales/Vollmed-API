package med.voll.api.domain.consultation;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultation.validations.agendamento.ValidatorConsultationService;
import med.voll.api.domain.consultation.validations.cancelamento.ValidatorCancelConsultation;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository repository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ValidatorConsultationService> validatorsAgendamento;

    @Autowired
    private List<ValidatorCancelConsultation> validatorsCancelamento;


    public DataDetailingConsultation agendar(DataPostConsultation data){
        if (!patientRepository.existsById(data.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if (data.idMedico() != null && !doctorRepository.existsById(data.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validatorsAgendamento.forEach(v -> v.validar(data));

        var doctor = escolherMedico(data);

        if(doctor == null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        var patient = patientRepository.findById(data.idPaciente()).get();

        var consultation = new Consultation(null,doctor, patient, data.date(), null);

        repository.save(consultation);

        return new DataDetailingConsultation(consultation);
    }

    private Doctor escolherMedico(DataPostConsultation data) {
        if (data.idMedico() != null){
            return doctorRepository.getReferenceById(data.idMedico()); //(getReferenceById) Usado para quando não queremos carregar o objeto para mapula-lo, somente para atribuir/relacionar com outro objeto
        }
        if (data.specialty() == null){
            throw new ValidacaoException("Especialiade é obrigatório quando um médico não for especificado!");
        }

        return doctorRepository.getRandomDoctor(data.specialty(), data.date());


    }

    public void cancelar(@Valid DataCancelConsultation data) {
        if (!repository.existsById(data.idConsultation())){
            throw new ValidacaoException("ID da consulta não econtrado no sistema");
        }

        validatorsCancelamento.forEach(v -> v.validar(data));


        var consultation = repository.findById(data.idConsultation()).get();
        consultation.cancelar(data);
    }
}
