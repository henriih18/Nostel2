
package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.repository.ActividadComplementariaRepository;
import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import Sena.ProyectoNostel.persistence.entity.Compromiso;
import Sena.ProyectoNostel.persistence.entity.Asistente;
import Sena.ProyectoNostel.persistence.mapper.ActividadComplementariaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadComplementariaServiceImpl implements ActividadComplementariaService {

    private static final Logger logger = LoggerFactory.getLogger(ActividadComplementariaServiceImpl.class);

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
        logger.info("Iniciando agregarActividad con idAprendiz: {}, idInstructor: {}",
                actividadComplementariaDTO.getIdAprendiz(), actividadComplementariaDTO.getIdInstructor());

        // 1. Validar que los campos obligatorios no sean nulos
        if (actividadComplementariaDTO.getIdAprendiz() == null || actividadComplementariaDTO.getIdInstructor() == null) {
            logger.error("idAprendiz o idInstructor es nulo");
            throw new IllegalArgumentException("Los campos idAprendiz e idInstructor son obligatorios.");
        }

        // 2. Validar que el acta de asistencia esté completa
        if (!esActaAsistenciaCompleta(actividadComplementariaDTO)) {
            logger.error("El acta de asistencia no está completa. Debe incluir al menos un asistente con todos los campos obligatorios.");
            throw new IllegalArgumentException("El acta de asistencia no está completa. Debe incluir al menos un asistente con todos los campos obligatorios.");
        }

        // 3. Invocar el procedimiento almacenado para insertar los datos principales
        actividadComplementariaRepository.asignarActividadComplementaria(
                actividadComplementariaDTO.getIdAprendiz(),
                actividadComplementariaDTO.getIdInstructor(),
                actividadComplementariaDTO.getEstado(),
                actividadComplementariaDTO.getNombreComite(),
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

        // 3. Obtener el ID generado por el procedimiento
        Integer idActividad = actividadComplementariaRepository.getLastInsertedId();
        logger.info("ID de actividad generado: {}", idActividad);

        // 4. Crear una entidad temporal para manejar las relaciones
        ActividadComplementaria actividad = new ActividadComplementaria();
        actividad.setIdActividad(idActividad);
        actividad.setIdAprendiz(actividadComplementariaDTO.getIdAprendiz());
        actividad.setIdInstructor(actividadComplementariaDTO.getIdInstructor()); // Corrección de tipografía
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

        // 5. Guardar compromisos
        if (actividadComplementariaDTO.getCompromisos() != null && !actividadComplementariaDTO.getCompromisos().isEmpty()) {
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

        // 6. Guardar asistentes
        if (actividadComplementariaDTO.getAsistentes() != null && !actividadComplementariaDTO.getAsistentes().isEmpty()) {
            List<Asistente> asistentes = actividadComplementariaDTO.getAsistentes().stream()
                    .map(asistenteDTO -> {
                        Asistente asistente = new Asistente();
                        asistente.setNombre(asistenteDTO.getNombre());
                        asistente.setDependenciaEmpresa(asistenteDTO.getDependenciaEmpresa() != null ? asistenteDTO.getDependenciaEmpresa() : "Sin dependencia");
                        asistente.setAprueba(asistenteDTO.getAprueba() != null ? asistenteDTO.getAprueba() : "NO");
                        asistente.setObservacion(asistenteDTO.getObservacion());
                        asistente.setFirmaParticipacion(asistenteDTO.getFirmaParticipacion() != null ? asistenteDTO.getFirmaParticipacion() : "");
                        asistente.setActividadComplementaria(actividad);
                        asistente.setNumeroDocumento(asistenteDTO.getNumeroDocumento());
                        asistente.setPlanta(asistenteDTO.getPlanta());
                        asistente.setContratista(asistenteDTO.getContratista());
                        asistente.setOtro(asistenteDTO.getOtro());
                        asistente.setCorreoElectronico(asistenteDTO.getCorreoElectronico());
                        asistente.setTelefonoExt(asistenteDTO.getTelefonoExt());
                        asistente.setAutorizaGrabacion(asistenteDTO.getAutorizaGrabacion() != null ? asistenteDTO.getAutorizaGrabacion() : false);
                        return asistente;
                    }).collect(Collectors.toList());
            actividad.setAsistentes(asistentes);
        }

        // DEBUG: Imprimir valores booleanos antes de guardar
        if (actividad.getAsistentes() != null) {
            for (Asistente a : actividad.getAsistentes()) {
                System.out.println("===== DEBUG ASISTENTE =====");
                System.out.println("Nombre: " + a.getNombre());
                System.out.println("Planta: " + a.getPlanta());
                System.out.println("Contratista: " + a.getContratista());
                System.out.println("Autoriza Grabación: " + a.getAutorizaGrabacion());
            }
        }


        // 7. Guardar las relaciones (compromisos y asistentes) usando JPA
        actividadComplementariaRepository.save(actividad);
        logger.info("Actividad y relaciones guardadas exitosamente con idActividad: {}", idActividad);

        // 8. Recuperar la entidad completa para devolver el DTO
        ActividadComplementaria savedActividad = actividadComplementariaRepository.findById(idActividad)
                .orElseThrow(() -> new RuntimeException("No se pudo recuperar la actividad recién creada"));
        return actividadComplementariaMapper.toActividadComplementariaDTO(savedActividad);
    }

    /*private boolean esActaAsistenciaCompleta(ActividadComplementariaDTO dto) {
        if (dto.getFecha() == null || dto.getObjetivos() == null || dto.getObjetivos().trim().isEmpty()) {
            logger.error("Fecha u objetivos no están completos: fecha={}, objetivos={}", dto.getFecha(), dto.getObjetivos());
            return false;
        }
        if (dto.getAsistentes() == null || dto.getAsistentes().isEmpty()) {
            logger.error("No se proporcionaron asistentes para el acta.");
            return false;
        }
        return dto.getAsistentes().stream().allMatch(asistente -> {
            boolean isValid = asistente.getNombre() != null && !asistente.getNombre().trim().isEmpty() &&
                    asistente.getNumeroDocumento() != null && !asistente.getNumeroDocumento().trim().isEmpty() &&
                    asistente.getFirmaParticipacion() != null && !asistente.getFirmaParticipacion().trim().isEmpty();
            if (isValid && asistente.getCorreoElectronico() != null && !asistente.getCorreoElectronico().trim().isEmpty()) {
                // Validar formato de correo
                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                isValid = asistente.getCorreoElectronico().matches(emailPattern);
                if (!isValid) {
                    logger.warn("Correo electrónico inválido para asistente: {}", asistente.getCorreoElectronico());
                }
            }
            return isValid;
        });
    }*/
    private boolean esActaAsistenciaCompleta(ActividadComplementariaDTO dto) {
        // Validar campos del acta
        if (dto.getFecha() == null) {
            logger.error("Fecha no está completa: fecha={}", dto.getFecha());
            throw new IllegalArgumentException("La fecha del acta es obligatoria.");
        }
        if (dto.getObjetivos() == null || dto.getObjetivos().trim().isEmpty()) {
            logger.error("Objetivos no están completos: objetivos={}", dto.getObjetivos());
            throw new IllegalArgumentException("Los objetivos del acta son obligatorios y no pueden estar vacíos.");
        }
        // Validar asistentes
        if (dto.getAsistentes() == null || dto.getAsistentes().isEmpty()) {
            logger.error("No se proporcionaron asistentes para el acta.");
            throw new IllegalArgumentException("El acta debe incluir al menos un asistente.");
        }
        dto.getAsistentes().stream().allMatch(asistente -> {
            if (asistente.getNombre() == null || asistente.getNombre().trim().isEmpty()) {
                logger.error("Nombre del asistente no está completo: nombre={}", asistente.getNombre());
                throw new IllegalArgumentException("El nombre del asistente es obligatorio.");
            }
            if (asistente.getNumeroDocumento() == null || asistente.getNumeroDocumento().trim().isEmpty()) {
                logger.error("Número de documento del asistente no está completo: numeroDocumento={}", asistente.getNumeroDocumento());
                throw new IllegalArgumentException("El número de documento del asistente es obligatorio.");
            }
            if (asistente.getFirmaParticipacion() == null || asistente.getFirmaParticipacion().trim().isEmpty()) {
                logger.error("Firma de participación del asistente no está completa: firmaParticipacion={}", asistente.getFirmaParticipacion());
                throw new IllegalArgumentException("La firma o participación virtual del asistente es obligatoria.");
            }
            if (asistente.getCorreoElectronico() != null && !asistente.getCorreoElectronico().trim().isEmpty()) {
                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                if (!asistente.getCorreoElectronico().matches(emailPattern)) {
                    logger.warn("Correo electrónico inválido para asistente: {}", asistente.getCorreoElectronico());
                    throw new IllegalArgumentException("El correo electrónico del asistente no tiene un formato válido: " + asistente.getCorreoElectronico());
                }
            }
            return true;
        });
        return true;
    }

    @Override
    public Optional<ActividadComplementariaDTO> actualizarActividad(Integer idActividad, ActividadComplementariaDTO actividadComplementariaDTO) {
        Optional<ActividadComplementaria> existingActividad = actividadComplementariaRepository.findById(idActividad);
        if (existingActividad.isPresent()) {
            ActividadComplementaria actividad = actividadComplementariaMapper.toActividadComplementaria(actividadComplementariaDTO);
            actividad.setIdActividad(idActividad);
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