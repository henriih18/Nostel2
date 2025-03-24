package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ProgramaDTO;
import Sena.ProyectoNostel.persistence.mapper.ProgramaMapper;
import Sena.ProyectoNostel.domain.repository.ProgramaRepository;
import Sena.ProyectoNostel.persistence.entity.Programa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaServiceImpl implements ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private ProgramaMapper programaMapper;

    @Override

    public List<ProgramaDTO> obtenerProgramas() {
        return programaMapper.toProgramaDTOList(programaRepository.findAll());
    }

    @Override

    public Optional<ProgramaDTO> obtenerPorIdPrograma(Integer idPrograma) {
        return programaRepository.findById(idPrograma)
                .map(programaMapper::toProgramaDTO);
    }

    public ProgramaDTO crearPrograma(ProgramaDTO programaDTO) {
        Programa programa = programaMapper.toPrograma(programaDTO);
        return programaMapper.toProgramaDTO(programaRepository.save(programa));
    }

    public Optional<ProgramaDTO> actualizarPrograma(Integer idPrograma, ProgramaDTO programaDTO) {
        return programaRepository.findById(idPrograma).map(programaExistente -> {
            programaMapper.updateProgramaFromDto(programaDTO, programaExistente);
            return programaMapper.toProgramaDTO(programaRepository.save(programaExistente));
        });
    }

    public void eliminarPrograma(Integer idPrograma) {
        programaRepository.deleteById(idPrograma);
    }

}
