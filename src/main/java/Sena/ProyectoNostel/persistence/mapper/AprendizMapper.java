package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AprendizMapper {
    AprendizMapper INSTANCE = Mappers.getMapper(AprendizMapper.class);

    @Mapping(source = "primerNombre", target = "primerNombre")
    @Mapping(source = "segundoNombre", target = "segundoNombre")
    @Mapping(source = "primerApellido", target = "primerApellido")
    @Mapping(source = "segundoApellido", target = "segundoApellido")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "residencia", target = "residencia")
    @Mapping(source = "grupoEtnico", target = "grupoEtnico")
    /*@Mapping(source = "ficha.idFicha", target = "idFicha")*/
    //@Mapping(source = "inasistencias", target = "inasistencias")
    //@Mapping(target = "comentario", ignore = true)
    AprendizDTO toAprendizDTO(Aprendiz aprendiz);

    List<ComentarioDTO> toComentarioDTOList(List<Comentario> comentario);

    @Mapping(source = "primerNombre", target = "primerNombre")
    @Mapping(source = "segundoNombre", target = "segundoNombre")
    @Mapping(source = "primerApellido", target = "primerApellido")
    @Mapping(source = "segundoApellido", target = "segundoApellido")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "residencia", target = "residencia")
    @Mapping(source = "grupoEtnico", target = "grupoEtnico")
    //@Mapping(target = "ficha", ignore = true)
    //@Mapping(target = "comentario", ignore = true)
    //@Mapping(target = "inasistencias", ignore = true)
    Aprendiz toAprendiz(AprendizDTO aprendizDTO);

    void updateAprendizFromDto(AprendizDTO aprendizDTO, @MappingTarget Aprendiz aprendiz);
}