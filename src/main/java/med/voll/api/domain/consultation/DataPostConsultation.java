package med.voll.api.domain.consultation;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record DataPostConsultation(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        @JsonAlias("data")
        //@JsonFormat(pattern = "dd/MM/yyyy HH:mm") //-> formatando a data e hr
        LocalDateTime date,

        Specialty specialty) {
}
