package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional // Indica uma transação com o banco de dados
    public ResponseEntity register(@RequestBody @Valid DataRegisterDoctor data, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetailingDoctor(doctor));
    }


    @GetMapping
    public ResponseEntity<Page<DataGetDoctor>> getDoctors(@PageableDefault(size = 10,sort = {"nome"}) Pageable paginacao){
        var page =  repository.findAllByAtivoTrue(paginacao)
                .map(DataGetDoctor::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity putDoctor(@RequestBody @Valid DataPutDoctor data){
        var doctor = repository.findById(data.id()).get();
        doctor.updateInfo(data);

        return ResponseEntity.ok(new DataDetailingDoctor(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDoctor(@PathVariable Long id){
        var doctor = repository.findById(id).get();
        doctor.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailsDoctor(@PathVariable Long id){
        var doctor = repository.findById(id).get();

        return ResponseEntity.ok(new DataDetailingDoctor(doctor));
    }


}
