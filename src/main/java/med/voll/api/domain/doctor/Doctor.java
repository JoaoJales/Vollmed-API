package med.voll.api.domain.doctor;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Specialty especialidade;
    @Embedded // Indica que os atributos da Classe Address serão armazenados na mesma tabela de Doctor sem criar uma tabela para Address
    // (Address não será uma @Entity)
    private Address endereco;
    private Boolean ativo;

    public Doctor(DataRegisterDoctor data) {
        this.ativo = true;
        this.nome = data.nome();
        this.email = data.email();
        this.crm = data.crm();
        this.especialidade = data.especialidade();
        this.telefone = data.telefone();
        this.endereco = new Address(data.endereco());
    }

    public void updateInfo(@Valid DataPutDoctor data) {
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
