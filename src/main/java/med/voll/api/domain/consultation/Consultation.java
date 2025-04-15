package med.voll.api.domain.consultation;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.patient.Patient;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Reason reasonCancel;

    public void cancelar(@Valid DataCancelConsultation data) {
        this.reasonCancel = Reason.fromString(data.reason());
    }
}
