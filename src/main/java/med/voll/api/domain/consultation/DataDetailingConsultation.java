package med.voll.api.domain.consultation;

import java.time.LocalDateTime;

public record DataDetailingConsultation(Long id, Long idMedico, Long idPaciente, LocalDateTime date) {
    public DataDetailingConsultation(Consultation consultation) {
        this(consultation.getId(), consultation.getDoctor().getId(), consultation.getPatient().getId(), consultation.getData());
    }
}
