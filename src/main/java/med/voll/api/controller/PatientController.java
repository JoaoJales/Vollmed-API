package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/pacientes")
public class PatientController {
    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registerPatient(@RequestBody @Valid DataRegisterPatient data, UriComponentsBuilder uriBuilder){
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetailingPatient(patient));
    }

    @GetMapping
    public ResponseEntity<Page<DataGetPatients>> getPatients(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        var page = repository.findAllByAtivoTrue(pageable)
                .map(DataGetPatients::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity putPatient(@RequestBody @Valid DataPutPatient data){
        var patient = repository.findById(data.id()).get();
        patient.updateInfo(data);

        return ResponseEntity.ok(new DataDetailingPatient(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePatient(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        patient.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailsPatient(@PathVariable Long id){
        var patient = repository.getReferenceById(id);

        return ResponseEntity.ok(new DataDetailingPatient(patient));
    }

}
