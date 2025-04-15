package med.voll.api.domain.doctor;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT d FROM Doctor d
            WHERE d.ativo = true AND d.especialidade ILIKE :specialty 
            AND d.id not in(
                SELECT c.doctor.id FROM Consultation c 
                WHERE c.data = :date
                AND
                c.reasonCancel is null
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Doctor getRandomDoctor(Specialty specialty, @NotNull @Future LocalDateTime date);

    boolean existsByIdAndAtivoTrue(Long id);
}
