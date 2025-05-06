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

    /*InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);*/

    @Mapping(source = "usuario.idUsuario", target = "idUsuario")
    InstructorDTO toInstructorDTO(Instructor instructor);

    List<InstructorDTO> toInstructorDTOList(List<Instructor> instructores);

    Instructor toInstructor(InstructorDTO instructorDTO);

    void updateInstructorFromDto(InstructorDTO instructorDTO, @MappingTarget Instructor instructor);


}
