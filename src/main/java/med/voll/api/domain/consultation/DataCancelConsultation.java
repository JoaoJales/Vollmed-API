package med.voll.api.domain.consultation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataCancelConsultation(@NotNull Long idConsultation, @NotBlank String reason) {
}
