package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public interface FichaService {
    List<FichaDTO> obtenerFichas();
    List<Ficha> obtenerFichasDisponibles();
    Optional<FichaDTO> obtenerFichaId(Integer idFicha);
    FichaDTO crearFicha(FichaDTO fichaDTO);
    Optional<FichaDTO> actualizarFicha(Integer idFicha, FichaDTO fichaDTO);
    void eliminarFicha(Integer idFicha);
}
