package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;


public record DataDetailingDoctor(Long id, String email, String tefone, String crm, Specialty especialidade, Address endereco) {
    public DataDetailingDoctor(Doctor doctor){
        this(doctor.getId(), doctor.getEmail(), doctor.getTelefone(), doctor.getCrm(), doctor.getEspecialidade(), doctor.getEndereco());
    }
}
