package com.alain.test.test.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alain.test.test.entities.Tarea;
import com.alain.test.test.services.impl.TareaServiceImp;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private TareaServiceImp tareaServiceImp;

    public TareaController(TareaServiceImp tareaServiceImp) {
        this.tareaServiceImp = tareaServiceImp;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearTarea(@Valid @RequestBody Tarea tarea, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Tarea tareaCreada = tareaServiceImp.save(tarea);
        return ResponseEntity.ok(tareaCreada);
    }

   
    @PutMapping("/actualizar")
     public ResponseEntity<?> updateTarea(@Valid @RequestBody Tarea tarea, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Tarea> TareaActualizado = tareaServiceImp.actualizarTarea(tarea);
        if (TareaActualizado.isPresent()) {
            return ResponseEntity.ok(TareaActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(tareaServiceImp.findAll());
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
}
