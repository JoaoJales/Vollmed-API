package med.voll.api.doctor;

public record DataGetDoctor(Long id, String nome, String email, String crm, Specialty especialidade) {

    public DataGetDoctor(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
