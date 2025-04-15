package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultation.ConsultationService;
import med.voll.api.domain.consultation.DataCancelConsultation;
import med.voll.api.domain.consultation.DataDetailingConsultation;
import med.voll.api.domain.consultation.DataPostConsultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultationController {
    @Autowired
    private ConsultationService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DataPostConsultation data){
        var dataDetailingConsultation = service.agendar(data);

        return ResponseEntity.ok().body(dataDetailingConsultation);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DataCancelConsultation data){
        service.cancelar(data);

        return ResponseEntity.noContent().build();
    }


}
