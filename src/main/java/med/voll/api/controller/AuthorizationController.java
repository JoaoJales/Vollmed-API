package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.user.DataAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthorizationController {
    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DataAuthorization dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        //Converte o nosso DTO (DataAuthorization) para o DTO so Spring (UsernamePasswordAuthenticationToken) para gerar o token
        var authetication = manager.authenticate(token);

        return ResponseEntity.ok().build();

    }
}
