package com.alain.test.test.services.impl;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alain.test.test.entities.Tarea;
import com.alain.test.test.repositories.TareaRepository;
import com.alain.test.test.services.TareaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class TareaServiceImp implements TareaService{

    @Autowired
    private TareaRepository tareaRepository;

    public TareaServiceImp(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    @Transactional
    @Override
    public Tarea save(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Optional<Tarea> actualizarTarea(@Valid Tarea tarea) {
        Optional<Tarea> tareaActualizada = tareaRepository.findById(tarea.getId());
        if (tareaActualizada.isPresent()) {
            tareaActualizada.get().setDescripcion(tarea.getDescripcion());
            tareaActualizada.get().setVigente(tarea.isVigente());
            tareaActualizada.get().setAudit(tarea.getAudit());
            tareaRepository.save(tareaActualizada.get());
        }
        return tareaActualizada;
       
    }

    public List<Tarea> findAll() {
        return (List<com.alain.test.test.entities.Tarea>) tareaRepository.findAll();
    }

   
}
