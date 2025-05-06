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

    //@Mapping(source = "instructor.nombres", target = "nombreInstructor")
    ComentarioDTO toComentarioDTO(Comentario comentario);

    List<ComentarioDTO> toComentarioDTOList(List<Comentario> comentarios);


    Comentario toComentario(ComentarioDTO comentarioDTO);
    void updateComentarioFromDto(ComentarioDTO comentarioDTO, @MappingTarget Comentario comentario);
}
