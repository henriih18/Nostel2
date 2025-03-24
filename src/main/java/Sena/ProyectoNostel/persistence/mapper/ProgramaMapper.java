package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.ProgramaDTO;
import Sena.ProyectoNostel.persistence.entity.Programa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgramaMapper {

    @Mapping(source = "idPrograma", target = "idPrograma")
    @Mapping(source = "nombrePrograma", target = "nombrePrograma")
    ProgramaDTO toProgramaDTO(Programa programa);

    List<ProgramaDTO> toProgramaDTOList(List<Programa> programas);

    @Mapping(source = "idPrograma", target = "idPrograma")
    @Mapping(source = "nombrePrograma", target = "nombrePrograma")
    Programa toPrograma(ProgramaDTO programaDTO);

    void updateProgramaFromDto(ProgramaDTO programaDTO, @MappingTarget Programa programa);
}