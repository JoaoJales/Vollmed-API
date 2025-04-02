package med.voll.api.domain.doctor;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.DataAddress;


public record DataRegisterDoctor(
        //@NotNull   //NotBlank já implementa o NotNull
        @NotBlank    //Não pode ser vazio
        String nome,
        @NotBlank //(message = "Email é obrigatório") // Passando uma mensagem personalizada em caso de erro
//        @Email (message = "Email inválido")
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Specialty especialidade,

        @NotNull @Valid DataAddress endereco) {
}
