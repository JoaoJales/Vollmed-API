package med.voll.api.domain.patient;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByAtivoTrue(Pageable pageable);

    boolean existsByIdAndAtivoTrue(@NotNull Long id);
}
