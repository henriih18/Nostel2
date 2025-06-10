
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