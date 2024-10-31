package com.unik.unikForma.service;


import com.unik.unikForma.entity.Classe;
import com.unik.unikForma.entity.Course;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {

    private  final ClasseRepository classeRepository;

        @Autowired
        public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }


    public Classe saveClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public Page<Classe> findAll(int page, int size) {
        return classeRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Classe> getClasseById(Long id) {
        return classeRepository.findById(id);
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

    public Optional<Classe> update(Long id, Classe updatedClasse) {
        return classeRepository.findById(id).map(existingClasse -> {
            existingClasse.setName(updatedClasse.getName());
            existingClasse.setRoomNum(updatedClasse.getRoomNum());
            return classeRepository.save(existingClasse);
        });
    }


}
