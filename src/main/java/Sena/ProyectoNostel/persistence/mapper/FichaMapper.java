package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "string")
public interface FichaMapper {
    @Mapping(source = "numeroFicha", target = "numeroFicha")
    @Mapping(source = "nombrePrograma", target = "nombrePrograma")
    @Mapping(source = "horario", target = "horario")
    @Mapping(source = "fechaInicio", target = "fechaInicio")
    @Mapping(source = "fechaFin", target = "fechaFin")
    @Mapping(source = "numeroAmbiente", target = "numeroAmbiente")
    @Mapping(source = "aprendices", target = "aprendices")
    FichaDTO toFichaDTO(Ficha ficha);

    List<FichaDTO> toFichaDTOList(List<Ficha> fichas);

    @Mapping(source = "numeroFicha", target = "numeroFicha")
    @Mapping(source = "nombrePrograma", target = "nombrePrograma")
    @Mapping(source = "horario", target = "horario")
    @Mapping(source = "fechaInicio", target = "fechaInicio")
    @Mapping(source = "fechaFin", target = "fechaFin")
    @Mapping(source = "numeroAmbiente", target = "numeroAmbiente")
    @Mapping(source = "aprendices", target = "aprendices")
    Ficha toFicha(FichaDTO fichaDTO);

    void updateFichaFromDto(FichaDTO fichaDTO, @MappingTarget Ficha ficha);
}
