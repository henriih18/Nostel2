/*


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
        actividad.setIdInstructor(actividadComplementariaDTO.getIdInstructor());
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
*/
package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.domain.repository.ActividadComplementariaRepository;
import Sena.ProyectoNostel.domain.repository.PlanMejoramientoRepository;
import Sena.ProyectoNostel.persistence.entity.*;
import Sena.ProyectoNostel.persistence.mapper.ActividadComplementariaMapper;
import Sena.ProyectoNostel.persistence.mapper.PlanMejoramientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanMejoramientoServiceImpl implements PlanMejoramientoService {

    private static final Logger logger = LoggerFactory.getLogger(PlanMejoramientoServiceImpl.class);

    @Autowired
    private PlanMejoramientoRepository planMejoramientoRepository;

    @Autowired
    private PlanMejoramientoMapper planMejoramientoMapper;

    @Override
    @Transactional
    public List<PlanMejoramientoDTO> obtenerPlanesPorAprendiz(Integer idAprendiz) {
        return planMejoramientoRepository
                .findByIdAprendiz(idAprendiz)
                .stream()
                .map(planMejoramientoMapper::toPlanMejoramientoDTO)
                .toList();
    }

    @Override
    @Transactional
    public PlanMejoramientoDTO agregarPlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO) {
        logger.info("Iniciando agregarPlan con idAprendiz: {}, idInstructor: {}",
                planMejoramientoDTO.getIdAprendiz(), planMejoramientoDTO.getIdInstructor());

        // 1. Validar que los campos obligatorios no sean nulos
        if (planMejoramientoDTO.getIdAprendiz() == null || planMejoramientoDTO.getIdInstructor() == null) {
            logger.error("idAprendiz o idInstructor es nulo");
            throw new IllegalArgumentException("Los campos idAprendiz e idInstructor son obligatorios.");
        }

        // 2. Validar que el acta de asistencia esté completa
        if (!esActaAsistenciaCompleta(planMejoramientoDTO)) {
            logger.error("El acta de asistencia no está completa. Debe incluir al menos un asistente con todos los campos obligatorios.");
            throw new IllegalArgumentException("El acta de asistencia no está completa. Debe incluir al menos un asistente con todos los campos obligatorios.");
        }

        // 3. Invocar el procedimiento almacenado para insertar los datos principales
        planMejoramientoRepository.asignarPlanMejoramiento(
                planMejoramientoDTO.getIdAprendiz(),
                planMejoramientoDTO.getIdInstructor(),
                planMejoramientoDTO.getEstado(),
                planMejoramientoDTO.getNombreComite(),
                planMejoramientoDTO.getActaNumber(),
                planMejoramientoDTO.getCiudad(),
                planMejoramientoDTO.getFecha(),
                planMejoramientoDTO.getHoraInicio(),
                planMejoramientoDTO.getHoraFin(),
                planMejoramientoDTO.getLugarEnlace(),
                planMejoramientoDTO.getDireccionRegionalCentro(),
                planMejoramientoDTO.getAgenda(),
                planMejoramientoDTO.getObjetivos(),
                planMejoramientoDTO.getDesarrollo(),
                planMejoramientoDTO.getConclusiones()
        );

        // 3. Obtener el ID generado por el procedimiento
        Integer idPlanMejoramiento = planMejoramientoRepository.getLastInsertedId();
        logger.info("ID de plan generado: {}", idPlanMejoramiento);

        // 4. Crear una entidad temporal para manejar las relaciones
        PlanMejoramiento plan = new PlanMejoramiento();
        plan.setIdPlanMejoramiento(idPlanMejoramiento);
        plan.setIdAprendiz(planMejoramientoDTO.getIdAprendiz());
        plan.setIdInstructor(planMejoramientoDTO.getIdInstructor());
        plan.setEstado(PlanMejoramiento.EstadoPlan.valueOf(planMejoramientoDTO.getEstado()));
        plan.setActaNumber(planMejoramientoDTO.getActaNumber());
        plan.setNombreComite(planMejoramientoDTO.getNombreComite());
        plan.setCiudad(planMejoramientoDTO.getCiudad());
        plan.setFecha(planMejoramientoDTO.getFecha());
        plan.setHoraInicio(planMejoramientoDTO.getHoraInicio());
        plan.setHoraFin(planMejoramientoDTO.getHoraFin());
        plan.setLugarEnlace(planMejoramientoDTO.getLugarEnlace());
        plan.setDireccionRegionalCentro(planMejoramientoDTO.getDireccionRegionalCentro());
        plan.setAgenda(planMejoramientoDTO.getAgenda());
        plan.setObjetivos(planMejoramientoDTO.getObjetivos());
        plan.setDesarrollo(planMejoramientoDTO.getDesarrollo());
        plan.setConclusiones(planMejoramientoDTO.getConclusiones());

        // 5. Guardar compromisos
        if (planMejoramientoDTO.getCompromisosPlan() != null && !planMejoramientoDTO.getCompromisosPlan().isEmpty()) {
            List<CompromisoPlan> compromisosPlan = planMejoramientoDTO.getCompromisosPlan().stream()
                    .map(compromisoPlanDTO -> {
                        CompromisoPlan compromisoPlan = new CompromisoPlan();
                        compromisoPlan.setPlanDecision(compromisoPlanDTO.getPlanDecision());
                        compromisoPlan.setFecha(compromisoPlanDTO.getFecha());
                        compromisoPlan.setResponsable(compromisoPlanDTO.getResponsable());
                        compromisoPlan.setFirmaParticipacion(compromisoPlanDTO.getFirmaParticipacion());
                        compromisoPlan.setPlanMejoramiento(plan);
                        return compromisoPlan;
                    }).collect(Collectors.toList());
            plan.setCompromisosPlan(compromisosPlan);
        }

        // 6. Guardar asistentes
        if (planMejoramientoDTO.getAsistentesPlan() != null && !planMejoramientoDTO.getAsistentesPlan().isEmpty()) {
            List<AsistentePlan> asistentesPlan = planMejoramientoDTO.getAsistentesPlan().stream()
                    .map(asistentePlanDTO -> {
                        AsistentePlan asistentePlan = new AsistentePlan();
                        asistentePlan.setNombre(asistentePlanDTO.getNombre());
                        asistentePlan.setDependenciaEmpresa(asistentePlanDTO.getDependenciaEmpresa() != null ?
                                asistentePlanDTO.getDependenciaEmpresa() : "Sin dependencia");
                        asistentePlan.setAprueba(asistentePlanDTO.getAprueba() != null ?
                                asistentePlanDTO.getAprueba() :
                                "NO");
                        asistentePlan.setObservacion(asistentePlanDTO.getObservacion());
                        asistentePlan.setFirmaParticipacion(asistentePlanDTO.getFirmaParticipacion() != null ?
                                asistentePlanDTO.getFirmaParticipacion() : "");
                        asistentePlan.setPlanMejoramiento(plan);
                        asistentePlan.setNumeroDocumento(asistentePlanDTO.getNumeroDocumento());
                        asistentePlan.setPlanta(asistentePlanDTO.getPlanta());
                        asistentePlan.setContratista(asistentePlanDTO.getContratista());
                        asistentePlan.setOtro(asistentePlanDTO.getOtro());
                        asistentePlan.setCorreoElectronico(asistentePlanDTO.getCorreoElectronico());
                        asistentePlan.setTelefonoExt(asistentePlanDTO.getTelefonoExt());
                        asistentePlan.setAutorizaGrabacion(asistentePlanDTO.getAutorizaGrabacion() != null ?
                                asistentePlanDTO.getAutorizaGrabacion() : false);
                        return asistentePlan;
                    }).collect(Collectors.toList());
            plan.setAsistentesPlan(asistentesPlan);
        }

        // 7. Guardar las relaciones (compromisos y asistentes) usando JPA
        planMejoramientoRepository.save(plan);
        logger.info("Actividad y relaciones guardadas exitosamente con idActividad: {}", idPlanMejoramiento);

        // 8. Recuperar la entidad completa para devolver el DTO
        PlanMejoramiento save = planMejoramientoRepository.findById(idPlanMejoramiento)
                .orElseThrow(() -> new RuntimeException("No se pudo recuperar la actividad recién creada"));
        return planMejoramientoMapper.toPlanMejoramientoDTO(save);
    }

    private boolean esActaAsistenciaCompleta(PlanMejoramientoDTO dto) {
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
        if (dto.getAsistentesPlan() == null || dto.getAsistentesPlan().isEmpty()) {
            logger.error("No se proporcionaron asistentes para el acta.");
            throw new IllegalArgumentException("El acta debe incluir al menos un asistente.");
        }
        dto.getAsistentesPlan().stream().allMatch(asistentePlan -> {
            if (asistentePlan.getNombre() == null || asistentePlan.getNombre().trim().isEmpty()) {
                logger.error("Nombre del asistente no está completo: nombre={}", asistentePlan.getNombre());
                throw new IllegalArgumentException("El nombre del asistente es obligatorio.");
            }
            if (asistentePlan.getNumeroDocumento() == null || asistentePlan.getNumeroDocumento().trim().isEmpty()) {
                logger.error("Número de documento del asistente no está completo: numeroDocumento={}",
                        asistentePlan.getNumeroDocumento());
                throw new IllegalArgumentException("El número de documento del asistente es obligatorio.");
            }
            if (asistentePlan.getFirmaParticipacion() == null || asistentePlan.getFirmaParticipacion().trim().isEmpty()) {
                logger.error("Firma de participación del asistente no está completa: firmaParticipacion={}",
                        asistentePlan.getFirmaParticipacion());
                throw new IllegalArgumentException("La firma o participación virtual del asistente es obligatoria.");
            }
            if (asistentePlan.getCorreoElectronico() != null && !asistentePlan.getCorreoElectronico().trim().isEmpty()) {
                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                if (!asistentePlan.getCorreoElectronico().matches(emailPattern)) {
                    logger.warn("Correo electrónico inválido para asistente: {}", asistentePlan.getCorreoElectronico());
                    throw new IllegalArgumentException("El correo electrónico del asistente no tiene un formato " +
                            "válido: " + asistentePlan.getCorreoElectronico());
                }
            }
            return true;
        });
        return true;
    }

    @Override
    public Optional<PlanMejoramientoDTO> actualizarPlanMejoramiento(Integer idPlanMejoramiento,
                                                                    PlanMejoramientoDTO planMejoramientoDTO) {
        Optional<PlanMejoramiento> existingPlan = planMejoramientoRepository.findById(idPlanMejoramiento);
        if (existingPlan.isPresent()) {
            PlanMejoramiento plan = planMejoramientoMapper.toPlanMejoramiento(planMejoramientoDTO);
            plan.setIdPlanMejoramiento(idPlanMejoramiento);
            PlanMejoramiento updatedPlan = planMejoramientoRepository.save(plan);
            return Optional.of(planMejoramientoMapper.toPlanMejoramientoDTO(updatedPlan));
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminarPlanMejoramiento(Integer idAprendiz, Integer idPlanMejoramiento) {
        Optional<PlanMejoramiento> plan = planMejoramientoRepository.findById(idPlanMejoramiento);
        if (plan.isPresent() && plan.get().getIdAprendiz().equals(idAprendiz)) {
            planMejoramientoRepository.deleteById(idPlanMejoramiento);
            return true;
        }
        return false;
    }
}