package med.voll.api.domain.patient;


public record DataGetPatients (Long id, String nome, String email, String cpf){
    public DataGetPatients(Patient patient){
        this(patient.getId(),patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}
