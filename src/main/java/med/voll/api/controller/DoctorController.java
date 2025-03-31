package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medicos")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional // Indica uma transação com o banco de dados (save)
    public void register(@RequestBody @Valid DataRegisterDoctor data) {
        repository.save(new Doctor(data));
    }

    @GetMapping
    public Page<DataGetDoctor> getDoctors(@PageableDefault(size = 10,sort = {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DataGetDoctor::new);
    }

    @PutMapping
    @Transactional
    public void putDoctor( @RequestBody @Valid DataPutDoctor data){
        var doctor = repository.findById(data.id()).get();
        doctor.updateInfo(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDoctor(@PathVariable Long id){
        var doctor = repository.findById(id).get();
        doctor.delete();
    }



}
