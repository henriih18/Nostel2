
package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AprendizMapper {

    @Mapping(source = "ficha.numeroFicha", target = "numeroFicha")
    @Mapping(source = "ficha.nombrePrograma", target = "nombrePrograma")
    @Mapping(source = "ficha.numeroAmbiente", target = "numeroAmbiente")
    AprendizDTO toAprendizDTO(Aprendiz aprendiz);

    List<AprendizDTO> toAprendizDTOList(List<Aprendiz> aprendices);


    Aprendiz toAprendiz(AprendizDTO aprendizDTO);

    void updateAprendizFromDto(AprendizDTO aprendizDTO, @MappingTarget Aprendiz aprendiz);
}