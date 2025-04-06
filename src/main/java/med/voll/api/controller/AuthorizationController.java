package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.user.DataAuthorization;
import med.voll.api.domain.user.User;
import med.voll.api.infra.security.DataTokenJWT;
import med.voll.api.infra.security.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DataAuthorization dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        //Converte o nosso DTO (DataAuthorization) para o DTO do Spring (UsernamePasswordAuthenticationToken) para gerar o token
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal()); //Cria o TokenJWT

        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));

    }
}
