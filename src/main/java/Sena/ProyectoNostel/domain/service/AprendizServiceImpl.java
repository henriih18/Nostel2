/*package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AprendizServiceImpl implements AprendizService {

    @Autowired
    private AprendizRepository aprendizRepository;

    @Override
    public List<AprendizDTO> obtenerTodos() {
        return aprendizRepository.obtenerTodosDTO();
    }

    @Override
    public Optional<AprendizDTO> obtenerPorIdAprendiz(Integer idAprendiz) {
        return aprendizRepository.obtenerPorIdDTO(idAprendiz);
    }

    @Override
    public AprendizDTO crear(AprendizDTO aprendizDTO) {
        // Aquí puedes agregar lógica de negocio antes de crear el aprendiz
        Aprendiz aprendiz = aprendizRepository.convertirAaprendiz(aprendizDTO);
        aprendiz = aprendizRepository.save(aprendiz);
        return aprendizRepository.convertirAaprendizDTO(aprendiz);
    }

    @Override
    public Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO) {
        return aprendizRepository.findById(idAprendiz).map(aprendizExistente -> {
            aprendizExistente.setPrimerNombre(aprendizDTO.getPrimerNombre());
            aprendizExistente.setSegundoNombre(aprendizDTO.getSegundoNombre());
            aprendizExistente.setPrimerApellido(aprendizDTO.getPrimerApellido());
            aprendizExistente.setSegundoApellido(aprendizDTO.getSegundoApellido());
            aprendizExistente.setFechaNacimiento(aprendizDTO.getFechaNacimiento());
            aprendizExistente.setGenero(aprendizDTO.getGenero());
            aprendizExistente.setCorreo(aprendizDTO.getCorreo());
            aprendizExistente.setTelefono(aprendizDTO.getTelefono());
            aprendizExistente.setResidencia(aprendizDTO.getResidencia());
            /*aprendizExistente.setDiscapacidad(aprendizDTO.getDiscapacidad());
            aprendizExistente.setGrupoEtnico(aprendizDTO.getGrupoEtnico());
            Aprendiz aprendizActualizado = aprendizRepository.save(aprendizExistente);
            return aprendizRepository.convertirAaprendizDTO(aprendizActualizado);
        });
    }

    @Override
    public void eliminar(Integer idAprendiz) {
        // Aquí puedes agregar lógica de negocio antes de eliminar el aprendiz
        aprendizRepository.deleteById(idAprendiz);
    }
}*/


package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.mapper.AprendizMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AprendizServiceImpl implements AprendizService {

    @Autowired
    private AprendizRepository aprendizRepository;

    @Autowired
    private AprendizMapper aprendizMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional()
    public List<AprendizDTO> obtenerTodos() {
        return aprendizRepository.findAll().stream()
                .map(aprendizMapper::toAprendizDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional()
    public Optional<AprendizDTO> obtenerPorIdAprendiz(Integer idAprendiz) {
        return aprendizRepository.findById(idAprendiz)
                .map(aprendizMapper::toAprendizDTO);
        /*.map(aprendiz -> {
                    aprendiz.getInasistencias().size();
                    return aprendizMapper.toAprendizDTO(aprendiz);
                });*/
    }

    /*@Override
    public AprendizDTO crear(AprendizDTO aprendizDTO) {
        //verifica que el correo no esté registrado
        if (aprendizRepository.findByCorreo(aprendizDTO.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }  if (aprendizRepository.findByDocumento(aprendizDTO.getDocumento()).isPresent()) {
            throw new IllegalArgumentException("El número de documento ya está registrado");
        }
        //se aplica trim a los campos de texto que ingresa el usuario
        aprendizDTO.setNombres(aprendizDTO.getNombres().trim());
        aprendizDTO.setApellidos(aprendizDTO.getApellidos().trim());
        aprendizDTO.setCorreo(aprendizDTO.getCorreo().trim());
        aprendizDTO.setTelefono(aprendizDTO.getTelefono().trim());
        aprendizDTO.setResidencia(aprendizDTO.getResidencia().trim());

        //mapea DTO a entidad y encripta la contraseña
        Aprendiz aprendiz = aprendizMapper.toAprendiz(aprendizDTO);
        aprendiz.setContrasena(passwordEncoder.encode(aprendiz.getContrasena()));

        //guarda en la BD
        aprendiz = aprendizRepository.save(aprendiz);
        return aprendizMapper.toAprendizDTO(aprendiz);
    }*/

    @Override
    @Transactional
    public AprendizDTO crear(AprendizDTO aprendizDTO) {
        // Validaciones previas (correo, documento, etc.)
        // ...

        // Encriptar contraseña
        aprendizDTO.setContrasena(passwordEncoder.encode(aprendizDTO.getContrasena()));

        // Llamada al procedimiento almacenado
        jdbcTemplate.update("CALL registrarAprendiz(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                aprendizDTO.getTipoDocumento().name(),
                aprendizDTO.getDocumento(),
                aprendizDTO.getNombres(),
                aprendizDTO.getApellidos(),
                aprendizDTO.getFechaNacimiento(),
                aprendizDTO.getGenero().name(),
                aprendizDTO.getCorreo(),
                aprendizDTO.getContrasena(),
                aprendizDTO.getTelefono(),
                aprendizDTO.getResidencia(),
                aprendizDTO.getNumeroFicha()  // <-- Aquí envías el número de la ficha
        );

        // Opcional: recuperar el aprendiz recién insertado, por ejemplo usando el correo
        Optional<Aprendiz> aprendizOpt = aprendizRepository.findByCorreo(aprendizDTO.getCorreo());
        if (aprendizOpt.isPresent()) {
            return aprendizMapper.toAprendizDTO(aprendizOpt.get());
        } else {
            throw new IllegalArgumentException("Error al recuperar el aprendiz registrado.");
        }
    }


    @Override
    @Transactional
    public Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO) {
        return aprendizRepository.findById(idAprendiz).map(aprendizExistente -> {
            aprendizMapper.updateAprendizFromDto(aprendizDTO, aprendizExistente);
            Aprendiz aprendizActualizado = aprendizRepository.save(aprendizExistente);
            return aprendizMapper.toAprendizDTO(aprendizActualizado);
        });
    }

    @Override
    public void eliminar(Integer idAprendiz) {
        aprendizRepository.deleteById(idAprendiz);
    }

    /*@Override
    public AprendizDTO obtenerAprendizConComentarios(Integer idAprendiz) {
        Aprendiz aprendiz = aprendizRepository.obtenerConComentarios(idAprendiz)
                .orElseThrow(() -> new RuntimeException("Aprendiz no encontrado"));
        return aprendizMapper.toAprendizDTO(aprendiz);
    }*/




}

