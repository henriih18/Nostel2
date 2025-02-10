package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.persistence.entity.PlanMejoramiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMejoramientoMapper {

    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "fechaInicio", target = "fechaInicio")
    @Mapping(source = "fechaFin", target = "fechaFin")
    @Mapping(source = "estado", target = "estado")
    @Mapping(source = "instructor.nombres", target = "nombreInstructor")
    //@Mapping(expression = "java(planMejoramiento.getInstructor().getNombres() + \" \" + planMejoramiento.getInstructor().getApellidos())", target = "nombreInstructor")
    PlanMejoramientoDTO toPlanMejoramientoDTO(PlanMejoramiento planMejoramiento);

   List<PlanMejoramientoDTO> toPlanMejoramientoList(List<PlanMejoramiento> planMejoramientos);

    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "fechaInicio", target = "fechaInicio")
    @Mapping(source = "fechaFin", target = "fechaFin")
    @Mapping(source = "estado", target = "estado")
   PlanMejoramiento toPlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO);

    void updatePlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO, @MappingTarget PlanMejoramiento planMejoramiento );
}
