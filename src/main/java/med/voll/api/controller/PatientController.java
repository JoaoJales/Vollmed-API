package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pacientes")
public class PatientController {
    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public void registerPatient(@RequestBody @Valid DataRegisterPatient data){
        repository.save(new Patient(data));
    }

    @GetMapping
    public Page<DataGetPatients> getPatients(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        return repository.findAllByAtivoTrue(pageable)
                .map(DataGetPatients::new);
    }

    @PutMapping
    @Transactional
    public void putPatient(@RequestBody @Valid DataPutPatient data){
        var patient = repository.findById(data.id()).get();
        patient.updateInfo(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletePatient(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        patient.delete();
    }
}
