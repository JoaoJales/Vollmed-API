package med.voll.api.domain.patient;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import med.voll.api.domain.address.Address;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded // Indica que os atributos da Classe Address serão armazenados na mesma tabela de Patient (Address não será uma @Entity)
    private Address endereco;
    private Boolean ativo;

    public Patient(DataRegisterPatient data) {
        this.ativo = true;
        this.nome = data.nome();
        this.email = data.email();
        this.telefone = data.telefone();
        this.cpf = data.cpf();
        this.endereco = new Address(data.endereco());
    }

    public void updateInfo(@Valid DataPutPatient data) {
        if (data.nome() != null){
            this.nome = data.nome();
        }
        if (data.telefone() != null){
            this.telefone = data.telefone();
        }
        if (data.endereco() != null){
            this.endereco.updateInfoAddress(data.endereco());
        }
    }

    public void delete() {
        this.ativo = false;
    }
}
