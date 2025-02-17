package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "numeroDocente", target = "numeroDocente")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "contrasena", target = "contrasena")
    /*@Mapping(target = "actividadComplementarias", ignore = true)
    @Mapping(target = "inasistencias", ignore = true)
    @Mapping(target = "planesMejoramiento", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "fichasInstructores", ignore = true)*/
    InstructorDTO toInstructorDTO(Instructor instructor);

    List<InstructorDTO> toInstructorDTOList(List<Instructor> instructores);

    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "numeroDocente", target = "numeroDocente")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "contrasena", target = "contrasena")
   /* @Mapping(target = "actividadComplementarias", ignore = true)
    @Mapping(target = "inasistencias", ignore = true)
    @Mapping(target = "planesMejoramiento", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "fichasInstructores", ignore = true)*/
    Instructor toInstructor(InstructorDTO instructorDTO);

    void updateInstructorFromDto(InstructorDTO instructorDTO, @MappingTarget Instructor instructor);


}
