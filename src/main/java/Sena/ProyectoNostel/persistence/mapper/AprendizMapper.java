package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AprendizMapper {
    //AprendizMapper INSTANCE = Mappers.getMapper(AprendizMapper.class);

    @Mapping(source = "documento", target = "documento")
    @Mapping(source = "tipoDocumento", target = "tipoDocumento")
    @Mapping(source = "nombres", target = "nombres")
    /*@Mapping(source = "segundoNombre", target = "segundoNombre")*/
    @Mapping(source = "apellidos", target = "apellidos")
   /* @Mapping(source = "segundoApellido", target = "segundoApellido")*/
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")

    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "contrasena", target = "contrasena")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "residencia", target = "residencia")
   // @Mapping(source = "grupoEtnico", target = "grupoEtnico")
    @Mapping(source = "ficha.numeroFicha", target = "numeroFicha")
    @Mapping(source = "ficha.nombrePrograma", target = "nombrePrograma")
    @Mapping(source = "ficha.numeroAmbiente", target = "numeroAmbiente")
    //@Mapping(source = "inasistencias", target = "inasistencias")
    @Mapping(source = "comentarios", target = "comentarios")
    @Mapping(source = "planMejoramientos", target ="planMejoramientos")
    //@Mapping(expression = "java(aprendiz.getFicha().getNumeroFicha()) ", target = "numeroFicha")

    @Mapping(source = "actividadComplementarias", target = "actividadComplementarias")
    AprendizDTO toAprendizDTO(Aprendiz aprendiz);

   /* List<ComentarioDTO> toComentarioDTOList(List<Comentario> comentario);*/
    List<AprendizDTO> toAprendizDTOList(List<Aprendiz> aprendices);


    @Mapping(source = "documento", target = "documento")
    @Mapping(source = "tipoDocumento", target = "tipoDocumento")
    /*@Mapping(source = "primerNombre", target = "primerNombre")
    @Mapping(source = "segundoNombre", target = "segundoNombre")
    @Mapping(source = "primerApellido", target = "primerApellido")
    @Mapping(source = "segundoApellido", target = "segundoApellido")*/
    @Mapping(source = "nombres", target = "nombres")

    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "contrasena", target = "contrasena")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "residencia", target = "residencia")
    //@Mapping(source = "grupoEtnico", target = "grupoEtnico")
    //@Mapping(source = "inasistencias", target = "inasistencias")
    @Mapping(source = "comentarios", target = "comentarios")
    @Mapping(source = "planMejoramientos", target ="planMejoramientos")
    @Mapping(source = "actividadComplementarias", target = "actividadComplementarias")
    //@Mapping(target = "ficha", ignore = true)
    Aprendiz toAprendiz(AprendizDTO aprendizDTO);


    /*@Mapping(target = "inasistencias", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "planesMejoramiento", ignore = true)
    @Mapping(target = "actividadComplementarias", ignore = true)
    @Mapping(target = "ficha", ignore = true)*/


    void updateAprendizFromDto(AprendizDTO aprendizDTO, @MappingTarget Aprendiz aprendiz);
}