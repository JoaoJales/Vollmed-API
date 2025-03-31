package med.voll.api.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.address.DataAddress;

public record DataPutPatient(@NotNull Long id, String nome, String telefone, @Valid DataAddress endereco) {
}
