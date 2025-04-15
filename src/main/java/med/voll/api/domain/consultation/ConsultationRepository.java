package med.voll.api.domain.consultation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    boolean existsByDoctorIdAndDataAndReasonCancelIsNull(Long id, @NotNull @Future LocalDateTime date);

    boolean existsByPatientIdAndDataBetween(@NotNull Long aLong, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
