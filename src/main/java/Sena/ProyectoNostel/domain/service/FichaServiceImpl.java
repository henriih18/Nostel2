package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.domain.repository.FichaRepository;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import Sena.ProyectoNostel.persistence.mapper.FichaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FichaServiceImpl implements FichaService {
    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaMapper fichaMapper;

    @Override
public List<FichaDTO> obtenerFichas() {
    return fichaRepository.findAll().stream().map(fichaMapper::toFichaDTO).collect(Collectors.toList());
}


@Override
    public Optional<FichaDTO> obtenerFichaId(Integer idFicha) {
        return fichaRepository.findById(idFicha)
                .map(fichaMapper::toFichaDTO);
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
