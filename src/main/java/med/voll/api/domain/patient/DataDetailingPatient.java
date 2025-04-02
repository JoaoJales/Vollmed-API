package med.voll.api.domain.patient;

import med.voll.api.domain.address.Address;

public record DataDetailingPatient(Long id, String nome, String email, String telefone, String cpf, Address endereco) {
    public DataDetailingPatient(Patient patient){
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getTelefone(), patient.getCpf(), patient.getEndereco());
    }
}
