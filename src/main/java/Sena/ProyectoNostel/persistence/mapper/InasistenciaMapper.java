package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.InasistenciaDTO;
import Sena.ProyectoNostel.persistence.entity.Inasistencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InasistenciaMapper {
    @Mapping(source = "fechaInasistencia", target = "fechaInasistencia")
    @Mapping(source = "motivo", target = "motivo")
    @Mapping(expression = "java(inasistencia.getInstructor().getNombres() + \" \" + inasistencia.getInstructor().getApellidos())", target = "nombreInstructor")
    InasistenciaDTO toInasistenciaDTO(Inasistencia inasistencia);

    List<InasistenciaDTO> toInasistenciaDTOList(List<Inasistencia> inasistencias);

    @Mapping(source = "fechaInasistencia", target = "fechaInasistencia")
    @Mapping(source = "motivo", target = "motivo")
    @Mapping(target = "instructor", ignore = true)
    Inasistencia toInasistencia(InasistenciaDTO inasistenciaDTO);
}
