package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.persistence.entity.PlanMejoramiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMejoramientoMapper {


    //@Mapping(source = "actividad.idActividad", target = "idActividad")
    PlanMejoramientoDTO toPlanMejoramientoDTO(PlanMejoramiento planMejoramiento);

   List<PlanMejoramientoDTO> toPlanMejoramientoList(List<PlanMejoramiento> planMejoramientos);


    /*@Mapping(source = "idPlanMejoramiento", target = "idPlanMejoramiento")
    @Mapping(source = "aprendiz", target = "aprendizDTO") // Asegúrate de tener AprendizMapper
    @Mapping(source = "instructor", target = "instructorDTO")*/
   PlanMejoramiento toPlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO);

    void updatePlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO, @MappingTarget PlanMejoramiento planMejoramiento );
}
