package med.voll.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.DataAddress;

public record DataPutDoctor(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        DataAddress endereco

) {
}
