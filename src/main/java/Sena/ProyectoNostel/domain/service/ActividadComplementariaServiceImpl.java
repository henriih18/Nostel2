
package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.repository.ActividadComplementariaRepository;
import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import Sena.ProyectoNostel.persistence.entity.Compromiso;
import Sena.ProyectoNostel.persistence.entity.Asistente;
import Sena.ProyectoNostel.persistence.mapper.ActividadComplementariaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadComplementariaServiceImpl implements ActividadComplementariaService {

    @Autowired
    private ActividadComplementariaRepository actividadComplementariaRepository;

    @Autowired
    private ActividadComplementariaMapper actividadComplementariaMapper;

    @Override
    @Transactional
    public List<ActividadComplementariaDTO> obtenerActividadesPorAprendiz(Integer idAprendiz) {
        return actividadComplementariaRepository
                .findByIdAprendiz(idAprendiz)
                .stream()
                .map(actividadComplementariaMapper::toActividadComplementariaDTO)
                .toList();
    }

    @Override
    @Transactional
    public ActividadComplementariaDTO agregarActividad(ActividadComplementariaDTO actividadComplementariaDTO) {

        // 1. Invocar el procedimiento almacenado para insertar los datos principales
        actividadComplementariaRepository.asignarActividadComplementaria(
                actividadComplementariaDTO.getIdAprendiz(),
                actividadComplementariaDTO.getIdInstructor(),
                actividadComplementariaDTO.getEstado(),
                actividadComplementariaDTO.getNombreComite(), // Usamos nombreComite como actividad

                actividadComplementariaDTO.getActaNumber(),
                actividadComplementariaDTO.getCiudad(),
                actividadComplementariaDTO.getFecha(),
                actividadComplementariaDTO.getHoraInicio(),
                actividadComplementariaDTO.getHoraFin(),
                actividadComplementariaDTO.getLugarEnlace(),
                actividadComplementariaDTO.getDireccionRegionalCentro(),
                actividadComplementariaDTO.getAgenda(),
                actividadComplementariaDTO.getObjetivos(),
                actividadComplementariaDTO.getDesarrollo(),
                actividadComplementariaDTO.getConclusiones()
        );

        // 2. Obtener el ID generado por el procedimiento
        Integer idActividad = actividadComplementariaRepository.getLastInsertedId();

        // 3. Crear una entidad temporal para manejar las relaciones
        ActividadComplementaria actividad = new ActividadComplementaria();
        actividad.setIdActividad(idActividad);
        actividad.setIdAprendiz(actividadComplementariaDTO.getIdAprendiz());
        actividad.setIdIntructor(actividadComplementariaDTO.getIdInstructor());
        actividad.setEstado(ActividadComplementaria.EstadoActvidad.valueOf(actividadComplementariaDTO.getEstado()));
        actividad.setActaNumber(actividadComplementariaDTO.getActaNumber());
        actividad.setNombreComite(actividadComplementariaDTO.getNombreComite());
        actividad.setCiudad(actividadComplementariaDTO.getCiudad());
        actividad.setFecha(actividadComplementariaDTO.getFecha());
        actividad.setHoraInicio(actividadComplementariaDTO.getHoraInicio());
        actividad.setHoraFin(actividadComplementariaDTO.getHoraFin());
        actividad.setLugarEnlace(actividadComplementariaDTO.getLugarEnlace());
        actividad.setDireccionRegionalCentro(actividadComplementariaDTO.getDireccionRegionalCentro());
        actividad.setAgenda(actividadComplementariaDTO.getAgenda());
        actividad.setObjetivos(actividadComplementariaDTO.getObjetivos());
        actividad.setDesarrollo(actividadComplementariaDTO.getDesarrollo());
        actividad.setConclusiones(actividadComplementariaDTO.getConclusiones());

        // 4. Guardar compromisos
        if (actividadComplementariaDTO.getCompromisos() != null) {
            List<Compromiso> compromisos = actividadComplementariaDTO.getCompromisos().stream()
                    .map(compromisoDTO -> {
                        Compromiso compromiso = new Compromiso();
                        compromiso.setActividadDecision(compromisoDTO.getActividadDecision());
                        compromiso.setFecha(compromisoDTO.getFecha());
                        compromiso.setResponsable(compromisoDTO.getResponsable());
                        compromiso.setFirmaParticipacion(compromisoDTO.getFirmaParticipacion());
                        compromiso.setActividadComplementaria(actividad);
                        return compromiso;
                    }).collect(Collectors.toList());
            actividad.setCompromisos(compromisos);
        }

        // 5. Guardar asistentes
        if (actividadComplementariaDTO.getAsistentes() != null) {
            List<Asistente> asistentes = actividadComplementariaDTO.getAsistentes().stream()
                    .map(asistenteDTO -> {
                        Asistente asistente = new Asistente();
                        asistente.setNombre(asistenteDTO.getNombre());
                        asistente.setDependenciaEmpresa(asistenteDTO.getDependenciaEmpresa());
                        asistente.setAprueba(asistenteDTO.getAprueba());
                        asistente.setObservacion(asistenteDTO.getObservacion());
                        asistente.setFirmaParticipacion(asistenteDTO.getFirmaParticipacion());
                        asistente.setActividadComplementaria(actividad);
                        return asistente;
                    }).collect(Collectors.toList());
            actividad.setAsistentes(asistentes);
        }

        // 6. Guardar las relaciones (compromisos y asistentes) usando JPA
        actividadComplementariaRepository.save(actividad);

        // 7. Recuperar la entidad completa para devolver el DTO
        ActividadComplementaria savedActividad = actividadComplementariaRepository.findById(idActividad)
                .orElseThrow(() -> new RuntimeException("No se pudo recuperar la actividad recién creada"));
        return actividadComplementariaMapper.toActividadComplementariaDTO(savedActividad);
    }

    @Override
    public Optional<ActividadComplementariaDTO> actualizarActividad(Integer idActividad, ActividadComplementariaDTO actividadComplementariaDTO) {
        Optional<ActividadComplementaria> existingActividad = actividadComplementariaRepository.findById(idActividad);
        if (existingActividad.isPresent()) {
            ActividadComplementaria actividad = actividadComplementariaMapper.toActividadComplementaria(actividadComplementariaDTO);
            actividad.setIdActividad(idActividad); // Mantener el ID existente
            ActividadComplementaria updatedActividad = actividadComplementariaRepository.save(actividad);
            return Optional.of(actividadComplementariaMapper.toActividadComplementariaDTO(updatedActividad));
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminarActividad(Integer idAprendiz, Integer idActividad) {
        Optional<ActividadComplementaria> actividad = actividadComplementariaRepository.findById(idActividad);
        if (actividad.isPresent() && actividad.get().getIdAprendiz().equals(idAprendiz)) {
            actividadComplementariaRepository.deleteById(idActividad);
            return true;
        }
        return false;
    }
}
