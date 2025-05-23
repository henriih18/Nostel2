/*
package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.domain.repository.FichaRepository;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import Sena.ProyectoNostel.persistence.mapper.FichaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FichaServiceImpl implements FichaService {*/
/*
    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaMapper fichaMapper;
*//*


    private final FichaRepository fichaRepository;
    private final FichaMapper fichaMapper;

    public FichaServiceImpl(FichaRepository fichaRepository, FichaMapper fichaMapper) {
        this.fichaRepository = fichaRepository;
        this.fichaMapper = fichaMapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<FichaDTO> obtenerFichas() {
        return fichaRepository.findAll().stream().map(ficha -> {
            FichaDTO fichaDTO = fichaMapper.toFichaDTO(ficha);
            fichaDTO.setTotalAprendices(ficha.getAprendices() != null ? ficha.getAprendices().size() : 0);
            return fichaDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public Optional<FichaDTO> obtenerFichaId(Integer idFicha) {
        return fichaRepository.findById(idFicha)
                .map(ficha -> {
                    FichaDTO fichaDTO = fichaMapper.toFichaDTO(ficha);
                    fichaDTO.setTotalAprendices(ficha.getAprendices() != null ? ficha.getAprendices().size() : 0);
                    return fichaDTO;
                });
    }

    @Override
    public FichaDTO crearFicha(FichaDTO fichaDTO) {
        Ficha ficha = fichaMapper.toFicha(fichaDTO);
        ficha = fichaRepository.save(ficha);
        return fichaMapper.toFichaDTO(ficha);
    }

    @Override
    public Optional<FichaDTO> actualizarFicha(Integer idFicha, FichaDTO fichaDTO) {
        return fichaRepository.findById(idFicha).map(fichaExistente -> {
            fichaMapper.updateFichaFromDto(fichaDTO, fichaExistente);
            Ficha fichaActualizado = fichaRepository.save(fichaExistente);
            return fichaMapper.toFichaDTO(fichaActualizado);
        });
    }

    @Override
    public void eliminarFicha(Integer idFicha) {
        fichaRepository.deleteById(idFicha);
    }

}
*/

package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.domain.repository.FichaRepository;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import Sena.ProyectoNostel.persistence.mapper.FichaMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FichaServiceImpl implements FichaService {

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaMapper fichaMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public List<FichaDTO> obtenerFichas() {
        return fichaRepository.findAll().stream()
                .map(ficha -> {
                    FichaDTO fichaDTO = fichaMapper.toFichaDTO(ficha);
                    fichaDTO.setTotalAprendices(ficha.getAprendices() != null ? ficha.getAprendices().size() : 0);
                    return fichaDTO;
                }).collect(Collectors.toList());
    }
    public List<Ficha> obtenerFichasDisponibles() {
        LocalDate fechaActual = LocalDate.now();
        return fichaRepository.findFichasDisponibles(fechaActual);
    }

    @Override
    @Transactional
    public Optional<FichaDTO> obtenerFichaId(Integer idFicha) {
        return fichaRepository.findById(idFicha)
                .map(ficha -> {
                    FichaDTO fichaDTO = fichaMapper.toFichaDTO(ficha);
                    fichaDTO.setTotalAprendices(ficha.getAprendices() != null ? ficha.getAprendices().size() : 0);
                    return fichaDTO;
                });
    }

    @Override
    @Transactional
    public FichaDTO crearFicha(FichaDTO fichaDTO) {
        // Llamar al procedimiento almacenado
        jdbcTemplate.update("CALL InsertarFicha(?, ?, ?, ?, ?, ?)",
                fichaDTO.getNombrePrograma(),
                fichaDTO.getNumeroFicha(),
                fichaDTO.getHorario(),
                fichaDTO.getFechaInicio(),
                fichaDTO.getFechaFin(),
                fichaDTO.getNumeroAmbiente()
        );

        // Recuperar la ficha insertada
        Optional<Ficha> fichaOpt = fichaRepository.findByNumeroFicha(fichaDTO.getNumeroFicha());
        if (fichaOpt.isPresent()) {
            return fichaMapper.toFichaDTO(fichaOpt.get());
        } else {
            throw new IllegalArgumentException("Error al recuperar la ficha registrada.");
        }
    }

    @Override
    @Transactional
    public Optional<FichaDTO> actualizarFicha(Integer idFicha, FichaDTO fichaDTO) {
        return fichaRepository.findById(idFicha).map(fichaExistente -> {
            fichaMapper.updateFichaFromDto(fichaDTO, fichaExistente);
            Ficha fichaActualizado = fichaRepository.save(fichaExistente);
            return fichaMapper.toFichaDTO(fichaActualizado);
        });
    }

    @Override
    public void eliminarFicha(Integer idFicha) {
        fichaRepository.deleteById(idFicha);
    }
}

