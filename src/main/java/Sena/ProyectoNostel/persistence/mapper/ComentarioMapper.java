package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    @Mapping(source = "instructor.nombres", target = "nombreInstructor")
    @Mapping(source = "comentario", target = "comentario")
    ComentarioDTO toComentarioDTO(Comentario comentario);
}
