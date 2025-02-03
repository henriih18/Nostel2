package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    @Mapping(source = "comentario", target = "comentario")
    @Mapping(source = "fechaComentario", target = "fechaComentario")
    @Mapping(expression = "java(comentario.getInstructor().getNombres() + \" \" + comentario.getInstructor().getApellidos())", target = "nombreInstructor")
    ComentarioDTO toComentarioDTO(Comentario comentario);

    List<ComentarioDTO> toComentarioDTOList(List<Comentario> comentarios);

    @Mapping(source = "comentario", target = "comentario")
    @Mapping(source = "fechaComentario", target = "fechaComentario")
    @Mapping(target = "idInstructor", ignore = true)
    @Mapping(target = "idAprendiz", ignore = true)
    Comentario toComentario(ComentarioDTO comentarioDTO);
    void updateComentarioFromDto(ComentarioDTO comentarioDTO, @MappingTarget Comentario comentario);
}
