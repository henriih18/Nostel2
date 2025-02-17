package Sena.ProyectoNostel.domain.service;


import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.persistence.mapper.InstructorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorMapper instructorMapper;

    @Override
    public List<InstructorDTO> obtenerInstructores() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toInstructorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InstructorDTO> obtenerPorIdInstructor(Integer idInstructor) {
        return instructorRepository.findById(idInstructor)
                .map(instructorMapper::toInstructorDTO);
    }

    @Override
    public InstructorDTO crearInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = instructorMapper.toInstructor(instructorDTO);
        instructor = instructorRepository.save(instructor);
        return instructorMapper.toInstructorDTO(instructor);
    }

    @Override
    public Optional<InstructorDTO> actualizarInstructor(Integer idInstructor, InstructorDTO instructorDTO) {
        return instructorRepository.findById(idInstructor).map(instructorExistente -> {
            instructorMapper.updateInstructorFromDto(instructorDTO, instructorExistente);
            Instructor instructorActualizado = instructorRepository.save(instructorExistente);
            return instructorMapper.toInstructorDTO(instructorActualizado);
        });
    }

    @Override
    public void eliminarInstructor(Integer idInstructor) {
        instructorRepository.deleteById(idInstructor);
    }
}
