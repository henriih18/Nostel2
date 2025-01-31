package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "numeroDocente", target = "numeroDocente")
    @Mapping(source = "area", target = "area")
    InstructorDTO toInstructorDTO(Instructor instructor);

    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "numeroDocente", target = "numeroDocente")
    @Mapping(source = "area", target = "area")
    Instructor toInstructor(InstructorDTO instructorDTO);


}
